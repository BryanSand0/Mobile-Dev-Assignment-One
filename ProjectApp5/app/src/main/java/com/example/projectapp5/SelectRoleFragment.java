/*
 * Assignment #5
 * File Name: SelectRoleFragment.java
 * Full Name: Bryan Sandoval & Lucnel Nordelus
 */

package com.example.projectapp5;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SelectRoleFragment extends Fragment {

    private SelectRoleListener listener;
    private RadioGroup radioGroup;

    public interface SelectRoleListener {
        void onRoleSelected(String role);
        void onRoleSelectionCancelled();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof SelectRoleListener) {
            listener = (SelectRoleListener) context;
        } else {
            throw new RuntimeException(context + " must implement SelectRoleListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_select_role, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        radioGroup = view.findViewById(R.id.radioGroup);
        Button buttonSubmit = view.findViewById(R.id.buttonSubmit);
        Button buttonCancel = view.findViewById(R.id.buttonCancel);

        buttonSubmit.setOnClickListener(v -> {
            int selectedId = radioGroup.getCheckedRadioButtonId();
            if (selectedId == -1) {
                Toast.makeText(getContext(), "Please select a role", Toast.LENGTH_SHORT).show();
                return;
            }

            String role;
            if (selectedId == R.id.radioButtonStudent) {
                role = "Student";
            } else if (selectedId == R.id.radioButtonEmployee) {
                role = "Employee";
            } else {
                role = "Other";
            }

            listener.onRoleSelected(role);
        });

        buttonCancel.setOnClickListener(v -> listener.onRoleSelectionCancelled());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}