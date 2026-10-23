/**
 * Assignment: 7
 * File Name: LoginFragment.java
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

import edu.charlotte.assignment07.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {

    public LoginFragment() {
        // Required empty public constructor
    }

    FragmentLoginBinding binding;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoCreateAccount();
            }
        });

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = String.valueOf(binding.editTextEmailAddress.getText()).trim();
                String password = String.valueOf(binding.editTextPassword.getText()).trim();

                if (email.isEmpty()) {
                    showErrorDialog("Email is required.");
                } else if (password.isEmpty()) {
                    showErrorDialog("Password is required.");
                } else {
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        mListener.onLoginSuccess();
                                    } else {
                                        String errorMessage = task.getException() != null ?
                                                task.getException().getMessage() : "Login failed.";
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

    LoginListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (LoginListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement LoginListener");
        }
    }

    public interface LoginListener {
        void onLoginSuccess();
        void gotoCreateAccount();
    }
}
