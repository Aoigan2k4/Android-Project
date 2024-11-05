package com.example.libraryapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
//
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CartActivity extends AppCompatActivity {
    private RecyclerView cartRecyclerView;
    private CartAdapter cartAdapter;
    private ArrayList<Book> cartList;
    private Button btnRentAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartRecyclerView = findViewById(R.id.cartRecyclerView);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Book> cartList = (ArrayList<Book>) getIntent().getSerializableExtra("cartList");


        if (cartList == null) {
            cartList = new ArrayList<>();
        }

        cartAdapter = new CartAdapter(cartList);
        cartRecyclerView.setAdapter(cartAdapter);

        btnRentAll = findViewById(R.id.btnRentAll);
        btnRentAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rentAllBooks();
            }
        });
    }

    private void rentAllBooks() {

        for (Book book : cartList) {
            rentBook(book);
        }

        cartList.clear();
        cartAdapter.notifyDataSetChanged();
    }

    private void rentBook(Book book) {

////        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("rentedBooks").child(BorrowBookActicity.userId);
//
//        dbRef.push().setValue(book);
    }

    private String calculateDueDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);  // Add 1 month
        Date dueDate = calendar.getTime();

        // Format the date to display it in a readable format (e.g., "MM/dd/yyyy")
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        return sdf.format(dueDate);
    }
}