/*
 * Assignment 5
 * File Name: ProfileFragment.java
 * Full Name: Lucnel Nordelus
 */

package com.example.projectapp5;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    private static final String ARG_USER = "ARG_USER";
    private User mUser;
    private ProfileListener listener;

    public interface ProfileListener {
        void onCloseProfile();
    }

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(User user) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ProfileListener) {
            listener = (ProfileListener) context;
        } else {
            throw new RuntimeException(context + " must implement ProfileListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUser = (User) getArguments().getSerializable(ARG_USER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView textViewName = view.findViewById(R.id.textViewName);
        TextView textViewEmail = view.findViewById(R.id.textViewEmail);
        TextView textViewRole = view.findViewById(R.id.textViewRole);
        TextView textViewIncome = view.findViewById(R.id.textViewIncome);
        TextView textViewStatus = view.findViewById(R.id.textViewStatus);
        Button buttonClose = view.findViewById(R.id.buttonClose);

        if (mUser != null) {
            textViewName.setText(mUser.getName());
            textViewEmail.setText(mUser.getEmail());
            textViewRole.setText(mUser.getRole());
            textViewIncome.setText(mUser.getIncome());
            textViewStatus.setText(mUser.getStatus());
        }
        else {
            Toast.makeText(getContext(), "User not displaying, User object not created", Toast.LENGTH_SHORT).show();
        }

        buttonClose.setOnClickListener(v -> {
            if (listener != null) {
                listener.onCloseProfile();
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}