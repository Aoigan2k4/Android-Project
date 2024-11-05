package com.example.libraryapp;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class BorrowBookActivity extends AppCompatActivity {
    private Button btn_viewCart;
    private EditText edit_searchBar;
    private RecyclerView recycle_bookRecyclerView;
    private BookAdapter bookAdapter;
    private ArrayList<Book> bookList; // display all books
    private ArrayList<Book> cartList; // display all the books selected by the cleitn and added to the cart

    private DatabaseReference databaseReference;
    private String userId = "client1";

//    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow_book_acticity);

        btn_viewCart = findViewById(R.id.btn_viewCart);
        edit_searchBar = findViewById(R.id.edit_searchBar);
        recycle_bookRecyclerView = findViewById(R.id.recycle_bookRecyclerView);

        bookList = new ArrayList<>();
        cartList = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId);

        // load books from Google Books API
        loadBooks();

        bookAdapter = new BookAdapter(this,bookList, cartList);
        recycle_bookRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recycle_bookRecyclerView.setAdapter(bookAdapter);


        btn_viewCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("BorrowBookActivity", "View Cart button clicked");
                Intent intent = new Intent(BorrowBookActivity.this,CartActivity.class);
                intent.putExtra("cartList", cartList);
                startActivity(intent);
            }
        });

    }
    public void loadBooks() {
        bookList.add(new Book("Book1", "Author1", "1"));
        bookList.add(new Book("Book2", "Author2", "2"));
    }
    private void addToCart(Book book) {
        // mAuth
        String userId = "client1"; // Use FirebaseAuth to get the current user ID

        databaseReference.child("users").child(userId).child("cart").push().setValue(book);

        // Add the book to the local cart list
        cartList.add(book);

        // Notify the adapter that the data has changed
        bookAdapter.notifyDataSetChanged();

        if (book != null) {
            Toast.makeText(BorrowBookActivity.this, book.getTitle() + " added to cart.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(BorrowBookActivity.this, "Error adding book to cart.", Toast.LENGTH_SHORT).show();
        }
    }
}