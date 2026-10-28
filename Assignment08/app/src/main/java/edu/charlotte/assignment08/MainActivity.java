/**
 * Assignment #: Assignment 8
 * File Name: MainActivity.java
 * Full Name: [Student Name]
 */

package edu.charlotte.assignment08;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements
        LoginFragment.LoginFragmentListener,
        RegisterFragment.RegisterFragmentListener,
        ForumsFragment.ForumsFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Perform initial fragment selection on startup
        if (savedInstanceState == null) {
            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                // User is already logged in -> Navigate directly to ForumsFragment
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, ForumsFragment.newInstance())
                        .commit();
            } else {
                // User is not logged in -> Display LoginFragment
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, LoginFragment.newInstance())
                        .commit();
            }
        }
    }


    private void clearBackStack() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }


    @Override
    public void gotoRegister() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, RegisterFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoForums() {
        clearBackStack();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, ForumsFragment.newInstance())
                .commit();
    }


    @Override
    public void gotoLogin() {
        // Return to LoginFragment
        clearBackStack();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, LoginFragment.newInstance())
                .commit();
    }


    @Override
    public void logout() {
        // Sign out user from Firebase Authentication and return to LoginFragment
        FirebaseAuth.getInstance().signOut();
        clearBackStack();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, LoginFragment.newInstance())
                .commit();
    }

    @Override
    public void gotoNewForum() {
        Toast.makeText(this, "New Forum screen navigation triggered.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void gotoForumDetails(String forumId) {
        Toast.makeText(this, "Forum Details screen navigation triggered.", Toast.LENGTH_SHORT).show();
    }
}
