/**
 * Assignment: 7
 * File Name: MainActivity.java
 * Student Name: Lucnel Nordelus and Bryan Sandoval
 *
 */
package edu.charlotte.assignment07;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

import edu.charlotte.assignment07.fragments.AddTaskFragment;
import edu.charlotte.assignment07.fragments.CreateAccountFragment;
import edu.charlotte.assignment07.fragments.LoginFragment;
import edu.charlotte.assignment07.fragments.SelectCategoryFragment;
import edu.charlotte.assignment07.fragments.SelectPriorityFragment;
import edu.charlotte.assignment07.fragments.TasksFragment;
import edu.charlotte.assignment07.models.Priority;
import edu.charlotte.assignment07.models.Task;


public class MainActivity extends AppCompatActivity implements SelectPriorityFragment.SelectPriorityListener, SelectCategoryFragment.SelectCategoryListener,
        AddTaskFragment.AddTaskListener, TasksFragment.TasksListener, LoginFragment.LoginListener, CreateAccountFragment.RegisterListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (savedInstanceState == null) {
            // Requirement 3: Main Activity should load the “Tasks” Fragment as the initial fragment (or check auth)
            // If user is already logged in, show TasksFragment, otherwise LoginFragment
            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main, new TasksFragment())
                        .commit();
            } else {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main, new LoginFragment())
                        .commit();
            }
        }
    }

    @Override
    public void onCategorySelected(String category) {
        AddTaskFragment addTaskFragment = (AddTaskFragment) getSupportFragmentManager().findFragmentByTag("addTaskFragment");
        if (addTaskFragment != null) {
            addTaskFragment.setSelectedCategory(category);
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onPrioritySelected(Priority priority) {
        AddTaskFragment addTaskFragment = (AddTaskFragment) getSupportFragmentManager().findFragmentByTag("addTaskFragment");
        if (addTaskFragment != null) {
            addTaskFragment.setSelectedPriority(priority);
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void gotoSelectPriority() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new SelectPriorityFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoSelectCategory() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new SelectCategoryFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onTaskAdded(Task task) {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onCancelSelection() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void gotoAddTask() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new AddTaskFragment(), "addTaskFragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void logoutGotoLogin() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new LoginFragment())
                .commit();
    }

    @Override
    public void onLoginSuccess() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new TasksFragment())
                .commit();
    }

    @Override
    public void onCreateAccountSuccess() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new TasksFragment())
                .commit();
    }

    @Override
    public void gotoLoginAccount() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new LoginFragment())
                .commit();
    }

    @Override
    public void gotoCreateAccount() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new CreateAccountFragment())
                .commit();
    }
}
