package com.example.real_timedbaccount;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AdminHP extends AppCompatActivity {
    TextView title;
    Button update, view, logout;
    SharedPreferences data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_hp);

        title = findViewById(R.id.AdminHP);
        update = findViewById(R.id.UpdateInfo);
        view = findViewById(R.id.ViewClients);
        logout = findViewById(R.id.LogOut);

        data = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String username = data.getString("username", "Error");
        title.setText("Welcome back, " + username + "!");

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHP.this, UpdateInfo.class);
                startActivity(intent);
            }
        });

            view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHP.this, ViewAllUser.class);
                startActivity(intent);
            }
                       });
        logout.setOnClickListener(v -> logout());
    }

    private void logout() {
        SharedPreferences.Editor editor = data.edit();
        editor.clear();
        editor.apply();

        Toast.makeText(this, "Logged out successfully!", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, LogIn.class);
        startActivity(intent);
        finish();
    }
}