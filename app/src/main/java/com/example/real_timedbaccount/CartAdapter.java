package com.example.real_timedbaccount;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private ArrayList<Book> cartList;

    public CartAdapter(ArrayList<Book> cartList) {
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Book book = cartList.get(position);
        holder.bookTitle.setText(book.getTitle());
        holder.bookAuthor.setText(book.getAuthor());

        String dueDate = calculateDueDate();
        holder.bookDueDate.setText(dueDate);
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView bookTitle, bookAuthor, bookDueDate;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            bookTitle = itemView.findViewById(R.id.bookTitle);
            bookAuthor = itemView.findViewById(R.id.bookAuthor);
            bookDueDate = itemView.findViewById(R.id.bookDueDate);
        }
    }
    private String calculateDueDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);  // Add 1 month to the current date
        Date dueDate = calendar.getTime();

        // Format the date to display it in a readable format (e.g., "MM/dd/yyyy")
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        return sdf.format(dueDate);  // Return the formatted date
    }
}
