package edu.charlotte.projectapp6;

/*
 * Assignment #6
 * File Name: FragmentBooks.java
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
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentBookDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBookDetails extends Fragment {


    FragmentBookDetailsListener mListener;
    static Book bookViewing;
    Button backButton;
    TextView titleText;
    TextView authorNameText;
    TextView genreText;
    TextView yearText;


    public static FragmentBookDetails newInstance(Book book) {

        Bundle args = new Bundle();

        FragmentBookDetails fragment = new FragmentBookDetails();
        fragment.setArguments(args);

        bookViewing = book;
        return fragment;
    }

    public interface FragmentBookDetailsListener {
        void onBooksDetailsBackClicked();
    }

    public FragmentBookDetails() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FragmentBookDetailsListener) {
            mListener = (FragmentBookDetailsListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement FragmentBookDetailsListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Get Components
        backButton = view.findViewById(R.id.buttonBackBookDetails);
        titleText = view.findViewById(R.id.textViewBookTitleDisplay);
        authorNameText = view.findViewById(R.id.textViewAuthorNameDisplay);
        genreText = view.findViewById(R.id.textViewGenreDisplay);
        yearText = view.findViewById(R.id.textViewYearDisplay);

        //Set text for display
        titleText.setText(bookViewing.getTitle());
        authorNameText.setText(bookViewing.getAuthor());
        genreText.setText(bookViewing.getGenre());
        yearText.setText(String.valueOf(bookViewing.getYear()));

        //Set a on click listner for a back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onBooksDetailsBackClicked();
            }
        });
    }
}