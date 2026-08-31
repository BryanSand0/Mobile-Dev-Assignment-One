package edu.charlotte.projectapp3;

import android.os.Bundle;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.Toast;
public class RegistrationActivity extends AppCompatActivity {
    EditText editTextName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setTitle("Registration");

        editTextName = findViewById(R.id.editTextName);

        // Validation: Ensure input is not empty
//        if (input.isEmpty()) {
//            Toast.makeText(
//                    this,
//                    "Please enter a temperature",
//                    Toast.LENGTH_SHORT
//            ).show();
//            return;
        }
    }