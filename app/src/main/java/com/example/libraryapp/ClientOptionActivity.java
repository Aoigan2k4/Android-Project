package com.example.libraryapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ClientOptionActivity extends AppCompatActivity {
    Button btn_borrow, btn_current, btn_return, btn_manage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_option);

        btn_borrow = findViewById(R.id.btn_borrow);
        btn_current = findViewById(R.id.btn_current);
        btn_return = findViewById(R.id.btn_return);
        btn_manage = findViewById(R.id.btn_manage);


        btn_borrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClientOptionActivity.this, BorrowBookActicity.class);
                startActivity(intent);
            }
        });

        btn_current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btn_manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}