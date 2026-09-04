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

/*
Assigment 04
EditUserActivity
Bryan Sandoval
 */

public class EditUserActivity extends AppCompatActivity {

    public static final String KEY_USER = "USER";
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

                Intent intent =
                        new Intent(
                                EditUserActivity.this,
                                ProfileActivity.class
                        );

                intent.putExtra(KEY_USER, user);

                startActivity(intent);

                finish();
            }
        });

        cancelButton.setOnClickListener(v -> {
            finish();
        });
    }
}