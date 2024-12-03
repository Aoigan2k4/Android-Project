package com.example.real_timedbaccount;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class BookshelfAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Book> books;

    public BookshelfAdapter(Context context, ArrayList<Book> books) {
        this.context = context;
        this.books = books;
    }

    @Override
    public int getCount() {
        return books.size();
    }

    @Override
    public Object getItem(int position) {
        return books.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_bookshelf, parent, false);
            holder = new ViewHolder();
            holder.title = convertView.findViewById(R.id.bookTitle);
            holder.author = convertView.findViewById(R.id.bookAuthor);
            holder.icon = convertView.findViewById(R.id.bookIcon);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Book book = books.get(position);
        holder.title.setText(book.getTitle());
        holder.author.setText(book.getAuthor());
        // You can load book images into `holder.icon` if you have image URLs

        return convertView;
    }

    private static class ViewHolder {
        TextView title, author;
        AppCompatImageView icon;
    }
}
