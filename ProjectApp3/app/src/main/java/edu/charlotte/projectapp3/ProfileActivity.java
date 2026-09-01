package edu.charlotte.projectapp3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ProfileActivity extends AppCompatActivity {

    TextView textViewName;
    Button updateButton;

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

        textViewName = findViewById(R.id.display_name);
        updateButton = findViewById(R.id.button_update);

        if (true){//getIntent() != null && getIntent().getExtras() != null && getIntent().hasExtra("Name")) {
            //String Name = getIntent().getStringExtra("Name");
            textViewName.setText("John Doe");//Name);
        }

        updateButton.setOnClickListener(v -> {
                    //Intent intent = new Intent(ProfileActivity.this, /*Part4.class*/);
                    //startActivity(intent);
                }
        );
    }
}