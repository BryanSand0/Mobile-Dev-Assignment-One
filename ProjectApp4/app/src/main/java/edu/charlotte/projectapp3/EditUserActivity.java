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

public class EditUserActivity extends AppCompatActivity {

    private EditText editTextName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setTitle("Edit User Info");

        editTextName = findViewById(R.id.editTextName);
        Button buttonSubmit = findViewById(R.id.button_submit);
        Button buttonCancel = findViewById(R.id.button_cancel);

        if (getIntent() != null && getIntent().getExtras() != null && getIntent().hasExtra(CreateUserActivity.KEY_NAME)) {
            String name = getIntent().getStringExtra(CreateUserActivity.KEY_NAME);
            editTextName.setText(name);
        }

        buttonSubmit.setOnClickListener(v -> {
            String name = editTextName.getText().toString().trim();
            if (name.isEmpty()) {
                Toast.makeText(EditUserActivity.this, "Name is required", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent();
                intent.putExtra(CreateUserActivity.KEY_NAME, name);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        buttonCancel.setOnClickListener(v -> finish());
    }
}