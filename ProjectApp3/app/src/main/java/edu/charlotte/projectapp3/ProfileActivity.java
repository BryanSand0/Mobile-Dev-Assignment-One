package edu.charlotte.projectapp3;

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
    private Button updateButton;

    private ActivityResultLauncher<Intent> editUserLauncher;

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
        textViewName = findViewById(R.id.display_name);
        updateButton = findViewById(R.id.button_update);

        if (getIntent() != null && getIntent().getExtras() != null && getIntent().hasExtra(CreateUserActivity.KEY_NAME)) {
            String name = getIntent().getStringExtra(CreateUserActivity.KEY_NAME);
            textViewName.setText(name);
        }

        editUserLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        String updatedName = result.getData().getStringExtra(CreateUserActivity.KEY_NAME);
                        textViewName.setText(updatedName);
                    }
                }
        );

        updateButton.setOnClickListener(v -> {
                    Intent intent = new Intent(ProfileActivity.this, EditUserActivity.class);
                    intent.putExtra(CreateUserActivity.KEY_NAME, textViewName.getText().toString());
                    editUserLauncher.launch(intent);
                }
        );
    }
}