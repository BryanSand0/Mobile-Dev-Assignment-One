package edu.charlotte.assignment07.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import edu.charlotte.assignment07.R;
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
                String name = String.valueOf(binding.editTextText.getText());
                String email = String.valueOf(binding.editTextEmailAddress.getText());
                String password = String.valueOf(binding.editTextPassword.getText());

                if (name.length() == 0) {
                    Toast.makeText(v.getContext(), "Please enter your full name", Toast.LENGTH_SHORT).show();
                }
                else if (email.length() == 0) {
                    Toast.makeText(v.getContext(), "Please enter an email address", Toast.LENGTH_SHORT).show();
                }
                else if (password.length() == 0) {
                    Toast.makeText(v.getContext(), "Please enter your password", Toast.LENGTH_SHORT).show();
                }
                else {
                    //TODO: Do something with the full name when creating a user too
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        mListener.onCreateAccountSuccess();
                                    }
                                    else {
                                        String errorMessage = task.getException().getMessage();
                                        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
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