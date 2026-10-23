/**
 * Assignment: 7
 * File Name: SelectPriorityFragment.java
 * Student Name: Lucnel Nordelus and Bryan Sandoval
 *
 */
package edu.charlotte.assignment07.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import edu.charlotte.assignment07.R;
import edu.charlotte.assignment07.databinding.FragmentSelectPriorityBinding;
import edu.charlotte.assignment07.models.Data;
import edu.charlotte.assignment07.models.Priority;


public class SelectPriorityFragment extends Fragment {

    public SelectPriorityFragment() {
        // Required empty public constructor
    }

    FragmentSelectPriorityBinding binding;
    ArrayAdapter<Priority> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSelectPriorityBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, Data.priorities);
        binding.listView.setAdapter(adapter);
        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.onPrioritySelected(adapter.getItem(position));
            }
        });

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onCancelSelection();
            }
        });

    }

    SelectPriorityListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof SelectPriorityListener) {
            mListener = (SelectPriorityListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement SelectPriorityListener");
        }
    }

    public interface SelectPriorityListener {
        void onPrioritySelected(Priority priority);
        void onCancelSelection();
    }
}