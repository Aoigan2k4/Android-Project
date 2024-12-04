package com.example.real_timedbaccount;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateInfo extends AppCompatActivity {
    EditText usernameTxt, passTxt, conPassTxt;
    Button UpdateBtn;
    TextView menu;
    DatabaseReference db;

    String oldUsername;
    String oldPassword ;
    String id;
    String role;
    SharedPreferences data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info);

        data = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        id = data.getString("id","Error");
        oldPassword = data.getString("password", "Error");
        oldUsername = data.getString("username", "Error");
        role = data.getString("roles", "Error");

        usernameTxt = findViewById(R.id.Username);
        passTxt = findViewById(R.id.Password);
        conPassTxt = findViewById(R.id.ConfirmPassword);
        UpdateBtn = findViewById(R.id.UpdateBtn);
        menu = findViewById(R.id.returnLink);

        usernameTxt.setText(oldUsername);
        passTxt.setText(oldPassword);
        conPassTxt.setText(oldPassword);

        UpdateBtn.setOnClickListener(v -> Update());
        menu.setOnClickListener(v -> ReturnMenu(role));
    }

    private void Update(){
        String inputName = usernameTxt.getText().toString();
        String inputPass = passTxt.getText().toString();
        String inputConPass = conPassTxt.getText().toString();

        data = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        id = data.getString("id","Error");
        role = data.getString("roles", "Error");
        oldPassword = data.getString("password", "Error");
        oldUsername = data.getString("username", "Error");

        if (inputName.isEmpty() || inputPass.isEmpty() || inputConPass.isEmpty()) {
            Toast.makeText(this, "Please fill all the fields!", Toast.LENGTH_SHORT).show();
        }

        db = FirebaseDatabase.getInstance().getReference("Users").child(role).child(id);

        db.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    SharedPreferences.Editor editor = data.edit();

                    if (ifNameChanged(inputName, oldUsername)) {
                        snapshot.getRef().child("username").setValue(inputName);
                        editor.putString("username", inputName);
                    }

                    if (ifPassChanged(inputPass, oldPassword)) {
                        snapshot.getRef().child("password").setValue(inputPass);
                        editor.putString("password", inputPass);
                    }

                    editor.apply();
                    Toast.makeText(UpdateInfo.this, "Info is updated successfully!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(UpdateInfo.this, "User not found in the database!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateInfo.this, "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean ifNameChanged (String name, String old){
        return !name.equals(old);
    }

    private boolean ifPassChanged (String pass, String old){
        return !pass.equals(old);
    }

    private void ReturnMenu(String roles){
        Intent intent;
        switch (roles){
            case "Admin":
                intent = new Intent(UpdateInfo.this, AdminHP.class);
                startActivity(intent);
                finish();
                break;
            case "Client":
                intent = new Intent(UpdateInfo.this, ClientHP.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}