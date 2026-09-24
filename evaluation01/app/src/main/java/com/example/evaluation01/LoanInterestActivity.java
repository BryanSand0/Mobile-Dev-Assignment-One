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

public class LoanInterestActivity extends AppCompatActivity {

    private EditText editInterest;
    private Button buttonSubmit, buttonCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_loan_interest);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setTitle(R.string.title_enter_interest);

        editInterest = findViewById(R.id.editInterestRate);
        buttonSubmit = findViewById(R.id.buttonSubmitInterest);
        buttonCancel = findViewById(R.id.buttonCancelInterest);

        buttonSubmit.setOnClickListener(v -> {
            String rateStr = editInterest.getText().toString();
            try {
                double rate = Double.parseDouble(rateStr);
                if (rate >= 0.1 && rate <= 30.0) {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("interestRate", rate);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                } else {
                    Toast.makeText(this, R.string.error_invalid_interest, Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, R.string.error_invalid_interest, Toast.LENGTH_SHORT).show();
            }
        });

        buttonCancel.setOnClickListener(v -> finish());
    }
}
