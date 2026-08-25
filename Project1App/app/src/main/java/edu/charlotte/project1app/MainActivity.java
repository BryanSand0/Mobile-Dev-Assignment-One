/*
Assignment 1
File Name: MainActivity.java
Name: Your Lucnel Nordelus
*/

package edu.charlotte.project1app;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText editTemperature;
    private TextView textConversion;
    private Button buttonCtoF;
    private Button buttonFtoC;
    private Button buttonReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTemperature = findViewById(R.id.editTemperature);
        textConversion = findViewById(R.id.textConversion);

        buttonCtoF = findViewById(R.id.buttonCtoF);
        buttonFtoC = findViewById(R.id.buttonFtoC);
        buttonReset = findViewById(R.id.buttonReset);

        buttonCtoF.setOnClickListener(v -> convertCelsiusToFahrenheit());
        buttonFtoC.setOnClickListener(v -> convertFahrenheitToCelsius());
        buttonReset.setOnClickListener(v -> resetScreen());
    }

    private void convertCelsiusToFahrenheit() {

        String input = editTemperature.getText().toString().trim();

        if (input.isEmpty()) {
            Toast.makeText(
                    this,
                    "Please enter a temperature",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        try {
            double celsius = Double.parseDouble(input);

            double fahrenheit = (9.0 / 5.0 * celsius) + 32;

            textConversion.setText(
                    String.format("Conversion: %.2f F", fahrenheit)
            );

        } catch (NumberFormatException e) {

            Toast.makeText(
                    this,
                    "Please enter a valid number",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    private void convertFahrenheitToCelsius() {

        String input = editTemperature.getText().toString().trim();

        if (input.isEmpty()) {
            Toast.makeText(
                    this,
                    "Please enter a temperature",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        try {
            double fahrenheit = Double.parseDouble(input);

            double celsius = (5.0 / 9.0) * (fahrenheit - 32);

            textConversion.setText(
                    String.format("Conversion: %.2f C", celsius)
            );

        } catch (NumberFormatException e) {

            Toast.makeText(
                    this,
                    "Please enter a valid number",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    private void resetScreen() {
        editTemperature.setText("");
        textConversion.setText(getString(R.string.conversion_label));
    }
}