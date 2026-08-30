package com.example.projectapp2;


/*
 * Assignment 2
 * MainActivity.java
 * Lucnel Nordelus
 */

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // SeekBars
    private SeekBar seekBarRed;
    private SeekBar seekBarGreen;
    private SeekBar seekBarBlue;

    // TextViews
    private TextView textRedValue;
    private TextView textGreenValue;
    private TextView textBlueValue;
    private TextView textHex;
    private TextView textRgb;

    // Color display
    private View colorView;

    // Buttons
    private Button buttonWhite;
    private Button buttonBlack;
    private Button buttonBlue;
    private Button buttonReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Connect Java variables to XML views
        seekBarRed = findViewById(R.id.seekBarRed);
        seekBarGreen = findViewById(R.id.seekBarGreen);
        seekBarBlue = findViewById(R.id.seekBarBlue);

        textRedValue = findViewById(R.id.textRedValue);
        textGreenValue = findViewById(R.id.textGreenValue);
        textBlueValue = findViewById(R.id.textBlueValue);

        textHex = findViewById(R.id.textHex);
        textRgb = findViewById(R.id.textRgb);

        colorView = findViewById(R.id.colorView);

        buttonWhite = findViewById(R.id.buttonWhite);
        buttonBlack = findViewById(R.id.buttonBlack);
        buttonBlue = findViewById(R.id.buttonBlue);
        buttonReset = findViewById(R.id.buttonReset);

        // Listener used by all three SeekBars
        SeekBar.OnSeekBarChangeListener seekBarListener =
                new SeekBar.OnSeekBarChangeListener() {

                    @Override
                    public void onProgressChanged(
                            SeekBar seekBar,
                            int progress,
                            boolean fromUser) {

                        updateColor();
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        // No action needed
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        // No action needed
                    }
                };

        // Attach listener to each SeekBar
        seekBarRed.setOnSeekBarChangeListener(seekBarListener);
        seekBarGreen.setOnSeekBarChangeListener(seekBarListener);
        seekBarBlue.setOnSeekBarChangeListener(seekBarListener);

        // White button
        buttonWhite.setOnClickListener(v ->
                setColor(255, 255, 255));

        // Black button
        buttonBlack.setOnClickListener(v ->
                setColor(0, 0, 0));

        // Blue button
        buttonBlue.setOnClickListener(v ->
                setColor(0, 0, 255));

        // Reset button
        buttonReset.setOnClickListener(v ->
                setColor(64, 128, 0));

        // Make sure app starts with assignment's initial values
        setColor(64, 128, 0);
    }

    /*
     * Sets all three SeekBars to a specific RGB color.
     */
    private void setColor(int red, int green, int blue) {

        seekBarRed.setProgress(red);
        seekBarGreen.setProgress(green);
        seekBarBlue.setProgress(blue);

        updateColor();
    }

    /*
     * Updates the numbers, displayed color, RGB value, and HEX value.
     */
    private void updateColor() {

        // Get current SeekBar values
        int red = seekBarRed.getProgress();
        int green = seekBarGreen.getProgress();
        int blue = seekBarBlue.getProgress();

        // Update values beside SeekBars
        textRedValue.setText(String.valueOf(red));
        textGreenValue.setText(String.valueOf(green));
        textBlueValue.setText(String.valueOf(blue));

        // Update displayed color
        colorView.setBackgroundColor(
                Color.rgb(red, green, blue));


        textRgb.setText(
                getString(R.string.color_rgb_format, red, green, blue));


        // Convert RGB values to HEX
        String hex = String.format(
                java.util.Locale.US,
                "#%02X%02X%02X",
                red,
                green,
                blue);


        textHex.setText(
                getString(R.string.color_hex_format, hex));
    }
}