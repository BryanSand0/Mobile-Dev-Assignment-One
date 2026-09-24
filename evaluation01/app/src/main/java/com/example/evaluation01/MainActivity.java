package com.example.evaluation01;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Button submitButton;
    private Button resetButton;
    private Button loanTypeSelectButton;
    private Button loanAmountSelectButton;
    private Button loanDownPaymentSelectButton;
    private Button loanTermSelectButton;
    private Button loanInterestSelectButton;

    private TextView textLoanTypeValue;
    private TextView textLoanAmountValue;
    private TextView textLoanDownPaymentValue;
    private TextView textLoanTermValue;
    private TextView textLoanInterestValue;

    // Stored variables
    private String selectedLoanType = null;
    private double selectedLoanAmount = -1;
    private double selectedDownPayment = -1;
    private int selectedLoanTerm = -1;
    private double selectedInterestRate = -1;

    // Launchers
    private final ActivityResultLauncher<Intent> loanTypeLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    selectedLoanType = result.getData().getStringExtra("loanType");
                    textLoanTypeValue.setText(selectedLoanType);
                }
            });

    private final ActivityResultLauncher<Intent> loanAmountLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    selectedLoanAmount = result.getData().getDoubleExtra("loanAmount", -1);
                    textLoanAmountValue.setText(String.format(Locale.getDefault(), "%.2f", selectedLoanAmount));
                }
            });

    private final ActivityResultLauncher<Intent> downPaymentLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    selectedDownPayment = result.getData().getDoubleExtra("downPayment", -1);
                    textLoanDownPaymentValue.setText(String.format(Locale.getDefault(), "%.0f%%", selectedDownPayment));
                }
            });

    private final ActivityResultLauncher<Intent> loanTermLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    selectedLoanTerm = result.getData().getIntExtra("loanTerm", -1);
                    textLoanTermValue.setText(String.format(Locale.getDefault(), "%d Years", selectedLoanTerm));
                }
            });

    private final ActivityResultLauncher<Intent> interestLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    selectedInterestRate = result.getData().getDoubleExtra("interestRate", -1);
                    textLoanInterestValue.setText(String.format(Locale.getDefault(), "%.2f%%", selectedInterestRate));
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setTitle(R.string.title_color_selection);

        // Initialize buttons
        loanTypeSelectButton = findViewById(R.id.buttonLoanType);
        loanAmountSelectButton = findViewById(R.id.buttonloanAmount);
        loanDownPaymentSelectButton = findViewById(R.id.buttonDownPayment);
        loanTermSelectButton = findViewById(R.id.buttonLoanTerm);
        loanInterestSelectButton = findViewById(R.id.buttonLoanInterest);
        submitButton = findViewById(R.id.buttonSubmit);
        resetButton = findViewById(R.id.buttonReset);

        // Initialize text views
        textLoanTypeValue = findViewById(R.id.textLoanTypeValue);
        textLoanAmountValue = findViewById(R.id.textLoanAmountValue);
        textLoanDownPaymentValue = findViewById(R.id.textLoanDownPaymentValue);
        textLoanTermValue = findViewById(R.id.textLoanTermValue);
        textLoanInterestValue = findViewById(R.id.textLoanInterestValue);

        loanTypeSelectButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoanTypeActivity.class);
            loanTypeLauncher.launch(intent);
        });

        loanAmountSelectButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoanAmountActivity.class);
            loanAmountLauncher.launch(intent);
        });

        loanDownPaymentSelectButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DownPaymentActivity.class);
            downPaymentLauncher.launch(intent);
        });

        loanTermSelectButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoanTermActivity.class);
            loanTermLauncher.launch(intent);
        });

        loanInterestSelectButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoanInterestActivity.class);
            interestLauncher.launch(intent);
        });

        resetButton.setOnClickListener(v -> {
            selectedLoanType = null;
            selectedLoanAmount = -1;
            selectedDownPayment = -1;
            selectedLoanTerm = -1;
            selectedInterestRate = -1;

            textLoanTypeValue.setText(R.string.not_available);
            textLoanAmountValue.setText(R.string.not_available);
            textLoanDownPaymentValue.setText(R.string.not_available);
            textLoanTermValue.setText(R.string.not_available);
            textLoanInterestValue.setText(R.string.not_available);
        });

        submitButton.setOnClickListener(v -> {
            if (selectedLoanType == null) {
                Toast.makeText(this, R.string.error_no_type, Toast.LENGTH_SHORT).show();
            } else if (selectedLoanAmount == -1) {
                Toast.makeText(this, R.string.error_no_amount, Toast.LENGTH_SHORT).show();
            } else if (selectedDownPayment == -1) {
                Toast.makeText(this, R.string.error_no_down_payment, Toast.LENGTH_SHORT).show();
            } else if (selectedLoanTerm == -1) {
                Toast.makeText(this, R.string.error_no_term, Toast.LENGTH_SHORT).show();
            } else if (selectedInterestRate == -1) {
                Toast.makeText(this, R.string.error_no_interest, Toast.LENGTH_SHORT).show();
            } else {
                Loan loan = new Loan(selectedLoanType, selectedLoanAmount, selectedDownPayment, selectedLoanTerm, selectedInterestRate);
                Intent intent = new Intent(MainActivity.this, LoanDetailsActivity.class);
                intent.putExtra("loanObject", loan);
                startActivity(intent);
                finish();
            }
        });
    }
}
