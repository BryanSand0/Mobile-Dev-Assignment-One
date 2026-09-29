package edu.charlotte.projectapp6;

/*
 * Assignment #6
 * File Name: MainActivity.java
 * Full Name: Bryan Sandoval
 */

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

    //Bryan's TODO: Create a function that pushes FragmentGenres to the backstack, sends the genre selected to Books Fragment
}