package com.example.libraryapp.Controller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.libraryapp.R;
import com.google.firebase.BuildConfig;

public class searchBookController extends AppCompatActivity {

    private Button searchButton;
    private EditText searchInput;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_book);
        searchButton = findViewById(R.id.searchButton);
        searchInput = findViewById(R.id.bookInput);

    }
    private void searchBook() {
        String query = searchInput.getText().toString();
        // String apiKey = BuildConfig.api_key;   fix error here can't see the api Key

    }
}
