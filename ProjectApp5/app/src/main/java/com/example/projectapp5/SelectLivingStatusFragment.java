/*
 * Assignment 5
 * File Name: SelectLivingStatusFragment.java
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

public class SelectLivingStatusFragment extends Fragment {

    private SelectLivingStatusListener listener;

    public interface SelectLivingStatusListener {
        void onLivingStatusSelected(String status);
        void onLivingStatusSelectionCancelled();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof SelectLivingStatusListener) {
            listener = (SelectLivingStatusListener) context;
        } else {
            throw new RuntimeException(context + " must implement SelectLivingStatusListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_select_living_status, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button buttonHomeowner = view.findViewById(R.id.buttonHomeowner);
        Button buttonRenter = view.findViewById(R.id.buttonRenter);
        Button buttonLessee = view.findViewById(R.id.buttonLessee);
        Button buttonOther = view.findViewById(R.id.buttonOther);
        Button buttonPreferNotToSay = view.findViewById(R.id.buttonPreferNotToSay);
        Button buttonCancel = view.findViewById(R.id.buttonCancel);

        buttonHomeowner.setOnClickListener(v -> listener.onLivingStatusSelected("Homeowner"));
        buttonRenter.setOnClickListener(v -> listener.onLivingStatusSelected("Renter"));
        buttonLessee.setOnClickListener(v -> listener.onLivingStatusSelected("Lessee"));
        buttonOther.setOnClickListener(v -> listener.onLivingStatusSelected("Other"));
        buttonPreferNotToSay.setOnClickListener(v -> listener.onLivingStatusSelected("Prefer not to say"));

        buttonCancel.setOnClickListener(v -> listener.onLivingStatusSelectionCancelled());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}