package com.example.real_timedbaccount;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ReturnBookActivity extends AppCompatActivity {

    private GridView gridView;
    private ReturnBookAdapter adapter;
    private ArrayList<Book> returnList;
    private Button btnReturnSelected;
    private DatabaseReference databaseReference;
    SharedPreferences data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_book);

        data = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String userId = data.getString("id", null);

        gridView = findViewById(R.id.book_returnGridView);
        btnReturnSelected = findViewById(R.id.btnReturnSelected);


        returnList = new ArrayList<>();
        adapter = new ReturnBookAdapter(this, returnList);
        gridView.setAdapter(adapter);


        databaseReference = FirebaseDatabase.getInstance()
                .getReference("Users/Client")
                .child(userId)
                .child("Bookshelf");
        loadReturnableBooks();

        btnReturnSelected.setOnClickListener(v -> {
            ArrayList<Book> selectedBooks = adapter.getSelectedBooks();

            if (selectedBooks.isEmpty()) {
                Toast.makeText(this, "No books selected to return.", Toast.LENGTH_SHORT).show();
                return;
            }

            for (Book book : selectedBooks) {
                returnBook(book);
            }

            returnList.removeAll(selectedBooks);
            adapter.notifyDataSetChanged();
        });
    }



    private void loadReturnableBooks() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                returnList.clear();  // Clear the list before adding new data
                if (snapshot.exists()) {
                    for (DataSnapshot bookSnapshot : snapshot.getChildren()) {
                        Book book = bookSnapshot.getValue(Book.class);
                        if (book != null) {
                            returnList.add(book);  // Add each book to the list
                        }
                    }
                    adapter.notifyDataSetChanged();  // Refresh the GridView
                    Toast.makeText(ReturnBookActivity.this, "Books loaded successfully.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ReturnBookActivity.this, "No books found in the bookshelf.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ReturnBookActivity.this, "Failed to load books: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void returnBook(Book book) {
        databaseReference.child(book.getIsbn()).removeValue()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Returned: " + book.getTitle(), Toast.LENGTH_SHORT).show();
                    Log.d("ReturnBookActivity", "Book removed from database");
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed to return: " + book.getTitle(), Toast.LENGTH_SHORT).show();
                    Log.d("ReturnBookActivity", "Failed to remove book: " + e.getMessage());
                });
    }

}
