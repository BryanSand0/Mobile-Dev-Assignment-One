/*
 * Assignment / Evaluation #2
 * File Name: MainActivity.java
 * Host Activity for Single-Activity Architecture
 */

package edu.charlotte.evaluation02;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import edu.charlotte.evaluation02.fragments.LoanCalculatorFragment;
import edu.charlotte.evaluation02.fragments.LoanDetailsFragment;
import edu.charlotte.evaluation02.fragments.SelectDownPaymentFragment;
import edu.charlotte.evaluation02.fragments.SelectInterestRateFragment;
import edu.charlotte.evaluation02.fragments.SelectLoanTermFragment;
import edu.charlotte.evaluation02.fragments.SelectLoanTypeFragment;
import edu.charlotte.evaluation02.models.Data;
import edu.charlotte.evaluation02.models.Loan;

/**
 * MainActivity serves as the host container for all fragments in the application.
 * It implements listener interfaces from all fragments to manage navigation,
 * fragment transactions, back stack management, and temporary selection state.
 */
public class MainActivity extends AppCompatActivity implements
        LoanCalculatorFragment.FragmentLoanCalculatorListener,
        SelectDownPaymentFragment.FragmentSelectDownPaymentListener,
        SelectInterestRateFragment.FragmentSelectInterestRateListener,
        SelectLoanTermFragment.FragmentSelectLoanTermListener,
        SelectLoanTypeFragment.FragmentSelectLoanTypeListener,
        LoanDetailsFragment.FragmentLoanDetailsListener {

    // Temporary storage for user selections across fragments
    private String selectedDownPayment;
    private String selectedInterest;
    private Data.LoanTerm selectedLoanTerm;
    private Data.LoanType selectedLoanType;

    public String getSelectedDownPayment() {
        return selectedDownPayment;
    }

    public String getSelectedInterest() {
        return selectedInterest;
    }

    public Data.LoanTerm getSelectedLoanTerm() {
        return selectedLoanTerm;
    }

    public Data.LoanType getSelectedLoanType() {
        return selectedLoanType;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Load initial LoanCalculatorFragment only on fresh launch
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main, LoanCalculatorFragment.newInstance())
                    .commit();
        }
    }

    // --- Fragment Navigation Callbacks ---

    @Override
    public void onSelectDownPaymentClicked() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, SelectDownPaymentFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onSelectInterestRateClicked() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, SelectInterestRateFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onSelectLoanTermClicked() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, SelectLoanTermFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onSelectLoanTypeClicked() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, SelectLoanTypeFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onSubmitLoan(Loan loan) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, LoanDetailsFragment.newInstance(loan))
                .addToBackStack(null)
                .commit();
    }

    // --- Selection Result Callbacks (Pop Back Stack to Return) ---

    @Override
    public void onLoanDownPaymentSelected(String downPayment) {
        selectedDownPayment = downPayment;
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onLoanInterestRateSelected(String interestRate) {
        selectedInterest = interestRate;
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onLoanTermSelected(Data.LoanTerm loanTerm) {
        selectedLoanTerm = loanTerm;
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onLoanTypeSelected(Data.LoanType loanType) {
        selectedLoanType = loanType;
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onLoanDetailsBackClicked() {
        getSupportFragmentManager().popBackStack();
    }
}
