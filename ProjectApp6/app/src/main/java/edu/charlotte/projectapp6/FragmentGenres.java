package edu.charlotte.projectapp6;

/*
 * Assignment #6
 * File Name: FragmentGenres.java
 * Full Name: Bryan Sandoval, Lucnel Nordelus
 */

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * FragmentGenres displays a list of book genres using a standard ListView and ArrayAdapter.
 * 
 * DEV NOTES - LISTVIEWS, ADAPTERS & INTERFACES:
 * 1. Interface communication: Defines FragmentGenresListener so the fragment can notify
 *    MainActivity when a genre is selected without holding a direct reference to the activity.
 * 2. Adapter: Uses a built-in ArrayAdapter with android.R.layout.simple_list_item_1 to display strings.
 */
public class FragmentGenres extends Fragment {

    Data data;
    final String TAG = "Main";
    ListView listView;
    ArrayList<String> genres;
    ArrayAdapter<String> adapter;
    FragmentGenresListener mListener;

    /**
     * Fragment-to-Activity Communication Interface.
     * The host Activity must implement this interface.
     */
    public interface FragmentGenresListener {
        void onGenreSelected(String genre);
    }

    public FragmentGenres() {
        // Required empty public constructor
    }

    public static FragmentGenres newInstance() {
        return new FragmentGenres();
    }

    /**
     * onAttach is called when the fragment is first attached to its context (Activity).
     * We verify that the hosting Activity implements our listener interface here.
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FragmentGenresListener) {
            mListener = (FragmentGenresListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement FragmentGenresListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null; // Prevent memory leaks
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_genres, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        // Load data and setup ListView
        data = new Data();
        genres = data.getAllGenres();
        listView = view.findViewById(R.id.listViewGenres);
        
        // ArrayAdapter bridges the ArrayList<String> data to the ListView rows
        adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, genres);
        listView.setAdapter(adapter);

        // Handle item clicks on the ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mListener != null) {
                    // Pass the selected genre string up to MainActivity via the interface callback
                    mListener.onGenreSelected(genres.get(position));
                }
            }
        });
    }
}
