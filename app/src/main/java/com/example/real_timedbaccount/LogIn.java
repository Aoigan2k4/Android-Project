package com.example.real_timedbaccount;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class LogIn extends AppCompatActivity {
    EditText usernameTxt, passTxt;
    Button loginBtn;
    TextView signUp;
    DatabaseReference db;
    RadioGroup roleRadioGroup;
    RadioButton admin, client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        usernameTxt = findViewById(R.id.usernameTxt);
        passTxt = findViewById(R.id.passwordTxt);
        loginBtn = findViewById(R.id.loginButton);
        signUp = findViewById(R.id.signUpLink);
        roleRadioGroup = findViewById(R.id.roleRadioGroup);
        admin = findViewById(R.id.admin);
        client = findViewById(R.id.client);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameTxt.getText().toString().trim();
                String password = passTxt.getText().toString().trim();
                int selectedRoleId = roleRadioGroup.getCheckedRadioButtonId();

                if (username.isEmpty() || password.isEmpty() || selectedRoleId == -1) {
                    Toast.makeText(LogIn.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    if (selectedRoleId == client.getId()) {
                        db = FirebaseDatabase.getInstance().getReference("Users").child("Client");
                        Validation(username,password,db);
                    } else if (selectedRoleId == admin.getId()) {
                        db = FirebaseDatabase.getInstance().getReference("Users").child("Admin");
                        Validation(username,password,db);
                    }
                }
            }
        });

        signUp.setOnClickListener(v -> SignUp());
    }
    private void SignUp(){
        Intent intent = new Intent(LogIn.this, SignUp.class);
        startActivity(intent);
        finish();
    }

    private void Validation(String username, String password, DatabaseReference db) {
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    User user = userSnapshot.getValue(User.class);
                    if (user != null && user.getUsername().equals(username) && user.getPassword().equals(password)) {
                        Toast.makeText(LogIn.this, "Logged in successfully", Toast.LENGTH_SHORT).show();

                        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putString("id", user.getId());
                        editor.putString("username", user.getUsername());
                        editor.putString("password", user.getPassword());

                        String role = (db.getKey().equals("Admin")) ? "Admin" : "Client";
                        editor.putString("roles", role);
                        editor.apply();

                        // Navigate to appropriate activity based on role
                        Intent intent;
                        if (role.equals("Admin")) {
                            intent = new Intent(LogIn.this, AdminHP.class);
                        } else {
                            intent = new Intent(LogIn.this, ClientHP.class);
                        }

                        startActivity(intent);
                        finish();
                        return; // Exit after finding the user
                    }
                }

                // Show message if user is not found
                Toast.makeText(LogIn.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LogIn.this, "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}