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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * FragmentBooks displays a list of books filtered by a selected genre.
 * 
 * DEV NOTES:
 * 1. Data Passing Down: Receives genre via Bundle arguments using static newInstance(genre).
 * 2. Custom Adapter: Implements inner class BooksAdapter (extends ArrayAdapter<Book>) 
 *    to bind custom Book objects to book_list_item.xml rows using convertView recycling.
 * 3. Interface Communication: Uses FragmentBooksListener to notify Activity of book clicks or back button.
 */
public class FragmentBooks extends Fragment {

    private static final String ARG_GENRE = "genre";

    private String mGenre;
    private TextView textViewGenreTitle;
    private ListView listViewBooks;
    private Button buttonBackBooks;

    private ArrayList<Book> books;
    private BooksAdapter adapter;
    private FragmentBooksListener mListener;

    public interface FragmentBooksListener {
        void onBookSelected(Book book);
        void onBooksBackClicked();
    }

    public FragmentBooks() {
        // Required empty public constructor
    }

    /**
     * Static factory method to create FragmentBooks and pass the selected genre in a Bundle.
     * This is best practice because Android can restore these arguments during configuration changes.
     */
    public static FragmentBooks newInstance(String genre) {
        FragmentBooks fragment = new FragmentBooks();
        Bundle args = new Bundle();
        args.putString(ARG_GENRE, genre);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mGenre = getArguments().getString(ARG_GENRE);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FragmentBooksListener) {
            mListener = (FragmentBooksListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement FragmentBooksListener");
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
        return inflater.inflate(R.layout.fragment_books, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textViewGenreTitle = view.findViewById(R.id.textViewGenreTitle);
        listViewBooks = view.findViewById(R.id.listViewBooks);
        buttonBackBooks = view.findViewById(R.id.buttonBackBooks);

        // Fetch books corresponding to the passed genre
        if (mGenre != null) {
            textViewGenreTitle.setText(mGenre);
            books = Data.getBooksByGenre(mGenre);
        } else {
            books = new ArrayList<>();
        }

        if (books == null) {
            books = new ArrayList<>();
        }

        // Initialize custom adapter and attach to ListView
        adapter = new BooksAdapter(requireContext(), books);
        listViewBooks.setAdapter(adapter);

        // Item click listener for ListView rows
        listViewBooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book selectedBook = books.get(position);
                if (mListener != null) {
                    mListener.onBookSelected(selectedBook);
                }
            }
        });

        // Back button click listener
        buttonBackBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onBooksBackClicked();
                }
            }
        });
    }

    /**
     * Custom ArrayAdapter for Book objects.
     * Demonstrates getView() layout inflation and convertView recycling for performance.
     */
    private class BooksAdapter extends ArrayAdapter<Book> {

        public BooksAdapter(@NonNull Context context, @NonNull List<Book> objects) {
            super(context, 0, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            // convertView recycling: Reuse views that have scrolled off-screen
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.book_list_item, parent, false);
            }

            Book book = getItem(position);

            TextView textViewTitle = convertView.findViewById(R.id.textViewTitle);
            TextView textViewAuthor = convertView.findViewById(R.id.textViewAuthor);
            TextView textViewGenre = convertView.findViewById(R.id.textViewGenre);
            TextView textViewYear = convertView.findViewById(R.id.textViewYear);

            if (book != null) {
                textViewTitle.setText(book.getTitle());
                textViewAuthor.setText(book.getAuthor());
                textViewGenre.setText(book.getGenre());
                textViewYear.setText(String.valueOf(book.getYear()));
            }

            return convertView;
        }
    }
}
