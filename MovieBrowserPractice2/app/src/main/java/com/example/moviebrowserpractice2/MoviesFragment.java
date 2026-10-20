package com.example.moviebrowserpractice2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class MoviesFragment extends Fragment {

    private static final String ARG_CATEGORY = "category";

    private String category;

    private TextView textViewCategoryTitle;
    private ListView listViewMovies;
    private Button buttonBackMovies;

    private ArrayList<Movie> movies;

    public MoviesFragment() {
        // Required empty public constructor
    }

    public static MoviesFragment newInstance(String category) {

        MoviesFragment fragment = new MoviesFragment();

        Bundle args = new Bundle();
        args.putString(ARG_CATEGORY, category);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            category = getArguments().getString(ARG_CATEGORY);
        }
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        return inflater.inflate(
                R.layout.fragment_movies,
                container,
                false
        );
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        textViewCategoryTitle =
                view.findViewById(R.id.textViewCategoryTitle);

        listViewMovies =
                view.findViewById(R.id.listViewMovies);

        buttonBackMovies =
                view.findViewById(R.id.buttonBackMovies);

        textViewCategoryTitle.setText(category);

        movies = Data.getMoviesByCategory(category);
    }


}