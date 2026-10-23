/**
 * Assignment: 7
 * File Name: CreateAccountFragment.java
 * Student Name: Lucnel Nordelus and Bryan Sandoval
 *
 */
package edu.charlotte.assignment07.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import edu.charlotte.assignment07.databinding.FragmentCreateAccountBinding;

public class CreateAccountFragment extends Fragment {
    public CreateAccountFragment() {
        // Required empty public constructor
    }

    FragmentCreateAccountBinding binding;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCreateAccountBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoLoginAccount();
            }
        });

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = String.valueOf(binding.editTextText.getText()).trim();
                String email = String.valueOf(binding.editTextEmailAddress.getText()).trim();
                String password = String.valueOf(binding.editTextPassword.getText()).trim();

                if (name.isEmpty()) {
                    showErrorDialog("Full name is required.");
                } else if (email.isEmpty()) {
                    showErrorDialog("Email address is required.");
                } else if (password.isEmpty()) {
                    showErrorDialog("Password is required.");
                } else {
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        if (user != null) {
                                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                    .setDisplayName(name)
                                                    .build();
                                            user.updateProfile(profileUpdates);
                                        }
                                        mListener.onCreateAccountSuccess();
                                    } else {
                                        String errorMessage = task.getException() != null ?
                                                task.getException().getMessage() : "Registration failed.";
                                        showErrorDialog(errorMessage);
                                    }
                                }
                            });
                }
            }
        });
    }

    private void showErrorDialog(String message) {
        if (getContext() != null) {
            new AlertDialog.Builder(getContext())
                    .setTitle("Error")
                    .setMessage(message)
                    .setPositiveButton("OK", null)
                    .show();
        }
    }

    RegisterListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (RegisterListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement RegisterListener");
        }
    }

    public interface RegisterListener {
        void onCreateAccountSuccess();
        void gotoLoginAccount();
    }
}
