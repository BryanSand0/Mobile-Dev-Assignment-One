/*
Assignment 1
File Name: Part2Activity.java
Name: Lucnel Nordelus
*/

package edu.charlotte.project1app;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity for the second part of the assignment, featuring a RadioGroup
 * for selecting the conversion type and a single calculate button.
 */
public class Part2Activity extends AppCompatActivity {

    private EditText editTemperature;
    private TextView textConversion;

    private RadioGroup radioGroup;
    private RadioButton radioCtoF;
    private RadioButton radioFtoC;

    private Button buttonCalculate;
    private Button buttonReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part2);

        // Initialize UI components for Part 2
        editTemperature = findViewById(R.id.editTemperaturePart2);
        textConversion = findViewById(R.id.textConversionPart2);

        radioGroup = findViewById(R.id.radioGroupConversion);
        radioCtoF = findViewById(R.id.radioCtoF);
        radioFtoC = findViewById(R.id.radioFtoC);

        buttonCalculate = findViewById(R.id.buttonCalculate);
        buttonReset = findViewById(R.id.buttonResetPart2);

        // Set up click listeners for action buttons
        buttonCalculate.setOnClickListener(v -> calculateConversion());
        buttonReset.setOnClickListener(v -> resetScreen());
    }

    /**
     * Performs temperature conversion based on the selected RadioButton.
     * Logic includes validation for empty input, numeric format, and selection state.
     */
    private void calculateConversion() {

        String input = editTemperature.getText().toString().trim();

        // Validation: Check for empty input
        if (input.isEmpty()) {
            Toast.makeText(
                    this,
                    "Please enter a temperature",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        double temperature;

        try {
            // Parse input string to double
            temperature = Double.parseDouble(input);

        } catch (NumberFormatException e) {
            // Error handling for invalid number format
            Toast.makeText(
                    this,
                    "Please enter a valid number",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        // Validation: Check if a conversion type is selected in the RadioGroup
        if (radioGroup.getCheckedRadioButtonId() == -1) {

            Toast.makeText(
                    this,
                    "Please select a conversion",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        // Perform calculation based on the checked radio button
        if (radioCtoF.isChecked()) {
            // Celsius to Fahrenheit formula: (9/5 * C) + 32
            double fahrenheit =
                    (9.0 / 5.0 * temperature) + 32;

            textConversion.setText(
                    String.format(
                            "Conversion: %.2f F",
                            fahrenheit
                    )
            );

        } else if (radioFtoC.isChecked()) {
            // Fahrenheit to Celsius formula: (5/9) * (F - 32)
            double celsius =
                    (5.0 / 9.0) * (temperature - 32);

            textConversion.setText(
                    String.format(
                            "Conversion: %.2f C",
                            celsius
                    )
            );
        }
    }

    /**
     * Resets all inputs, selection states, and result text in the Part 2 UI.
     */
    private void resetScreen() {

        editTemperature.setText("");
        radioGroup.clearCheck();

        textConversion.setText(
                getString(R.string.conversion_na)
        );
    }
}