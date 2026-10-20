/*
 * Assignment / Evaluation #2
 * File Name: LoanDetailsFragment.java
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
import android.widget.TextView;

import java.util.Locale;

import edu.charlotte.evaluation02.R;
import edu.charlotte.evaluation02.models.Loan;

/**
 * LoanDetailsFragment receives the Loan object via arguments,
 * performs financial calculations (amortization formula for monthly payment,
 * principal, total payment, and total interest), and displays the full summary.
 */
public class LoanDetailsFragment extends Fragment {

    private static final String ARG_LOAN = "loan";

    private Loan loan;
    private TextView textViewLoanAmount, textViewInterest, textViewDownPayment, textViewLoanTerm, textViewLoanType;
    private TextView textViewPrincipal, textViewMonthlyPayment, textViewTotalPayment, textViewTotalInterest;
    private Button buttonBack;
    private FragmentLoanDetailsListener mListener;

    public interface FragmentLoanDetailsListener {
        void onLoanDetailsBackClicked();
    }

    public LoanDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Static factory method to pass the selected Loan object via Bundle arguments.
     */
    public static LoanDetailsFragment newInstance(Loan loan) {
        LoanDetailsFragment fragment = new LoanDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_LOAN, loan);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FragmentLoanDetailsListener) {
            mListener = (FragmentLoanDetailsListener) context;
        } else {
            throw new RuntimeException(context + " must implement FragmentLoanDetailsListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            loan = (Loan) getArguments().getSerializable(ARG_LOAN);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_loan_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textViewLoanAmount = view.findViewById(R.id.textViewLoanAmount);
        textViewInterest = view.findViewById(R.id.textViewInterest);
        textViewDownPayment = view.findViewById(R.id.textViewDownPayment);
        textViewLoanTerm = view.findViewById(R.id.textViewLoanTerm);
        textViewLoanType = view.findViewById(R.id.textViewLoanType);
        textViewPrincipal = view.findViewById(R.id.textViewPrincipal);
        textViewMonthlyPayment = view.findViewById(R.id.textViewMonthlyPayment);
        textViewTotalPayment = view.findViewById(R.id.textViewTotalPayment);
        textViewTotalInterest = view.findViewById(R.id.textViewTotalInterest);
        buttonBack = view.findViewById(R.id.buttonBack);

        if (loan != null) {
            textViewLoanAmount.setText(String.format(Locale.getDefault(), "$%.2f", loan.getAmount()));
            textViewInterest.setText(String.format(Locale.getDefault(), "%.1f%%", loan.getInterest()));
            
            double downPaymentAmount = loan.getAmount() * (loan.getDownPayment() / 100.0);
            textViewDownPayment.setText(String.format(Locale.getDefault(), "$%.2f (%.0f%%)", downPaymentAmount, loan.getDownPayment()));
            
            textViewLoanTerm.setText(String.format(Locale.getDefault(), "%d Years", loan.getLoanTerm()));
            textViewLoanType.setText(loan.getLoanType());

            // Principal calculation (Loan Amount minus Down Payment)
            double principal = loan.getAmount() - downPaymentAmount;
            textViewPrincipal.setText(String.format(Locale.getDefault(), "$%.2f", principal));

            // Amortization calculation for monthly payment
            double annualRate = loan.getInterest() / 100.0;
            double monthlyRate = annualRate / 12.0;
            int totalMonths = loan.getLoanTerm() * 12;

            double monthlyPayment;
            if (monthlyRate == 0) {
                monthlyPayment = principal / totalMonths;
            } else {
                monthlyPayment = principal * (monthlyRate * Math.pow(1 + monthlyRate, totalMonths)) / (Math.pow(1 + monthlyRate, totalMonths) - 1);
            }

            double totalPayment = monthlyPayment * totalMonths;
            double totalInterest = totalPayment - principal;

            textViewMonthlyPayment.setText(String.format(Locale.getDefault(), "$%.2f", monthlyPayment));
            textViewTotalPayment.setText(String.format(Locale.getDefault(), "$%.2f", totalPayment));
            textViewTotalInterest.setText(String.format(Locale.getDefault(), "$%.2f", totalInterest));
        }

        buttonBack.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onLoanDetailsBackClicked();
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
