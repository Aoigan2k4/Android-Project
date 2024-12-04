package com.example.real_timedbaccount;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
public class CartActivity extends AppCompatActivity {
    DatabaseReference db;
    SharedPreferences data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        data = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String userID = data.getString("id", "Error");

        if ("Error".equals(userID)) {
            Toast.makeText(this, "Error retrieving user ID.", Toast.LENGTH_SHORT).show();
            return;
        }

        db = FirebaseDatabase.getInstance()
                .getReference("Users/Client")
                .child(userID)
                .child("Bookshelf");

        ArrayList<Book> cartList = getIntent().getParcelableArrayListExtra("cartList");

        TextView cartDetails = findViewById(R.id.cart_details);
        if (cartList != null && !cartList.isEmpty()) {
            StringBuilder details = new StringBuilder();
            for (Book book : cartList) {
                details.append("Title: ").append(book.getTitle()).append("\n")
                        .append("Author: ").append(book.getAuthor()).append("\n")
                        .append("ISBN: ").append(book.getIsbn()).append("\n")
                        .append("Due Date: ").append(book.getDueDate()).append("\n\n");
            }
            cartDetails.setText(details.toString());
        } else {
            cartDetails.setText("Your cart is empty.");
        }

        Button rentAllButton = findViewById(R.id.btn_rentAll);
        rentAllButton.setOnClickListener(v -> {
            if (cartList != null && !cartList.isEmpty()) {
                rentBooks(cartList, cartDetails);
            } else {
                Toast.makeText(CartActivity.this, "No books in cart.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void rentBooks(ArrayList<Book> cartList, TextView cartDetails) {
        Button rentAllButton = findViewById(R.id.btn_rentAll);
        rentAllButton.setEnabled(false);

        cartDetails.setText("Renting books...");

        for (Book book : cartList) {
            DatabaseReference bookRef = db.push();
            bookRef.setValue(book).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    cartDetails.setText("Books successfully rented!");
                } else {
                    cartDetails.setText("Failed to rent books: " + task.getException().getMessage());
                }
            });
        }

        cartList.clear();
    }
}
