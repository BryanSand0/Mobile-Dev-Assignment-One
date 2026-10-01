package com.example.moviebrowserpractice2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class CategoriesFragment extends Fragment {

    Data data;
    ListView listView;
    ArrayList<String> categories;
    ArrayAdapter<String> adapter;

    private CategoriesFragmentListener mListener;

    public interface CategoriesFragmentListener {
        void onCategorySelected(String category);
    }

    public CategoriesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof CategoriesFragmentListener) {
            mListener = (CategoriesFragmentListener) context;
        }
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        return inflater.inflate(
                R.layout.fragment_categories,
                container,
                false
        );
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        data = new Data();

        categories = data.getAllCategories();

        listView =
                view.findViewById(R.id.listViewCategories);

        adapter =
                new ArrayAdapter<>(
                        view.getContext(),
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1,
                        categories
                );

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(
                (parent, clickedView, position, id) -> {

                    String selectedCategory =
                            categories.get(position);

                    mListener.onCategorySelected(selectedCategory);
                }
        );
    }
}