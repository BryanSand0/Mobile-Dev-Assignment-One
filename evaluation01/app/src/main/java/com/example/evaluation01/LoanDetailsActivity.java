package com.example.evaluation01;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class LoanDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_loan_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setTitle(R.string.title_loan_details);

        Loan loan = (Loan) getIntent().getSerializableExtra("loanObject");
        if (loan != null) {
            displayDetails(loan);
        }

        findViewById(R.id.buttonCloseDetails).setOnClickListener(v -> finish());
    }

    private void displayDetails(Loan loan) {
        double totalLoanAmount = loan.getLoanAmount();
        double downPaymentPercent = loan.getDownPayment();
        double downPaymentAmount = totalLoanAmount * (downPaymentPercent / 100.0);
        double principal = totalLoanAmount - downPaymentAmount;
        double annualInterestRate = loan.getInterestRate();
        int loanTermYears = loan.getLoanTerm();

        double monthlyInterestRate = (annualInterestRate / 100.0) / 12.0;
        int totalMonths = loanTermYears * 12;

        double monthlyPayment;
        if (monthlyInterestRate > 0) {
            monthlyPayment = principal * (monthlyInterestRate * Math.pow(1 + monthlyInterestRate, totalMonths)) /
                    (Math.pow(1 + monthlyInterestRate, totalMonths) - 1);
        } else {
            monthlyPayment = principal / totalMonths;
        }

        double totalPayment = monthlyPayment * totalMonths;
        double totalInterest = totalPayment - principal;

        String summary = String.format(Locale.getDefault(),
                "Loan Type: %s\n" +
                "Total Loan Amount: $%.2f\n" +
                "Down Payment: %.0f%% ($%.2f)\n" +
                "Loan Principal: $%.2f\n" +
                "Loan Term: %d Years (%d Months)\n" +
                "Interest Rate: %.2f%% annual\n\n" +
                "--- CALCULATIONS ---\n" +
                "Monthly Payment: $%.2f\n" +
                "Total Payment: $%.2f\n" +
                "Total Interest: $%.2f",
                loan.getLoanType(),
                totalLoanAmount,
                downPaymentPercent, downPaymentAmount,
                principal,
                loanTermYears, totalMonths,
                annualInterestRate,
                monthlyPayment,
                totalPayment,
                totalInterest
        );

        TextView textSummary = findViewById(R.id.textDetailsSummary);
        textSummary.setText(summary);
    }
}
