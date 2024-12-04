package com.example.real_timedbaccount;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.HashSet;

public class ReturnBookAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Book> returnList;
    private HashSet<Integer> selectedBooks;

    public ReturnBookAdapter(Context context, ArrayList<Book> returnList) {
        this.context = context;
        this.returnList = returnList;
        this.selectedBooks = new HashSet<>();
        Log.d("ReturnBookAdapter", "Books loaded: " + returnList.size());

    }

    @Override
    public int getCount() {
        return returnList.size();
    }

    @Override
    public Object getItem(int position) {
        return returnList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_return_book, parent, false);
        }

        Book book = returnList.get(position);

        TextView title = convertView.findViewById(R.id.bookTitle);
        TextView author = convertView.findViewById(R.id.bookAuthor);
        TextView isbn = convertView.findViewById(R.id.bookIsbn);
        TextView dueDate = convertView.findViewById(R.id.bookDueDate);
        CheckBox checkBox = convertView.findViewById(R.id.checkboxReturn);

        title.setText(book.getTitle());
        author.setText(book.getAuthor());
        isbn.setText("ISBN: " + book.getIsbn());
        dueDate.setText("Due Date: " + book.getDueDate());

        checkBox.setChecked(book.isSelected());

        checkBox.setOnClickListener(v -> {
            book.setSelected(!book.isSelected());

            if (book.isSelected()) {
                selectedBooks.add(position);
            } else {
                selectedBooks.remove(position);
            }

            Log.d("ReturnBookAdapter", "Selected books: " + selectedBooks);
        });

        return convertView;
    }


    public ArrayList<Book> getSelectedBooks() {
        ArrayList<Book> selected = new ArrayList<>();
        for (Integer index : selectedBooks) {
            selected.add(returnList.get(index));
        }
        return selected;
    }
}
