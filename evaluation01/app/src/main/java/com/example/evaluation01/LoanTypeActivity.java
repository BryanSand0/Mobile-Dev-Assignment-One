package com.example.evaluation01;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoanTypeActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private Button buttonSubmit, buttonCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_loan_type);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setTitle(R.string.title_select_type);

        radioGroup = findViewById(R.id.radioGroupLoanType);
        buttonSubmit = findViewById(R.id.buttonSubmitType);
        buttonCancel = findViewById(R.id.buttonCancelType);

        buttonSubmit.setOnClickListener(v -> {
            int selectedId = radioGroup.getCheckedRadioButtonId();
            RadioButton selectedButton = findViewById(selectedId);
            String loanType = selectedButton.getText().toString();

            Intent resultIntent = new Intent();
            resultIntent.putExtra("loanType", loanType);
            setResult(RESULT_OK, resultIntent);
            finish();
        });

        buttonCancel.setOnClickListener(v -> finish());
    }
}
