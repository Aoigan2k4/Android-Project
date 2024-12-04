package com.example.real_timedbaccount;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BookAdapter extends ArrayAdapter<Book> {
    private ArrayList<Book> booksList;
    private Context context;
    private ArrayList<Book> cartList;

    // Constructor
    public BookAdapter(Context context, ArrayList<Book> booksList, ArrayList<Book> cartList) {
        super(context, 0, booksList);
        this.booksList = booksList;
        this.context = context;
        this.cartList = cartList;
    }


    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_book, parent, false);

        }
        Book books = getItem(position);
        Book currentBook = booksList.get(position);
        TextView titleTextView = convertView.findViewById(R.id.book_title);
        ImageView imageView = convertView.findViewById(R.id.bookImage);

        titleTextView.setText(books.getTitle());

        if(books.getImageURL() != null){
            Picasso.get().load(books.getImageURL()).into(imageView);
        }else{
            imageView.setImageResource(R.drawable.book_image);
        }
        Button addButton = convertView.findViewById(R.id.btn_addToCart);
        addButton.setOnClickListener(v -> {
            cartList.add(currentBook);
            Toast.makeText(context, "Book added to cart successfully!", Toast.LENGTH_SHORT).show();
        });
        return convertView;
    }

    @Override
    public int getCount() {
        return booksList.size();
    }
}
