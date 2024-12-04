package com.example.real_timedbaccount;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


import com.squareup.picasso.Picasso;

public class BookView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book_view);


        TextView titleView = findViewById(R.id.title);
        TextView authorsView = findViewById(R.id.author);
        TextView isbnView = findViewById(R.id.isbn);
        ImageView thumbnailView = findViewById(R.id.bookImage);
        TextView synopsisView = findViewById(R.id.sypnosis);
        Button previewButton = findViewById(R.id.previewButton);

        String title = getIntent().getStringExtra("title");
        String authors = getIntent().getStringExtra("authors");
        String isbn = getIntent().getStringExtra("isbn");
        String thumbnailUrl = getIntent().getStringExtra("thumbnailUrl");
        String synopsis = getIntent().getStringExtra("synopsis");
        String previewLink = getIntent().getStringExtra("previewLink");


        titleView.setText(title);
        authorsView.setText(authors);
        isbnView.setText("ISBN: " + isbn);
        synopsisView.setText(synopsis);

        if (thumbnailUrl != null) {
           Picasso.get().load(thumbnailUrl).into(thumbnailView);
        }

        if (previewLink != null) {
            previewButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(previewLink));
                    startActivity(browserIntent);
                }
            });
        } else {
            previewButton.setEnabled(false);
            Toast.makeText(this, "No preview available for this book", Toast.LENGTH_SHORT).show();
        }
    }
}