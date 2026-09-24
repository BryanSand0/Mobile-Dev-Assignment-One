/*
 * Assignment 5
 * File Name: WelcomeFragment.java
 * Full Name: Lucnel Nordelus
 */

package com.example.projectapp5;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class WelcomeFragment extends Fragment {

    private WelcomeListener listener;

    public interface WelcomeListener {
        void onStartClicked();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof WelcomeListener) {
            listener = (WelcomeListener) context;
        } else {
            throw new RuntimeException(context + " must implement WelcomeListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_welcome, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button buttonStart = view.findViewById(R.id.buttonStart);
        buttonStart.setOnClickListener(v -> listener.onStartClicked());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}