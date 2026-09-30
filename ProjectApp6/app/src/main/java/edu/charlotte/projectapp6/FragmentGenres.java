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

public class FragmentGenres extends Fragment {

    Data data;
    final String TAG = "Main";
    ListView listView;
    ArrayList<String> genres;
    ArrayAdapter<String> adapter;
    FragmentGenresListener mListener;

    public interface FragmentGenresListener {
        void onGenreSelected(String genre);
    }

    public FragmentGenres() {
        // Required empty public constructor
    }

    public static FragmentGenres newInstance() {
        return new FragmentGenres();
    }

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
        mListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_genres, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        data = new Data();
        genres = data.getAllGenres();
        listView = view.findViewById(R.id.listViewGenres);
        adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, genres);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mListener != null) {
                    mListener.onGenreSelected(genres.get(position));
                }
            }
        });
    }
}
