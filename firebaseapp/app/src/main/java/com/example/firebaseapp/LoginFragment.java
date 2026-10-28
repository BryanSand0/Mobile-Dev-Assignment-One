package com.example.firebaseapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;


public class LoginFragment extends Fragment {

    private FirebaseAuth mAuth;
    final private String TAG = "demo";



    public LoginFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState);}

    EditText editTextTextEmailAddress, editTextTextPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        editTextTextEmailAddress = view.findViewById(R.id.editTextTextEmailAddress);
        editTextTextPassword = view.findViewById(R.id.editTextTextPassword);

        view.findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = editTextTextEmailAddress.getText().toString();
                String password = editTextTextPassword.getText().toString();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getActivity(), "Enter Email", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                    Toast.makeText(getActivity(), "Enter Password", Toast.LENGTH_SHORT).show();
                } else {

                    mAuth = FirebaseAuth.getInstance();

                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                               Log.d(TAG,"onComplete: Logged In Successfully");
                                assert mAuth.getCurrentUser() != null;
                                Log.d(TAG,"onComplete: " + mAuth.getCurrentUser().getUid());
                            } else {
                                Log.d(TAG,"onComplete: Error !!");
                                String errorMessage = Objects.requireNonNull(task.getException()).getMessage();
                                Log.d(TAG,"onComplete: " + errorMessage);
                                Toast.makeText(getActivity(), "Login Failed: " + errorMessage, Toast.LENGTH_LONG).show();
                            }

                        }
                    });

                }
                }
        });


        view.findViewById(R.id.buttonRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });


        return view;
    }
}