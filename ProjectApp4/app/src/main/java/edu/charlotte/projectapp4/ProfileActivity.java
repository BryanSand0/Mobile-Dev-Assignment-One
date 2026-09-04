/*
 * Assignment 4
 * ProfileActivity.java
 * Lucnel Nordelus
 */
package edu.charlotte.projectapp4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ProfileActivity extends AppCompatActivity {

    private TextView textViewName;
    private TextView textViewEmail;
    private TextView textViewRole;
    private Button updateButton;

    private User user;

    private ActivityResultLauncher<Intent> editUserLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main),
                (v, insets) -> {

                    Insets systemBars =
                            insets.getInsets(WindowInsetsCompat.Type.systemBars());

                    v.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                    );

                    return insets;
                });

        setTitle("Profile");

        textViewName = findViewById(R.id.display_name);
        textViewEmail = findViewById(R.id.display_email);
        textViewRole = findViewById(R.id.display_role);
        updateButton = findViewById(R.id.button_update);

        // Receive User object from CreateUserActivity
        if (getIntent() != null &&
                getIntent().hasExtra(CreateUserActivity.KEY_USER)) {

            user = (User) getIntent()
                    .getSerializableExtra(CreateUserActivity.KEY_USER);

            displayUser();
        }

        // Receive updated User object back from EditUserActivity
        editUserLauncher =
                registerForActivityResult(
                        new ActivityResultContracts.StartActivityForResult(),
                        result -> {

                            if (result.getResultCode() == RESULT_OK
                                    && result.getData() != null) {

                                user = (User) result.getData()
                                        .getSerializableExtra(
                                                CreateUserActivity.KEY_USER
                                        );

                                displayUser();
                            }
                        });

        // Update button
        updateButton.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            ProfileActivity.this,
                            EditUserActivity.class
                    );

            intent.putExtra(
                    CreateUserActivity.KEY_USER,
                    user
            );

            editUserLauncher.launch(intent);
        });
    }

    private void displayUser() {

        if (user != null) {

            textViewName.setText(user.getName());
            textViewEmail.setText(user.getEmail());
            textViewRole.setText(user.getRole());
        }
    }
}