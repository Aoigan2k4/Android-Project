package com.example.libraryapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private ArrayList<Book> bookList; // List of all available books
    private ArrayList<Book> cartList; // List of books added to cart
    private Context context;

    // Constructor
    public BookAdapter(Context context, ArrayList<Book> bookList, ArrayList<Book> cartList) {
        this.context = context;
        this.bookList = bookList;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.titleTextView.setText(book.getTitle());
        holder.authorTextView.setText(book.getAuthor());

        // Set an OnClickListener for the Add to Cart button
        holder.btnAddToCart.setOnClickListener(v -> {
            if (!cartList.contains(book)) {
                cartList.add(book); // Add the book to the cart
                Toast.makeText(context, book.getTitle() + " added to cart.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, book.getTitle() + " is already in the cart.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    // Method to update the list when searching
    public void updateList(ArrayList<Book> newList) {
        bookList = newList;
        notifyDataSetChanged();
    }

    // ViewHolder class
    public static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView authorTextView;
        Button btnAddToCart; // Button for adding to cart

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.bookTitle);
            authorTextView = itemView.findViewById(R.id.bookTitle);
            btnAddToCart = itemView.findViewById(R.id.btn_addToCart); // Initialize the button
        }
    }
}