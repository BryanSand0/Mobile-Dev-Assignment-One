/**
 * Assignment #: Assignment 8
 * File Name: ForumsFragment.java
 * Full Name: [Student Name]
 */

package edu.charlotte.assignment08;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.List;

public class ForumsFragment extends Fragment implements ForumAdapter.ForumAdapterListener {

    private RecyclerView recyclerViewForums;
    private ForumAdapter adapter;
    private final List<Forum> forumList = new ArrayList<>();
    
    private ForumsFragmentListener mListener;
    private FirebaseFirestore db;
    private ListenerRegistration forumListenerRegistration;
    private String currentUserId;

    public ForumsFragment() {

    }

    public static ForumsFragment newInstance() {
        return new ForumsFragment();
    }

    public interface ForumsFragmentListener {
        void logout();
        void gotoNewForum();
        void gotoForumDetails(String forumId);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ForumsFragmentListener) {
            mListener = (ForumsFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement ForumsFragmentListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            currentUserId = currentUser.getUid();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forums, container, false);

        recyclerViewForums = view.findViewById(R.id.recyclerViewForums);
        recyclerViewForums.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new ForumAdapter(forumList, currentUserId, this);
        recyclerViewForums.setAdapter(adapter);

        view.findViewById(R.id.buttonLogout).setOnClickListener(v -> {
            if (mListener != null) {
                mListener.logout();
            }
        });

        view.findViewById(R.id.buttonNewForum).setOnClickListener(v -> {
            if (mListener != null) {
                mListener.gotoNewForum();
            }
        });

        setupForumsSnapshotListener();

        return view;
    }


    private void setupForumsSnapshotListener() {
        Query query = db.collection("forums").orderBy("createdAt", Query.Direction.DESCENDING);

        forumListenerRegistration = query.addSnapshotListener((value, error) -> {
            if (error != null) {
                if (getContext() != null) {
                    Toast.makeText(getContext(), "Error loading forums: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
                return;
            }

            if (value != null) {
                forumList.clear();
                for (QueryDocumentSnapshot doc : value) {
                    Forum forum = doc.toObject(Forum.class);
                    if (forum.getForumId() == null) {
                        forum.setForumId(doc.getId());
                    }
                    forumList.add(forum);
                }
                adapter.setForums(forumList);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Detach snapshot listener to prevent leaks
        if (forumListenerRegistration != null) {
            forumListenerRegistration.remove();
            forumListenerRegistration = null;
        }
    }


    @Override
    public void onForumClick(Forum forum) {
        if (mListener != null && forum != null && forum.getForumId() != null) {
            mListener.gotoForumDetails(forum.getForumId());
        }
    }

    @Override
    public void onLikeClick(Forum forum) {
        if (currentUserId == null || forum == null || forum.getForumId() == null) return;

        DocumentReference forumRef = db.collection("forums").document(forum.getForumId());

        // Toggle like state using atomic Firestore array operations
        if (forum.isLikedBy(currentUserId)) {
            forumRef.update("likedBy", FieldValue.arrayRemove(currentUserId))
                    .addOnFailureListener(e -> {
                        if (getContext() != null) {
                            Toast.makeText(getContext(), "Failed to update like: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            forumRef.update("likedBy", FieldValue.arrayUnion(currentUserId))
                    .addOnFailureListener(e -> {
                        if (getContext() != null) {
                            Toast.makeText(getContext(), "Failed to update like: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    @Override
    public void onDeleteClick(Forum forum) {
        if (forum == null || forum.getForumId() == null) return;

        // Verify ownership: Delete is allowed only for the forum creator
        if (currentUserId == null || !currentUserId.equals(forum.getCreatorId())) {
            Toast.makeText(getContext(), "You can only delete forums created by you.", Toast.LENGTH_SHORT).show();
            return;
        }

        new AlertDialog.Builder(requireContext())
                .setTitle("Delete Forum")
                .setMessage("Are you sure you want to delete this forum and all its comments?")
                .setPositiveButton("Delete", (dialog, which) -> deleteForumAndComments(forum))
                .setNegativeButton("Cancel", null)
                .show();
    }


    private void deleteForumAndComments(Forum forum) {
        if (forum == null || forum.getForumId() == null) return;

        if (currentUserId == null || !currentUserId.equals(forum.getCreatorId())) {
            if (getContext() != null) {
                Toast.makeText(getContext(), "Unauthorized: You do not own this forum.", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        String forumId = forum.getForumId();

        db.collection("forums")
                .document(forumId)
                .collection("comments")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<DocumentReference> refsToDelete = new ArrayList<>();

                    for (QueryDocumentSnapshot commentDoc : queryDocumentSnapshots) {
                        refsToDelete.add(commentDoc.getReference());
                    }

                    DocumentReference forumRef = db.collection("forums").document(forumId);
                    refsToDelete.add(forumRef);

                    deleteInBatches(refsToDelete, 0);
                })
                .addOnFailureListener(e -> {
                    if (getContext() != null) {
                        new AlertDialog.Builder(requireContext())
                                .setTitle("Deletion Aborted")
                                .setMessage("Failed to retrieve comments for deletion: " + e.getMessage() + "\n\nDeletion was aborted to prevent orphaned comment data.")
                                .setPositiveButton("OK", null)
                                .show();
                    }
                });
    }


    private void deleteInBatches(List<DocumentReference> refs, int startIndex) {
        if (startIndex >= refs.size()) {
            if (getContext() != null) {
                Toast.makeText(getContext(), "Forum deleted successfully", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        WriteBatch batch = db.batch();
        int endIndex = Math.min(startIndex + 500, refs.size());

        for (int i = startIndex; i < endIndex; i++) {
            batch.delete(refs.get(i));
        }

        batch.commit()
                .addOnSuccessListener(aVoid -> deleteInBatches(refs, endIndex))
                .addOnFailureListener(e -> {
                    if (getContext() != null) {
                        Toast.makeText(getContext(), "Error during forum deletion: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
