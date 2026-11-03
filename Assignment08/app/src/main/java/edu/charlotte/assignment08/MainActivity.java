/**
 * Assignment #: Assignment 8
 * File Name: MainActivity.java
 * Full Name: [Student Name]
 */

package edu.charlotte.assignment08;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements
        LoginFragment.LoginFragmentListener,
        RegisterFragment.RegisterFragmentListener,
        ForumsFragment.ForumsFragmentListener,
        NewForumFragment.NewForumFragmentListener,
        ForumDetailsFragment.ForumDetailsFragmentListener {

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
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, NewForumFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void submitForum(String title, String desc) {
        gotoForums();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            Map<String, Object> forum = new HashMap<>();
            forum.put("title", title);
            forum.put("description", desc);
            forum.put("createdAt", Timestamp.now());
            forum.put("creatorId", auth.getCurrentUser().getUid());
            forum.put("creatorName", auth.getCurrentUser().getDisplayName());

            String id = db.collection("forums").document().getId();
            db.collection("forums").document(id).set(forum)
                    .addOnSuccessListener(unused -> Toast.makeText(MainActivity.this, "Post made!", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show());
        } else {
            Toast.makeText(this, "Must be signed in to make forum posts", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void gotoForumDetails(String forumId) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new ForumDetailsFragment(forumId))
                .addToBackStack(null)
                .commit();
    }
}
