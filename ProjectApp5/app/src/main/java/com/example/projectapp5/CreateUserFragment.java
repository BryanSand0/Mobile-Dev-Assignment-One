/*
 * Assignment #5
 * File Name: CreateUserFragment.java
 * Full Name: Bryan Sandoval & Lucnel Nordelus
 */

package com.example.projectapp5;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CreateUserFragment extends Fragment {

    private CreateUserListener listener;

    private EditText editTextName;
    private EditText editTextEmail;

    private TextView textViewRole;
    private TextView textViewIncome;
    private TextView textViewStatus;

    private String selectedRole;
    private String selectedIncome;
    private String selectedStatus;

    public interface CreateUserListener {
        void onSelectRole();
        void onSelectIncome();
        void onSelectStatus();
        void onUserCreated(User user);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof CreateUserListener) {
            listener = (CreateUserListener) context;
        } else {
            throw new RuntimeException(context + " must implement CreateUserListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextName = view.findViewById(R.id.editTextName);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        textViewRole = view.findViewById(R.id.textViewRole);
        textViewIncome = view.findViewById(R.id.textViewIncome);
        textViewStatus = view.findViewById(R.id.textViewStatus);

        Button buttonSelectRole = view.findViewById(R.id.buttonSelectRole);
        Button buttonSelectIncome = view.findViewById(R.id.buttonSelectIncome);
        Button buttonSelectStatus = view.findViewById(R.id.buttonSelectStatus);
        Button buttonNext = view.findViewById(R.id.buttonNext);

        buttonSelectRole.setOnClickListener(v -> listener.onSelectRole());
        buttonSelectIncome.setOnClickListener(v -> listener.onSelectIncome());
        buttonSelectStatus.setOnClickListener(v -> listener.onSelectStatus());

        buttonNext.setOnClickListener(v -> validateAndCreateUser());

        updateDisplayedValues();
    }

    private void validateAndCreateUser() {
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();

        if (name.isEmpty()) {
            Toast.makeText(getContext(), "Name is required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (email.isEmpty()) {
            Toast.makeText(getContext(), "Email is required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedRole == null) {
            Toast.makeText(getContext(), "Role is required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedIncome == null) {
            Toast.makeText(getContext(), "Income is required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedStatus == null) {
            Toast.makeText(getContext(), "Status is required", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = new User(name, email, selectedRole, selectedIncome, selectedStatus);
        listener.onUserCreated(user);
    }

    public void setRole(String role) {
        selectedRole = role;
        if (textViewRole != null) {
            textViewRole.setText(role);
        }
    }

    public void setIncome(String income) {
        selectedIncome = income;
        if (textViewIncome != null) {
            textViewIncome.setText(income);
        }
    }

    public void setStatus(String status) {
        selectedStatus = status;
        if (textViewStatus != null) {
            textViewStatus.setText(status);
        }
    }

    private void updateDisplayedValues() {
        if (selectedRole != null && textViewRole != null) {
            textViewRole.setText(selectedRole);
        }
        if (selectedIncome != null && textViewIncome != null) {
            textViewIncome.setText(selectedIncome);
        }
        if (selectedStatus != null && textViewStatus != null) {
            textViewStatus.setText(selectedStatus);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}