package com.example.real_timedbaccount;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BookshelfActivity extends AppCompatActivity {
    private GridView gridView;
    private BookshelfAdapter adapter;
    private ArrayList<Book> bookshelfList;
    private DatabaseReference dbRef;
    private SharedPreferences data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookshelf);

        gridView = findViewById(R.id.bookshelfGridView);
        bookshelfList = new ArrayList<>();
        adapter = new BookshelfAdapter(this, bookshelfList);
        gridView.setAdapter(adapter);

        data = getSharedPreferences("UserPrefs", MODE_PRIVATE);

        // Get userId from SharedPreferences
        String userId = data.getString("id", null);

        if (userId == null) {
            Toast.makeText(this, "Error retrieving user ID.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Initialize Firebase reference
        dbRef = FirebaseDatabase.getInstance()
                .getReference("Users/Client")
                .child(userId)
                .child("Bookshelf");

        // Load the bookshelf from Firebase
        loadBookshelfFromFirebase();
    }

    private void loadBookshelfFromFirebase() {
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bookshelfList.clear();  // Clear the list before adding new data
                if (snapshot.exists()) {
                    for (DataSnapshot bookSnapshot : snapshot.getChildren()) {
                        Book book = bookSnapshot.getValue(Book.class);
                        if (book != null) {
                            bookshelfList.add(book);  // Add each book to the list
                        }
                    }
                    adapter.notifyDataSetChanged();  // Refresh the GridView
                    Toast.makeText(BookshelfActivity.this, "Books loaded successfully.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(BookshelfActivity.this, "No books found in the bookshelf.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(BookshelfActivity.this, "Failed to load books: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
