package com.example.real_timedbaccount;

import android.os.Bundle;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class BookshelfActivity extends AppCompatActivity {
    private GridView gridView;
    private BookshelfAdapter adapter;
    private ArrayList<Book> bookshelfList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookshelf);

        gridView = findViewById(R.id.bookshelfGridView);
        bookshelfList = new ArrayList<>();
        adapter = new BookshelfAdapter(this, bookshelfList);
        gridView.setAdapter(adapter);

        // Get the bookshelf from the intent
        ArrayList<Book> userBookshelf = (ArrayList<Book>) getIntent().getSerializableExtra("userBookshelf");
        if (userBookshelf != null) {
            bookshelfList.addAll(userBookshelf); // Add the books to the list
            adapter.notifyDataSetChanged(); // Update the GridView
        }
    }
}
