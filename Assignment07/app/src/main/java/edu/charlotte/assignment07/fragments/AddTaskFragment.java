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

import edu.charlotte.assignment07.R;
import edu.charlotte.assignment07.databinding.FragmentAddTaskBinding;
import edu.charlotte.assignment07.models.Priority;
import edu.charlotte.assignment07.models.Task;

public class AddTaskFragment extends Fragment {
    private Priority selectedPriority;
    private String selectedCategory;

    public void setSelectedPriority(Priority selectedPriority) {
        this.selectedPriority = selectedPriority;
    }

    public void setSelectedCategory(String selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    public AddTaskFragment() {
        // Required empty public constructor
    }

    FragmentAddTaskBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddTaskBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonSelectCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectCategory();
            }
        });

        binding.buttonSelectPriority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectPriority();
            }
        });

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.editTextName.getText().toString();
                if(name.isEmpty() || selectedCategory == null || selectedPriority == null) {
                    Toast.makeText(getActivity(), "Enter Name !!", Toast.LENGTH_SHORT).show();
                } else if(selectedCategory == null) {
                    Toast.makeText(getActivity(), "Select Category !!", Toast.LENGTH_SHORT).show();
                } else if(selectedPriority == null) {
                    Toast.makeText(getActivity(), "Select Priority !!", Toast.LENGTH_SHORT).show();
                } else {
                    //Task task = new Task(name, selectedCategory, selectedPriority);
                    //mListener.onTaskAdded(task);
                }
            }
        });

        if(selectedCategory != null) {
            binding.textViewCategory.setText(selectedCategory);
        } else {
            binding.textViewCategory.setText("N/A");
        }

        if(selectedPriority != null) {
            binding.textViewPriority.setText(selectedPriority.getName());
        } else {
            binding.textViewPriority.setText("N/A");
        }
    }

    AddTaskListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddTaskListener) {
            mListener = (AddTaskListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement AddTaskListener");
        }
    }

    public interface AddTaskListener {
        void gotoSelectPriority();
        void gotoSelectCategory();
        void onTaskAdded(Task task);
        void onCancelSelection();
    }
}