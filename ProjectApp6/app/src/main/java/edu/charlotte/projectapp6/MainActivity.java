package edu.charlotte.projectapp6;

/*
 * Assignment #6
 * File Name: MainActivity.java
 * Full Name: Bryan Sandoval, Lucnel Nordelus
 */

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements FragmentGenres.FragmentGenresListener, FragmentBooks.FragmentBooksListener, FragmentBookDetails.FragmentBookDetailsListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main, new FragmentGenres())
                    .commit();
        }
    }

    @Override
    public void onGenreSelected(String genre) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, FragmentBooks.newInstance(genre))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBookSelected(Book book) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, FragmentBookDetails.newInstance(book))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBooksBackClicked() {
        getSupportFragmentManager().popBackStack();
    }
    @Override
    public void onBooksDetailsBackClicked() {
        getSupportFragmentManager().popBackStack();
    }
}
