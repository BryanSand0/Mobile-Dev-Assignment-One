package edu.charlotte.projectapp6;

/*
 * Assignment #6
 * File Name: MainActivity.java
 * Full Name: Bryan Sandoval, Lucnel Nordelus
 */

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

/**
 * MainActivity acts as the Host Activity in a Single-Activity Architecture.
 * 
 * DEV NOTES - FRAGMENT NAVIGATION & INTERFACES:
 * 1. This Activity implements listener interfaces from all three fragments:
 *    - FragmentGenres.FragmentGenresListener
 *    - FragmentBooks.FragmentBooksListener
 *    - FragmentBookDetails.FragmentBookDetailsListener
 * 2. When user actions occur inside fragments, callbacks are triggered here in the Activity,
 *    allowing MainActivity to handle fragment transitions and back-stack management safely.
 */
public class MainActivity extends AppCompatActivity implements 
        FragmentGenres.FragmentGenresListener, 
        FragmentBooks.FragmentBooksListener, 
        FragmentBookDetails.FragmentBookDetailsListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // If savedInstanceState is null, this is the first launch.
        // Load the initial FragmentGenres into the container view (R.id.main).
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main, new FragmentGenres())
                    .commit();
        }
    }

    /**
     * Callback from FragmentGenres when a genre is clicked.
     * Passes the selected genre string to FragmentBooks using FragmentBooks.newInstance(genre).
     */
    @Override
    public void onGenreSelected(String genre) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, FragmentBooks.newInstance(genre))
                .addToBackStack(null) // Adds transaction to back stack so back button works
                .commit();
    }

    /**
     * Callback from FragmentBooks when a book is clicked.
     * Passes the selected Book object (Serializable) to FragmentBookDetails.
     */
    @Override
    public void onBookSelected(Book book) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, FragmentBookDetails.newInstance(book))
                .addToBackStack(null)
                .commit();
    }

    /**
     * Callback from FragmentBooks when the Back button is clicked.
     * Pops the back stack to return to FragmentGenres.
     */
    @Override
    public void onBooksBackClicked() {
        getSupportFragmentManager().popBackStack();
    }

    /**
     * Callback from FragmentBookDetails when the Back button is clicked.
     * Pops the back stack to return to FragmentBooks.
     */
    @Override
    public void onBooksDetailsBackClicked() {
        getSupportFragmentManager().popBackStack();
    }
}
