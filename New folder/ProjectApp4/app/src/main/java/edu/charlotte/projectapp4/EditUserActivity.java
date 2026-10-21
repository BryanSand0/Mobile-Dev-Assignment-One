/*
 * Assignment 4
 * EditUserActivity.java
 * Lucnel Nordelus
 */
package edu.charlotte.projectapp4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EditUserActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextEmail;
    private RadioGroup radioGroupRole;
    private Button submitButton;
    private Button cancelButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_user);

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

        setTitle("Edit Profile");

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        radioGroupRole = findViewById(R.id.radioGroupRole);
        submitButton = findViewById(R.id.button_submit);
        cancelButton = findViewById(R.id.button_cancel);

        // 1. Receive the User object and initialize views
        if (getIntent() != null && getIntent().hasExtra(CreateUserActivity.KEY_USER)) {
            User user = (User) getIntent().getSerializableExtra(CreateUserActivity.KEY_USER);
            if (user != null) {
                editTextName.setText(user.getName());
                editTextEmail.setText(user.getEmail());
                if (user.getRole().equals(getString(R.string.student))) {
                    radioGroupRole.check(R.id.radioStudent);
                } else if (user.getRole().equals(getString(R.string.employee))) {
                    radioGroupRole.check(R.id.radioEmployee);
                } else if (user.getRole().equals(getString(R.string.other))) {
                    radioGroupRole.check(R.id.radioOther);
                }
            }
        }

        submitButton.setOnClickListener(v -> {

            String name =
                    editTextName.getText().toString().trim();

            String email =
                    editTextEmail.getText().toString().trim();

            int selectedRoleId =
                    radioGroupRole.getCheckedRadioButtonId();

            if (name.isEmpty()) {

                Toast.makeText(
                        EditUserActivity.this,
                        "Name is required",
                        Toast.LENGTH_SHORT
                ).show();

            } else if (email.isEmpty()) {

                Toast.makeText(
                        EditUserActivity.this,
                        "Email is required",
                        Toast.LENGTH_SHORT
                ).show();

            } else if (selectedRoleId == -1) {

                Toast.makeText(
                        EditUserActivity.this,
                        "Role is required",
                        Toast.LENGTH_SHORT
                ).show();

            } else {

                RadioButton selectedRole =
                        findViewById(selectedRoleId);

                String role =
                        selectedRole.getText().toString();

                User user =
                        new User(name, email, role);

                // 2.b Send back the new User object to the Profile activity
                Intent intent = new Intent();
                intent.putExtra(CreateUserActivity.KEY_USER, user);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        cancelButton.setOnClickListener(v -> {
            finish();
        });
    }
}