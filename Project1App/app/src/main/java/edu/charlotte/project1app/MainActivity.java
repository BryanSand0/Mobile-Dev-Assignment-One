/*
Assignment 1
File Name: MainActivity.java
Name: Lucnel Nordelus
*/

package edu.charlotte.project1app;
import android.content.Intent;
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

    private Button buttonPart2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components by finding them in the layout
        editTemperature = findViewById(R.id.editTemperature);
        textConversion = findViewById(R.id.textConversion);

        buttonCtoF = findViewById(R.id.buttonCtoF);
        buttonFtoC = findViewById(R.id.buttonFtoC);
        buttonReset = findViewById(R.id.buttonReset);
        buttonPart2 = findViewById(R.id.buttonPart2);

        // Set up click listener for navigation to Part 2
        buttonPart2.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Part2Activity.class);
            startActivity(intent);
        });

        // Set up click listeners for conversion actions and reset
        buttonCtoF.setOnClickListener(v -> convertCelsiusToFahrenheit());
        buttonFtoC.setOnClickListener(v -> convertFahrenheitToCelsius());
        buttonReset.setOnClickListener(v -> resetScreen());
    }

    /**
     * Reads the Celsius value from input, calculates Fahrenheit, and updates the display.
     * Formula: F = (9/5 * C) + 32
     */
    private void convertCelsiusToFahrenheit() {

        String input = editTemperature.getText().toString().trim();

        // Validation: Ensure input is not empty
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

            // Calculation logic
            double fahrenheit = (9.0 / 5.0 * celsius) + 32;

            // Update UI with formatted result
            textConversion.setText(
                    String.format("Conversion: %.2f F", fahrenheit)
            );

        } catch (NumberFormatException e) {
            // Error handling for non-numeric input
            Toast.makeText(
                    this,
                    "Please enter a valid number",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    /**
     * Reads the Fahrenheit value from input, calculates Celsius, and updates the display.
     * Formula: C = (5/9) * (F - 32)
     */
    private void convertFahrenheitToCelsius() {

        String input = editTemperature.getText().toString().trim();

        // Validation: Ensure input is not empty
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

            // Calculation logic
            double celsius = (5.0 / 9.0) * (fahrenheit - 32);

            // Update UI with formatted result
            textConversion.setText(
                    String.format("Conversion: %.2f C", celsius)
            );

        } catch (NumberFormatException e) {
            // Error handling for non-numeric input
            Toast.makeText(
                    this,
                    "Please enter a valid number",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    /**
     * Resets the input field and conversion result text to their default states.
     */
    private void resetScreen() {
        editTemperature.setText("");
        textConversion.setText(getString(R.string.conversion_label));
    }
}