/*
 * Assignment / Evaluation #2
 * File Name: SelectInterestRateFragment.java
 */

package edu.charlotte.evaluation02.fragments;

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

import java.util.ArrayList;
import java.util.Locale;

import edu.charlotte.evaluation02.R;

/**
 * SelectInterestRateFragment displays a list of annual interest rates (1.0% to 15.0%)
 * in a ListView and communicates the selection back to MainActivity via interface callback.
 */
public class SelectInterestRateFragment extends Fragment {

    private ArrayList<String> interestRates;
    private ListView listView;
    private Button buttonCancel;
    private ArrayAdapter<String> adapter;
    private FragmentSelectInterestRateListener mListener;

    public interface FragmentSelectInterestRateListener {
        void onLoanInterestRateSelected(String interestRate);
    }

    public SelectInterestRateFragment() {
        // Required empty public constructor
    }

    public static SelectInterestRateFragment newInstance() {
        return new SelectInterestRateFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FragmentSelectInterestRateListener) {
            mListener = (FragmentSelectInterestRateListener) context;
        } else {
            throw new RuntimeException(context + " must implement FragmentSelectInterestRateListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Populate interest rates from 1.0% to 15.0% in steps of 0.5%
        interestRates = new ArrayList<>();
        for (double rate = 1.0; rate <= 15.0; rate += 0.5) {
            interestRates.add(String.format(Locale.getDefault(), "%.1f%%", rate));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_select_interest_rate, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = view.findViewById(R.id.listView);
        buttonCancel = view.findViewById(R.id.buttonCanel);

        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, android.R.id.text1, interestRates);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mListener != null) {
                    mListener.onLoanInterestRateSelected(interestRates.get(position));
                }
            }
        });

        buttonCancel.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
