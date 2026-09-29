package edu.charlotte.projectapp6;

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
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentGenres#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentGenres extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    //Create Variables
    Data data;
    final String TAG = "Main";
    ListView listView;
    ArrayList<String> genres;
    ArrayAdapter<String> adapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //Create listener interface

    public FragmentGenres() {
        // Required empty public constructor
    }
    public static FragmentGenres newInstance(String param1, String param2) {
        FragmentGenres fragment = new FragmentGenres();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_genres, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Intiaize variables
        data = new Data();
        genres = data.getAllGenres();
        listView = view.findViewById(R.id.listViewGenres);
        adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, genres);

        //Set Adapter
        listView.setAdapter(adapter);

        //click Listner
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast toast = Toast.makeText(view.getContext(), "Clicked on " + genres.get(position),Toast.LENGTH_SHORT);
                toast.show();
                //Bryan's TODO: Use a listner that calls back to main to go to the next screen, put this fragment on the backstack
            }
        });
    }
}