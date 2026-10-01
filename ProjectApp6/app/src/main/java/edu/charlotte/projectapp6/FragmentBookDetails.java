package edu.charlotte.projectapp6;

/*
 * Assignment #6
 * File Name: FragmentBookDetails.java
 * Full Name: Bryan Sandoval, Lucnel Nordelus
 */

import android.content.Context;
import android.os.Build;
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

    private static final String ARG_BOOK = "book";

    private FragmentBookDetailsListener mListener;
    private Book bookViewing;
    private Button backButton;
    private TextView titleText;
    private TextView authorNameText;
    private TextView genreText;
    private TextView yearText;

    public static FragmentBookDetails newInstance(Book book) {
        FragmentBookDetails fragment = new FragmentBookDetails();
        Bundle args = new Bundle();
        args.putSerializable(ARG_BOOK, book);
        fragment.setArguments(args);
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
        if (getArguments() != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bookViewing = getArguments().getSerializable(ARG_BOOK, Book.class);
            } else {
                bookViewing = (Book) getArguments().getSerializable(ARG_BOOK);
            }
        }
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
        if (bookViewing != null) {
            titleText.setText(bookViewing.getTitle());
            authorNameText.setText(bookViewing.getAuthor());
            genreText.setText(bookViewing.getGenre());
            yearText.setText(String.valueOf(bookViewing.getYear()));
        }

        //Set a on click listner for a back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onBooksDetailsBackClicked();
                }
            }
        });
    }
}