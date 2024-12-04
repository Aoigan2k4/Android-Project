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

public class ClientHP extends AppCompatActivity {
    TextView title;
    Button update, borrow, view, logout,btn_return,Books;
    SharedPreferences data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_hp);

        title = findViewById(R.id.ClientHP);
        update = findViewById(R.id.UpdateInfo);
        borrow = findViewById(R.id.Borrow);
        view = findViewById(R.id.Books);
        logout = findViewById(R.id.LogOut);
        btn_return = findViewById(R.id.btn_return);
        Books = findViewById(R.id.Books);

        data = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String username = data.getString("username", "Error");
        title.setText("Welcome back, " + username + "!");

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClientHP.this, UpdateInfo.class);
                startActivity(intent);
                finish();
            }
        });

        borrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClientHP.this, BorrowBookActivity.class);
                startActivity(intent);
            }
        });

        btn_return.setOnClickListener(view -> {
            // Navigate to the ReturnBookActivity
            Intent intent = new Intent(ClientHP.this, ReturnBookActivity.class);
            startActivity(intent);
        });

        Books.setOnClickListener(view -> {
            // Navigate to the ReturnBookActivity
            Intent intent = new Intent(ClientHP.this, BookshelfActivity.class);
            startActivity(intent);
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