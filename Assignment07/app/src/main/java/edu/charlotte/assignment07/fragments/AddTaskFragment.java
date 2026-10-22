/**
 * Assignment: 7
 * File Name: AddTaskFragment.java
 * Student Name: Lucnel Nordelus and Bryan Sandoval
 *
 * Description: Part 4 implementation of Assignment 7.
 * Handles user input validation, priority and category selection navigation,
 * task creation in Cloud Firestore associated with authenticated user ID,
 * prevention of duplicate submissions, error handling, diagnostic logging,
 * and lifecycle-safe backstack navigation.
 */
package edu.charlotte.assignment07.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import edu.charlotte.assignment07.databinding.FragmentAddTaskBinding;
import edu.charlotte.assignment07.models.Priority;
import edu.charlotte.assignment07.models.Task;

public class AddTaskFragment extends Fragment {
    private static final String TAG = "AddTaskFragment";

    private Priority selectedPriority;
    private String selectedCategory;
    private FragmentAddTaskBinding binding;
    private AddTaskListener mListener;
    private boolean isSubmitting = false;

    /**
     * Sets the selected priority and updates the UI.
     */
    public void setSelectedPriority(Priority selectedPriority) {
        this.selectedPriority = selectedPriority;
        updateSelectionUI();
    }

    /**
     * Sets the selected category and updates the UI.
     */
    public void setSelectedCategory(String selectedCategory) {
        this.selectedCategory = selectedCategory;
        updateSelectionUI();
    }

    /**
     * Helper method to display selected category and priority names in the UI.
     */
    private void updateSelectionUI() {
        if (binding != null) {
            if (selectedCategory != null) {
                binding.textViewCategory.setText(selectedCategory);
            } else {
                binding.textViewCategory.setText("N/A");
            }

            if (selectedPriority != null) {
                binding.textViewPriority.setText(selectedPriority.getName());
            } else {
                binding.textViewPriority.setText("N/A");
            }
        }
    }

    public AddTaskFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddTaskBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Display category and priority selections if available
        updateSelectionUI();

        // Navigate to Category Selection screen through MainActivity
        binding.buttonSelectCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectCategory();
            }
        });

        // Navigate to Priority Selection screen through MainActivity
        binding.buttonSelectPriority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectPriority();
            }
        });

        // Submit button click listener handling validation, duplicate prevention, and Firestore task save
        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Prevent duplicate form submissions while saving
                if (isSubmitting) {
                    return;
                }

                String name = binding.editTextName.getText().toString().trim();

                // Validate task name, category, and priority
                if (name.isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter a task name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (selectedCategory == null) {
                    Toast.makeText(getActivity(), "Please select a category", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (selectedPriority == null) {
                    Toast.makeText(getActivity(), "Please select a priority", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Verify user authentication state
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if (currentUser == null) {
                    Toast.makeText(getActivity(), "User not authenticated", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Lock UI submission controls and display saving indicator
                isSubmitting = true;
                binding.buttonSubmit.setEnabled(false);
                binding.buttonSubmit.setText("Saving...");
                if (getContext() != null) {
                    Toast.makeText(getContext(), "Saving task...", Toast.LENGTH_SHORT).show();
                }

                // Create document reference in Firestore "tasks" collection
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference docRef = db.collection("tasks").document();
                String docId = docRef.getId();

                Log.d(TAG, "Attempting to save task '" + name + "' for user " + currentUser.getUid() + " with doc ID: " + docId);

                // Instantiate Task model object with user input and current user UID
                Task task = new Task(name, selectedCategory, selectedPriority.getName(), currentUser.getUid());
                task.setId(docId);

                // Save task to Cloud Firestore
                docRef.set(task)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "Task saved successfully to Firestore with doc ID: " + docId);
                                if (isAdded() && getContext() != null) {
                                    Toast.makeText(getContext(), "Task added successfully", Toast.LENGTH_SHORT).show();
                                }
                                // Lifecycle-safe check before invoking listener navigation
                                if (isAdded() && mListener != null) {
                                    mListener.onTaskAdded(task);
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e(TAG, "Failed to save task to Firestore with doc ID: " + docId, e);
                                // Unlock UI controls on error
                                isSubmitting = false;
                                if (isAdded() && binding != null) {
                                    binding.buttonSubmit.setEnabled(true);
                                    binding.buttonSubmit.setText("Submit");
                                }
                                if (isAdded() && getContext() != null) {
                                    Toast.makeText(getContext(), "Error saving task: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

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
