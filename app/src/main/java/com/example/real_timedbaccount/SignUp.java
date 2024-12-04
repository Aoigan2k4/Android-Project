package com.example.real_timedbaccount;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    EditText usernameTxt, passTxt, conPassTxt;
    Button signUpBtn;
    DatabaseReference db;
    RadioGroup roleRadioGroup;
    TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        usernameTxt = findViewById(R.id.Username);
        passTxt = findViewById(R.id.Password);
        conPassTxt = findViewById(R.id.ConfirmPassword);
        signUpBtn = findViewById(R.id.SignUpBtn);
        roleRadioGroup = findViewById(R.id.roleRadioGroup);
        login = findViewById(R.id.loginLink);
        signUpBtn.setOnClickListener(v -> signUp());
        login.setOnClickListener(v -> LogInLink());

    }

    private void LogInLink(){
        Intent intent = new Intent(SignUp.this, LogIn.class);
        startActivity(intent);
        finish();
    }

    private void signUp() {
        String username = usernameTxt.getText().toString().trim();
        String password = passTxt.getText().toString().trim();
        String confirmPassword = conPassTxt.getText().toString().trim();
        int selectedRoleId = roleRadioGroup.getCheckedRadioButtonId();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || selectedRoleId == -1) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
        }
        else {
            if (!password.equals(confirmPassword)) {
                conPassTxt.setError("Passwords do not match.");
                return;
            }

            if (selectedRoleId == R.id.clientRadioButton) {
                db = FirebaseDatabase.getInstance().getReference("Users").child("Client");
            } else if (selectedRoleId == R.id.librarianRadioButton) {
                db = FirebaseDatabase.getInstance().getReference("Users").child("Admin");
            }

            String id = db.push().getKey();
            User user = new User(id, username, password);

            if (id !=null){
                db.child(id).setValue(user);
                Toast.makeText(this, "Signed up successfully!", Toast.LENGTH_SHORT).show();
                usernameTxt.setText("");
                passTxt.setText("");
                conPassTxt.setText("");
                LogInLink();
            }
        }
    }
}