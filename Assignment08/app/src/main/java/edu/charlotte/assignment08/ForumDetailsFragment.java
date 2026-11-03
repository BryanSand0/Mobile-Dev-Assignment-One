package edu.charlotte.assignment08;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ForumDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForumDetailsFragment extends Fragment implements CommentAdapter.CommentAdapterListener {

    private String forumId = "Default";
    private String title = "Title";
    private String authorName;
    private String description;
    private TextView titleText;
    private TextView authorNameText;
    private TextView descriptionText;

    private EditText editTextComment;

    private RecyclerView recyclerViewComments;
    private CommentAdapter adapter;
    private final List<Comment> commentList = new ArrayList<>();
    private ListenerRegistration commentListenerRegistration;

    private ForumDetailsFragmentListener mListener;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    public ForumDetailsFragment(String fId) {
        forumId = fId;
    }

    public static ForumDetailsFragment newInstance() {
        return new ForumDetailsFragment("Default");
    }


    public interface ForumDetailsFragmentListener {
        void gotoForums();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ForumDetailsFragment.ForumDetailsFragmentListener) {
            mListener = (ForumDetailsFragment.ForumDetailsFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement ForumFragmentListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forum_details, container, false);

        editTextComment = view.findViewById(R.id.editTextComment);

        //Find textviews to change in function later
        titleText = view.findViewById(R.id.textViewForumPostTitle);
        authorNameText = view.findViewById(R.id.textViewForumPostAuthorName);
        descriptionText = view.findViewById(R.id.textViewForumPostDescription);


        //forumId = "Default";
        //Set/ get the info for this post
        if (forumId.compareTo("Default") == 0){
            //Toast.makeText(view.getContext(), "Defaults are being used instead", Toast.LENGTH_SHORT).show();
            title = "Title";
            authorName = "Author Name";
            description = "Description";

            //Find textviews to change in function later
            titleText = view.findViewById(R.id.textViewForumPostTitle);
            authorNameText = view.findViewById(R.id.textViewForumPostAuthorName);
            descriptionText = view.findViewById(R.id.textViewForumPostDescription);

        }
        else {
            //Toast.makeText(view.getContext(), "Proper data for ID: " + forumId + ", should be displayed", Toast.LENGTH_SHORT).show();

            DocumentReference docRef = db.collection("forums").document(forumId);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Log.d("Forum Details", "DocumentSnapshot data: " + document.getData());
                        } else {
                            Log.d("Forum Details", "No such document");
                        }
                        Map<String, Object> data = document.getData();

                        title = data.get("title").toString();
                        Log.d("Forum Details", title);
                        authorName = data.get("creatorName").toString();
                        Log.d("Forum Details", authorName);
                        description = data.get("description").toString();
                        Log.d("Forum Details", description);

                        RefreshTextViews();
                    } else {
                        Log.d("Forum Details", "get failed with ", task.getException());
                    }
                }
            });
        }


        //TODO: Write recycler views to work, should be last thing
        recyclerViewComments = view.findViewById(R.id.recyclerViewComments);
        recyclerViewComments.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new CommentAdapter(commentList, mAuth.getCurrentUser().getUid(),this);
        recyclerViewComments.setAdapter(adapter);


        view.findViewById(R.id.buttonPostComment).setOnClickListener(v -> {
            String content = editTextComment.getText().toString();
            //Perform validation checks for title and description
            if (content.isEmpty()) {
                Toast.makeText(getContext(), "Please provide comments for your post", Toast.LENGTH_SHORT).show();
            }
            else {
                submitComment(content);
                editTextComment.setText("");
            }
        });

        view.findViewById(R.id.buttonCancelForumDetails).setOnClickListener(v -> {
            if (mListener != null) {
                mListener.gotoForums();
            }
        });

        setupCommentsSnapshotListener();

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Detach snapshot listener to prevent leaks
        if (commentListenerRegistration != null) {
            commentListenerRegistration.remove();
            commentListenerRegistration = null;
        }
    }

    private void RefreshTextViews() {
        titleText.setText(title);
        Log.d("Forum Filled In", titleText.getText().toString());
        authorNameText.setText(authorName);
        Log.d("Forum Filled In", authorNameText.getText().toString());
        descriptionText.setText(description);
        Log.d("Forum Filled In", descriptionText.getText().toString());
    }

    private void setupCommentsSnapshotListener() {
        Query query = db.collection("forums").document(forumId).collection("comments").orderBy("createdAt", Query.Direction.DESCENDING);

        commentListenerRegistration = query.addSnapshotListener((value, error) -> {
            if (error != null) {
                if (getContext() != null) {
                    Toast.makeText(getContext(), "Error loading forums: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
                return;
            }
            else {
                //Toast.makeText(getContext(), "No errors loading comments", Toast.LENGTH_SHORT).show();
            }

            if (value != null) {
                commentList.clear();
                for (QueryDocumentSnapshot doc : value) {
                    Comment comment = doc.toObject(Comment.class);


                    if (comment.getCommentId() == null) {
                        comment.setCommentId(doc.getId());
                    }
                    Log.d("Setting up comments", "Comment of ID: " + comment.getCommentId().toString() + " made");
                    //Toast.makeText(getContext(), comment.getCommentId().toString(), Toast.LENGTH_SHORT).show();

                    commentList.add(comment);
                }
                adapter.setComments(commentList);
                //Toast.makeText(getContext(), "Comments set", Toast.LENGTH_SHORT).show();
            }
            else {
                //Toast.makeText(getContext(), "I tried but it no work", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Comment submission
    private void submitComment(String content) {
        if (mAuth.getCurrentUser() != null) {
            //Create comment map here
            Map<String, Object> comment = new HashMap<>();
            comment.put("authorName", mAuth.getCurrentUser().getDisplayName());
            comment.put("content", content);
            comment.put("creatorId", mAuth.getCurrentUser().getUid());
            comment.put("createdAt", Timestamp.now());

            Log.d("Comment Submission", "Comment Map made");

            //Add the comment with a random id
            String id = db.collection("forums").document(forumId).collection("comments").document().getId();
            db.collection("forums").document(forumId)
                    .collection("comments").document(id).set(comment)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getContext(), "Comment made!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        else Toast.makeText(getContext(), "Must be signed in to make comments", Toast.LENGTH_SHORT).show();
    }

    //Comment Deletion
    private void deleteComment(Comment comment) {
        if (comment == null || comment.getCommentId() == null) {
            Toast.makeText(getContext(), "Deletion failed, comment could not be found", Toast.LENGTH_SHORT).show();
            return;
        }

        String currentUserId = mAuth.getCurrentUser().getUid();

        if (currentUserId == null || !currentUserId.equals(comment.getCreatorId())) {
            if (getContext() != null) {
                Toast.makeText(getContext(), "Unauthorized: You do not own this comment.", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        String commentId = comment.getCommentId();

        db.collection("forums")
                .document(forumId)
                .collection("comments")
                .document(commentId)
                .delete();
    }

    @Override
    public void onDeleteClick(Comment comment) {
        if (comment == null || comment.getCommentId() == null) return;
        String currentUserId = mAuth.getCurrentUser().getUid();
        // Verify ownership: Delete is allowed only for the comment creator
        if (currentUserId == null || !currentUserId.equals(comment.getCreatorId())) {
            Toast.makeText(getContext(), "You can only delete comments created by you.", Toast.LENGTH_SHORT).show();
            return;
        }

        new AlertDialog.Builder(requireContext())
                .setTitle("Delete Comment")
                .setMessage("Are you sure you want to delete this comment?")
                .setPositiveButton("Delete", (dialog, which) -> deleteComment(comment))
                .setNegativeButton("Cancel", null)
                .show();
    }
}