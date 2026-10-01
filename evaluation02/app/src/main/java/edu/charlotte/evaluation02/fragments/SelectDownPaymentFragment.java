/*
 * Assignment / Evaluation #2
 * File Name: SelectDownPaymentFragment.java
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

import edu.charlotte.evaluation02.R;

/**
 * SelectDownPaymentFragment displays a list of down payment percentages (0% to 90%)
 * in a ListView and communicates the selected value back to MainActivity via interface callback.
 */
public class SelectDownPaymentFragment extends Fragment {

    private ArrayList<String> downPayments;
    private ListView listView;
    private Button buttonCancel;
    private ArrayAdapter<String> adapter;
    private FragmentSelectDownPaymentListener mListener;

    public interface FragmentSelectDownPaymentListener {
        void onLoanDownPaymentSelected(String downPayment);
    }

    public SelectDownPaymentFragment() {
        // Required empty public constructor
    }

    public static SelectDownPaymentFragment newInstance() {
        return new SelectDownPaymentFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FragmentSelectDownPaymentListener) {
            mListener = (FragmentSelectDownPaymentListener) context;
        } else {
            throw new RuntimeException(context + " must implement FragmentSelectDownPaymentListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Populate down payment percentage choices from 0% to 90% in increments of 5%
        downPayments = new ArrayList<>();
        for (int i = 0; i <= 90; i += 5) {
            downPayments.add(i + "%");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_select_down_payment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = view.findViewById(R.id.listView);
        buttonCancel = view.findViewById(R.id.buttonCanel);

        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, android.R.id.text1, downPayments);
        listView.setAdapter(adapter);

        // Handle item selection
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mListener != null) {
                    mListener.onLoanDownPaymentSelected(downPayments.get(position));
                }
            }
        });

        // Cancel button pops back stack
        buttonCancel.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
