/*
 * Assignment / Evaluation #2
 * File Name: SelectLoanTermFragment.java
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.charlotte.evaluation02.R;
import edu.charlotte.evaluation02.models.Data;

/**
 * SelectLoanTermFragment displays loan terms (10, 15, 20, 25, 30 years)
 * using a custom ArrayAdapter (LoanTermAdapter) and row item layout (row_item_loan_term.xml)
 * featuring descriptions and money bag images.
 */
public class SelectLoanTermFragment extends Fragment {

    private ArrayList<Data.LoanTerm> loanTerms;
    private ListView listView;
    private Button buttonCancel;
    private LoanTermAdapter adapter;
    private FragmentSelectLoanTermListener mListener;

    public interface FragmentSelectLoanTermListener {
        void onLoanTermSelected(Data.LoanTerm loanTerm);
    }

    public SelectLoanTermFragment() {
        // Required empty public constructor
    }

    public static SelectLoanTermFragment newInstance() {
        return new SelectLoanTermFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FragmentSelectLoanTermListener) {
            mListener = (FragmentSelectLoanTermListener) context;
        } else {
            throw new RuntimeException(context + " must implement FragmentSelectLoanTermListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loanTerms = Data.getLoanTerms();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_select_loan_term, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = view.findViewById(R.id.listView);
        buttonCancel = view.findViewById(R.id.buttonCanel);

        adapter = new LoanTermAdapter(requireContext(), loanTerms);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mListener != null) {
                    mListener.onLoanTermSelected(loanTerms.get(position));
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
     * Custom ArrayAdapter for LoanTerm objects utilizing convertView recycling.
     */
    private static class LoanTermAdapter extends ArrayAdapter<Data.LoanTerm> {
        public LoanTermAdapter(@NonNull Context context, @NonNull List<Data.LoanTerm> objects) {
            super(context, 0, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_item_loan_term, parent, false);
            }

            Data.LoanTerm loanTerm = getItem(position);

            TextView textViewYears = convertView.findViewById(R.id.textViewYears);
            TextView textViewDesc = convertView.findViewById(R.id.textViewDesc);
            ImageView imageView = convertView.findViewById(R.id.imageView);

            if (loanTerm != null) {
                textViewYears.setText(loanTerm.getName());
                textViewDesc.setText(loanTerm.getDescription());
                imageView.setImageResource(loanTerm.getImage_res_id());
            }

            return convertView;
        }
    }
}
