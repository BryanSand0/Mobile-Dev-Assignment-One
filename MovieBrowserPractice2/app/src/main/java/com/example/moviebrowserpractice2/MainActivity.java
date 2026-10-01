package com.example.moviebrowserpractice2;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements CategoriesFragment.CategoriesFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, new CategoriesFragment())
                .commit();
    }

//    @Override
//    public void onCategorySelected(String category) {
//
//        Toast.makeText(
//                this,
//                "Selected: " + category,
//                Toast.LENGTH_SHORT
//        ).show();
//
//    }

    @Override
    public void onCategorySelected(String category) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(
                        R.id.main,
                        MoviesFragment.newInstance(category)
                )
                .addToBackStack(null)
                .commit();
    }
}