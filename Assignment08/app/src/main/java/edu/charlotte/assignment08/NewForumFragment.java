package edu.charlotte.assignment08;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewForumFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewForumFragment extends Fragment {

    private EditText editTextTitle;
    private EditText editTextDescription;
    private NewForumFragment.NewForumFragmentListener mListener;
    private FirebaseAuth mAuth;

    public NewForumFragment() {
    }

    public static NewForumFragment newInstance() {
        return new NewForumFragment();
    }


    public interface NewForumFragmentListener {
        void submitForum(String title, String desc);
        void gotoForums();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof NewForumFragment.NewForumFragmentListener) {
            mListener = (NewForumFragment.NewForumFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement NewForumFragmentListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_forum, container, false);

        editTextTitle = view.findViewById(R.id.editTextTitle);
        editTextDescription = view.findViewById(R.id.editTextDescription);

        view.findViewById(R.id.buttonSubmitForum).setOnClickListener(v -> {
            String title = editTextTitle.getText().toString();
            String desc = editTextDescription.getText().toString();
            //Perform validation checks for title and description
            if (title.isEmpty()) {
                Toast.makeText(getContext(), "Please provide a title for your post", Toast.LENGTH_SHORT).show();
            }
            else if (desc.isEmpty()) {
                Toast.makeText(getContext(), "Please provide a description for your post", Toast.LENGTH_SHORT).show();
            }
            else {
                mListener.submitForum(title, desc);
            }
        });

        view.findViewById(R.id.buttonCancelForum).setOnClickListener(v -> {
            if (mListener != null) {
                mListener.gotoForums();
            }
        });

        return view;
    }

    private void showErrorDialog(String message) {
        if (getContext() == null) return;
        new AlertDialog.Builder(requireContext())
                .setTitle("Error")
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }
}