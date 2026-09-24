package com.example.evaluation01;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoanTermActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_loan_term);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setTitle(R.string.title_select_term);

        findViewById(R.id.buttonTerm10).setOnClickListener(v -> returnResult(10));
        findViewById(R.id.buttonTerm15).setOnClickListener(v -> returnResult(15));
        findViewById(R.id.buttonTerm20).setOnClickListener(v -> returnResult(20));
        findViewById(R.id.buttonTerm25).setOnClickListener(v -> returnResult(25));
        findViewById(R.id.buttonTerm30).setOnClickListener(v -> returnResult(30));

        findViewById(R.id.buttonCancelTerm).setOnClickListener(v -> finish());
    }

    private void returnResult(int term) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("loanTerm", term);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
