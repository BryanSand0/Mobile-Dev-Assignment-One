

package edu.charlotte.assigment08;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegisterFragment extends Fragment {

    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private RegisterFragmentListener mListener;
    private FirebaseAuth mAuth;

    public RegisterFragment() {
    }

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }


    public interface RegisterFragmentListener {
        void gotoLogin();
        void gotoForums();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof RegisterFragmentListener) {
            mListener = (RegisterFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement RegisterFragmentListener");
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
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        editTextName = view.findViewById(R.id.editTextName);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextPassword = view.findViewById(R.id.editTextPassword);

        view.findViewById(R.id.buttonSubmit).setOnClickListener(v -> performRegistration());

        view.findViewById(R.id.buttonCancel).setOnClickListener(v -> {
            if (mListener != null) {
                mListener.gotoLogin();
            }
        });

        return view;
    }

    private void performRegistration() {
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (name.isEmpty()) {
            showErrorDialog("Name is required.");
            return;
        }

        if (email.isEmpty()) {
            showErrorDialog("Email is required.");
            return;
        }

        if (password.isEmpty()) {
            showErrorDialog("Password is required.");
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity(), task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            // Attach display name to the FirebaseUser profile
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name)
                                    .build();

                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(profileTask -> {
                                        if (profileTask.isSuccessful()) {
                                            Toast.makeText(getContext(), "Registration Successful!", Toast.LENGTH_SHORT).show();
                                            if (mListener != null) {
                                                mListener.gotoForums();
                                            }
                                        } else {
                                            String profileError = "Failed to set display name.";
                                            if (profileTask.getException() != null && profileTask.getException().getMessage() != null) {
                                                profileError = profileTask.getException().getMessage();
                                            }
                                            showErrorDialog(profileError);
                                        }
                                    });
                        } else {
                            if (mListener != null) {
                                mListener.gotoForums();
                            }
                        }
                    } else {

                        String errorMessage = "Registration failed.";
                        if (task.getException() != null && task.getException().getMessage() != null) {
                            errorMessage = task.getException().getMessage();
                        }
                        showErrorDialog(errorMessage);
                    }
                });
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
