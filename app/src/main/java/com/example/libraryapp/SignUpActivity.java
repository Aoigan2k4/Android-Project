package com.example.libraryapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText, confirmPasswordEditText;
    private Button signUpButton;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private RadioGroup roleRadioGroup;
    private RadioButton clientRadioButton, librarianRadioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        signUpButton = findViewById(R.id.signUpButton);
        roleRadioGroup = findViewById(R.id.roleRadioGroup);
        clientRadioButton = findViewById(R.id.clientRadioButton);
        librarianRadioButton = findViewById(R.id.librarianRadioButton);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpUser();
            }
        });

        TextView loginRedirectTextView = findViewById(R.id.loginRedirectTextView);
        loginRedirectTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void signUpUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("Email is required.");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("Password is required.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            confirmPasswordEditText.setError("Passwords do not match.");
            return;
        }

        int selectedRoleId = roleRadioGroup.getCheckedRadioButtonId();
        if (selectedRoleId == -1) {
            Toast.makeText(SignUpActivity.this, "Please select a role.", Toast.LENGTH_SHORT).show();
            return;
        }

        String role = "";
        if (selectedRoleId == R.id.clientRadioButton) {
            role = "Client";
        } else if (selectedRoleId == R.id.librarianRadioButton) {
            role = "Librarian";
        }

        String finalRole = role;
        mAuth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String userId = mAuth.getCurrentUser().getUid();
                    Map<String, Object> user = new HashMap<>();
                    user.put("email", email);
                    user.put("role", finalRole);

                    db.collection("users").document(userId)
                        .set(user)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(SignUpActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(SignUpActivity.this, "Failed to save user info.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                } else {
                    Toast.makeText(SignUpActivity.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}