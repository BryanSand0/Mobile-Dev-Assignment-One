package edu.charlotte.projectapp3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CreateUserActivity extends AppCompatActivity {

    public static final String KEY_NAME = "NAME";

    EditText editTextName;
    private Button next_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_user);
        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main),
                (v, insets) -> {
                    Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                    v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                    return insets;
                });

        setTitle("Registration");

        editTextName = findViewById(R.id.editTextName);
        next_button = findViewById(R.id.next_button);

        next_button.setOnClickListener(v -> {

            String name = editTextName.getText().toString().trim();

            if (name.isEmpty()) {

                Toast.makeText(
                        CreateUserActivity.this,
                        "Name is required",
                        Toast.LENGTH_SHORT
                ).show();

            } else {

                Intent intent = new Intent(
                        CreateUserActivity.this,
                        ProfileActivity.class
                );

                intent.putExtra(KEY_NAME, name);

                startActivity(intent);

                finish();
            }
        });
    }
}