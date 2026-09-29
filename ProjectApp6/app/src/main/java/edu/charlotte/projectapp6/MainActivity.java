package edu.charlotte.projectapp6;

/*
 * Assignment #6
 * File Name: MainActivity.java
 * Full Name: Bryan Sandoval
 */

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, new FragmentGenres())
                .commit();
    }
}