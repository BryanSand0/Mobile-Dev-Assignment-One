package edu.charlotte.projectapp3;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ProfileActivity extends AppCompatActivity {

    private TextView textViewName;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setTitle("Profile");

        textViewName = findViewById(R.id.textViewNameValue);
        Button buttonUpdate = findViewById(R.id.buttonUpdate);

        if (getIntent() != null && getIntent().hasExtra(CreateUserActivity.KEY_NAME)) {
            name = getIntent().getStringExtra(CreateUserActivity.KEY_NAME);
            textViewName.setText(name);
        }

        buttonUpdate.setOnClickListener(v -> {
            // Part 4: Logic to launch EditUserActivity will go here
        });
    }
}