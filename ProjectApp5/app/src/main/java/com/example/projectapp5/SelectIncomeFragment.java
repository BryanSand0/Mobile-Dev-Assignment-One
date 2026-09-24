/*
 * Assignment 5
 * File Name: SelectIncomeFragment.java
 * Full Name: Lucnel Nordelus
 */

package com.example.projectapp5;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SelectIncomeFragment extends Fragment {

    private SelectIncomeListener listener;
    private SeekBar seekBar;
    private TextView textViewSeekProgress;

    private static final String[] INCOME_RANGES = {
            "< $25k",
            "$25k - $50k",
            "$50k - $75k",
            "$75k - $100k",
            "$100k - $125k",
            "$125k - $150k",
            "$150k - $175k",
            "$175k - $200k",
            "$200k - $250k",
            "$250k - $300k",
            "> $300k"
    };

    public interface SelectIncomeListener {
        void onIncomeSelected(String income);
        void onIncomeSelectionCancelled();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof SelectIncomeListener) {
            listener = (SelectIncomeListener) context;
        } else {
            throw new RuntimeException(context + " must implement SelectIncomeListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_select_income, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        seekBar = view.findViewById(R.id.seekBar);
        textViewSeekProgress = view.findViewById(R.id.textViewSeekProgress);
        Button buttonSubmit = view.findViewById(R.id.buttonSubmit);
        Button buttonCancel = view.findViewById(R.id.buttonCancel);

        // Initial setting based on default seekbar progress
        updateProgressText(seekBar.getProgress());

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateProgressText(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        buttonSubmit.setOnClickListener(v -> {
            int progress = seekBar.getProgress();
            String income = INCOME_RANGES[Math.min(progress, INCOME_RANGES.length - 1)];
            listener.onIncomeSelected(income);
        });

        buttonCancel.setOnClickListener(v -> listener.onIncomeSelectionCancelled());
    }

    private void updateProgressText(int progress) {
        if (progress >= 0 && progress < INCOME_RANGES.length) {
            textViewSeekProgress.setText(INCOME_RANGES[progress]);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}