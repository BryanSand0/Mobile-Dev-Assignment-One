/*
 * Assignment / Evaluation #2
 * File Name: SelectLoanTypeFragment.java
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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.charlotte.evaluation02.R;
import edu.charlotte.evaluation02.models.Data;

/**
 * SelectLoanTypeFragment displays loan types (Home Loan, Auto Loan, Personal Loan, Other Loan)
 * using a custom ArrayAdapter (LoanTypeAdapter) and row item layout (row_item_loan_type.xml).
 */
public class SelectLoanTypeFragment extends Fragment {

    private ArrayList<Data.LoanType> loanTypes;
    private ListView listView;
    private Button buttonCancel;
    private LoanTypeAdapter adapter;
    private FragmentSelectLoanTypeListener mListener;

    public interface FragmentSelectLoanTypeListener {
        void onLoanTypeSelected(Data.LoanType loanType);
    }

    public SelectLoanTypeFragment() {
        // Required empty public constructor
    }

    public static SelectLoanTypeFragment newInstance() {
        return new SelectLoanTypeFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FragmentSelectLoanTypeListener) {
            mListener = (FragmentSelectLoanTypeListener) context;
        } else {
            throw new RuntimeException(context + " must implement FragmentSelectLoanTypeListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loanTypes = Data.getLoanTypes();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_select_loan_type, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = view.findViewById(R.id.listView);
        buttonCancel = view.findViewById(R.id.buttonCanel);

        adapter = new LoanTypeAdapter(requireContext(), loanTypes);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mListener != null) {
                    mListener.onLoanTypeSelected(loanTypes.get(position));
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

    /**
     * Custom ArrayAdapter for LoanType objects utilizing convertView recycling.
     */
    private static class LoanTypeAdapter extends ArrayAdapter<Data.LoanType> {
        public LoanTypeAdapter(@NonNull Context context, @NonNull List<Data.LoanType> objects) {
            super(context, 0, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_item_loan_type, parent, false);
            }

            Data.LoanType loanType = getItem(position);

            TextView textViewName = convertView.findViewById(R.id.textViewName);
            TextView textViewDesc = convertView.findViewById(R.id.textViewDesc);

            if (loanType != null) {
                textViewName.setText(loanType.getName());
                textViewDesc.setText(loanType.getDescription());
            }

            return convertView;
        }
    }
}
