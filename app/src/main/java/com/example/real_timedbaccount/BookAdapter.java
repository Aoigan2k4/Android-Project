package com.example.real_timedbaccount;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BookAdapter extends ArrayAdapter<Book> {
    private ArrayList<Book> booksList;
    private ArrayList<Book> cartList;
    private Context context;

    public BookAdapter(Context context, ArrayList<Book> booksList, ArrayList<Book> cartList) {
        super(context, 0, booksList);
        this.booksList = booksList;
        this.cartList = cartList;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_book, parent, false);
        }

        Book book = booksList.get(position);

        TextView titleTextView = convertView.findViewById(R.id.book_title);
        ImageView bookImageView = convertView.findViewById(R.id.bookImage);
        Button addToCartButton = convertView.findViewById(R.id.btn_addToCart);

        titleTextView.setText(book.getTitle());

        if (book.getImageURL() != null) {
            Picasso.get().load(book.getImageURL()).into(bookImageView);
        } else {
            bookImageView.setImageResource(R.drawable.book_image);
        }

        addToCartButton.setOnClickListener(v -> {
            cartList.add(book);
            Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT).show();
        });

        return convertView;
    }
}
