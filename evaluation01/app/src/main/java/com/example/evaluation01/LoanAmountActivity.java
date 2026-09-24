package com.example.evaluation01;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoanAmountActivity extends AppCompatActivity {

    private EditText editLoanAmount;
    private Button buttonSubmit, buttonCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_loan_amount);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setTitle(R.string.title_enter_amount);

        editLoanAmount = findViewById(R.id.editLoanAmount);
        buttonSubmit = findViewById(R.id.buttonSubmitAmount);
        buttonCancel = findViewById(R.id.buttonCancelAmount);

        buttonSubmit.setOnClickListener(v -> {
            String amountStr = editLoanAmount.getText().toString();
            try {
                double amount = Double.parseDouble(amountStr);
                if (amount > 0) {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("loanAmount", amount);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                } else {
                    Toast.makeText(this, R.string.error_invalid_amount, Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, R.string.error_invalid_amount, Toast.LENGTH_SHORT).show();
            }
        });

        buttonCancel.setOnClickListener(v -> finish());
    }
}
