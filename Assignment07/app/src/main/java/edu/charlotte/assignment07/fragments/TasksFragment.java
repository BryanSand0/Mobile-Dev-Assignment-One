/**
 * Assignment: 7
 * File Name: TasksFragment.java
 * Student Name: Lucnel Nordelus and Bryan Sandoval
 *
 */
package edu.charlotte.assignment07.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import edu.charlotte.assignment07.databinding.FragmentTasksBinding;
import edu.charlotte.assignment07.databinding.TaskListItemBinding;
import edu.charlotte.assignment07.models.Task;

public class TasksFragment extends Fragment {

    public TasksFragment() {
        // Required empty public constructor
    }

    private FragmentTasksBinding binding;
    private TasksAdapter adapter;
    private final ArrayList<Task> mTasks = new ArrayList<>();
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private ListenerRegistration listenerRegistration;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTasksBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize Firebase Auth and Firestore instances
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Initialize RecyclerView with LinearLayoutManager and adapter
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TasksAdapter();
        binding.recyclerView.setAdapter(adapter);

        // Navigate to Add Task screen via MainActivity callback
        binding.buttonAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoAddTask();
            }
        });

        // Sign out authenticated user and navigate back to Login screen
        binding.buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                mListener.logoutGotoLogin();
            }
        });

        // Retrieve current user and setup Firestore snapshot listener for real-time task updates
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            listenerRegistration = db.collection("tasks")
                    .whereEqualTo("userId", currentUser.getUid())
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            if (error != null) {
                                if (getContext() != null) {
                                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                                return;
                            }
                            mTasks.clear();
                            if (value != null) {
                                for (QueryDocumentSnapshot doc : value) {
                                    Task task = doc.toObject(Task.class);
                                    task.setId(doc.getId());
                                    mTasks.add(task);
                                }
                            }
                            // Refresh RecyclerView with updated tasks
                            adapter.notifyDataSetChanged();
                        }
                    });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Remove Firestore snapshot listener to avoid memory leaks
        if (listenerRegistration != null) {
            listenerRegistration.remove();
            listenerRegistration = null;
        }
        binding = null;
    }

    /**
     * RecyclerView Adapter to render the user's task items.
     */
    class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskViewHolder> {
        @NonNull
        @Override
        public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            TaskListItemBinding itemBinding = TaskListItemBinding.inflate(getLayoutInflater(), parent, false);
            return new TaskViewHolder(itemBinding);
        }

        @Override
        public int getItemCount() {
            return mTasks.size();
        }

        @Override
        public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
            Task task = mTasks.get(position);
            holder.bind(task);
        }

        class TaskViewHolder extends RecyclerView.ViewHolder {
            TaskListItemBinding itemBinding;

            public TaskViewHolder(TaskListItemBinding itemBinding) {
                super(itemBinding.getRoot());
                this.itemBinding = itemBinding;
            }

            public void bind(Task task) {
                // Set task details into UI components
                itemBinding.textViewName.setText(task.getName());
                itemBinding.textViewCategory.setText(task.getCategory());
                itemBinding.textViewPriority.setText(task.getPriority());

                // Handle delete icon click to remove selected task from Firestore
                itemBinding.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseUser currentUser = mAuth.getCurrentUser();
                        // Verify task ownership before deleting
                        if (currentUser != null && task.getUserId() != null && task.getUserId().equals(currentUser.getUid())) {
                            db.collection("tasks").document(task.getId()).delete()
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            if (getContext() != null) {
                                                Toast.makeText(getContext(), "Error deleting task: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                    }
                });
            }
        }
    }

    TasksListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof TasksListener) {
            mListener = (TasksListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement TasksListener");
        }
    }

    public interface TasksListener {
        void gotoAddTask();
        void logoutGotoLogin();
    }
}
