package com.example.evaluation01;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class DownPaymentActivity extends AppCompatActivity {

    private TextView textValue;
    private SeekBar seekBar;
    private Button buttonSubmit, buttonCancel;
    private double currentPercentage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_down_payment);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setTitle(R.string.title_select_down_payment);

        textValue = findViewById(R.id.textDownPaymentValue);
        seekBar = findViewById(R.id.seekBarDownPayment);
        buttonSubmit = findViewById(R.id.buttonSubmitDownPayment);
        buttonCancel = findViewById(R.id.buttonCancelDownPayment);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentPercentage = progress;
                textValue.setText(String.format(Locale.getDefault(), "%d%%", progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        buttonSubmit.setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("downPayment", currentPercentage);
            setResult(RESULT_OK, resultIntent);
            finish();
        });

        buttonCancel.setOnClickListener(v -> finish());
    }
}
