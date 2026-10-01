/*
 * Assignment / Evaluation #2
 * File Name: LoanCalculatorFragment.java
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import edu.charlotte.evaluation02.MainActivity;
import edu.charlotte.evaluation02.R;
import edu.charlotte.evaluation02.models.Data;
import edu.charlotte.evaluation02.models.Loan;

/**
 * LoanCalculatorFragment allows the user to input loan amount,
 * select down payment, interest rate, loan term, and loan type,
 * and submit the configuration for calculation.
 */
public class LoanCalculatorFragment extends Fragment {

    private EditText editTextLoanAmount;
    private Button buttonSelectDownPayment, buttonSelectInterest, buttonSelectTerm, buttonSelectLoanType, buttonSubmit;
    private TextView textViewDownPayment, textViewInterest, textViewLoanTerm, textViewLoanType;

    private FragmentLoanCalculatorListener mListener;

    private double amount = 0;
    private String downPayment = null;
    private String interest = null;
    private Data.LoanTerm loanTerm = null;
    private Data.LoanType loanType = null;

    /**
     * Interface defining communication callbacks from this fragment to MainActivity.
     */
    public interface FragmentLoanCalculatorListener {
        void onSelectDownPaymentClicked();
        void onSelectInterestRateClicked();
        void onSelectLoanTermClicked();
        void onSelectLoanTypeClicked();
        void onSubmitLoan(Loan loan);
    }

    public LoanCalculatorFragment() {
        // Required empty public constructor
    }

    public static LoanCalculatorFragment newInstance() {
        return new LoanCalculatorFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // Ensure host activity implements the required listener interface
        if (context instanceof FragmentLoanCalculatorListener) {
            mListener = (FragmentLoanCalculatorListener) context;
        } else {
            throw new RuntimeException(context + " must implement FragmentLoanCalculatorListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_loan_calculator, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextLoanAmount = view.findViewById(R.id.editTextLoanAmount);
        buttonSelectDownPayment = view.findViewById(R.id.buttonSelectDownPayment);
        buttonSelectInterest = view.findViewById(R.id.buttonSelectInterest);
        buttonSelectTerm = view.findViewById(R.id.buttonSelectTerm);
        buttonSelectLoanType = view.findViewById(R.id.buttonSelectLoanType);
        buttonSubmit = view.findViewById(R.id.buttonSubmit);

        textViewDownPayment = view.findViewById(R.id.textViewDownPayment);
        textViewInterest = view.findViewById(R.id.textViewInterest);
        textViewLoanTerm = view.findViewById(R.id.textViewLoanTerm);
        textViewLoanType = view.findViewById(R.id.textViewLoanType);

        // Restore selections from MainActivity when returning from selection fragments
        if (getActivity() instanceof MainActivity) {
            MainActivity activity = (MainActivity) getActivity();
            if (activity.getSelectedDownPayment() != null) {
                downPayment = activity.getSelectedDownPayment();
                textViewDownPayment.setText(downPayment);
            }
            if (activity.getSelectedInterest() != null) {
                interest = activity.getSelectedInterest();
                textViewInterest.setText(interest);
            }
            if (activity.getSelectedLoanTerm() != null) {
                loanTerm = activity.getSelectedLoanTerm();
                textViewLoanTerm.setText(loanTerm.getName());
            }
            if (activity.getSelectedLoanType() != null) {
                loanType = activity.getSelectedLoanType();
                textViewLoanType.setText(loanType.getName());
            }
        }

        if (amount > 0) {
            editTextLoanAmount.setText(String.valueOf(amount));
        }

        // Button click listeners to trigger navigation
        buttonSelectDownPayment.setOnClickListener(v -> {
            saveAmountInput();
            if (mListener != null) {
                mListener.onSelectDownPaymentClicked();
            }
        });

        buttonSelectInterest.setOnClickListener(v -> {
            saveAmountInput();
            if (mListener != null) {
                mListener.onSelectInterestRateClicked();
            }
        });

        buttonSelectTerm.setOnClickListener(v -> {
            saveAmountInput();
            if (mListener != null) {
                mListener.onSelectLoanTermClicked();
            }
        });

        buttonSelectLoanType.setOnClickListener(v -> {
            saveAmountInput();
            if (mListener != null) {
                mListener.onSelectLoanTypeClicked();
            }
        });

        // Submit button validation and data passing
        buttonSubmit.setOnClickListener(v -> {
            saveAmountInput();
            if (amount <= 0) {
                Toast.makeText(requireContext(), "Please enter a valid loan amount", Toast.LENGTH_SHORT).show();
                return;
            }
            if (downPayment == null) {
                Toast.makeText(requireContext(), "Please select a down payment", Toast.LENGTH_SHORT).show();
                return;
            }
            if (interest == null) {
                Toast.makeText(requireContext(), "Please select an interest rate", Toast.LENGTH_SHORT).show();
                return;
            }
            if (loanTerm == null) {
                Toast.makeText(requireContext(), "Please select a loan term", Toast.LENGTH_SHORT).show();
                return;
            }
            if (loanType == null) {
                Toast.makeText(requireContext(), "Please select a loan type", Toast.LENGTH_SHORT).show();
                return;
            }

            // Construct Loan model
            Loan loan = new Loan();
            loan.setAmount(amount);
            loan.setDownPayment(Double.parseDouble(downPayment.replace("%", "").trim()));
            loan.setInterest(Double.parseDouble(interest.replace("%", "").trim()));
            loan.setLoanTerm(loanTerm.getYears());
            loan.setLoanType(loanType.getName());

            if (mListener != null) {
                mListener.onSubmitLoan(loan);
            }
        });
    }

    private void saveAmountInput() {
        String amountStr = editTextLoanAmount.getText().toString().trim();
        if (!amountStr.isEmpty()) {
            try {
                amount = Double.parseDouble(amountStr);
            } catch (NumberFormatException e) {
                amount = 0;
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
