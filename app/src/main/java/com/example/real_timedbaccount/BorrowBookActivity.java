package com.example.real_timedbaccount;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class BorrowBookActivity extends AppCompatActivity {

    private RequestQueue requestQueue;
    private GridView gridView;
    private ArrayList<Book> booksList = new ArrayList<>();
    private ArrayList<Book> cartList = new ArrayList<>();
    private BookAdapter bookAdapter;
    private DatabaseReference firebaseDatabase;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow_book);

        // Initialize UI components
        gridView = findViewById(R.id.gridView);
        searchView = findViewById(R.id.searchView);
        Button viewCartButton = findViewById(R.id.btn_viewCart);

        requestQueue = Volley.newRequestQueue(this);

        // Firebase database reference
        firebaseDatabase = FirebaseDatabase.getInstance().getReference("books");

        // Set up adapter
        bookAdapter = new BookAdapter(this, booksList, cartList);
        gridView.setAdapter(bookAdapter);

        // Load books from Firebase initially
        loadBooksFromFirebase();

        // Search books
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                booksList.clear(); // Clear previous results
                searchBooksFromFirebase(query);
                searchBooksFromGoogle(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                return false; // No action on text change
            }
        });

        // Navigate to CartActivity
        viewCartButton.setOnClickListener(v -> {
            Intent intent = new Intent(BorrowBookActivity.this, CartActivity.class);
            intent.putParcelableArrayListExtra("cartList", cartList);
            startActivity(intent);
        });
    }

    private void loadBooksFromFirebase() {
        firebaseDatabase.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DataSnapshot snapshot : task.getResult().getChildren()) {
                    Book book = snapshot.getValue(Book.class);
                    if (book != null) {
                        booksList.add(book);
                    }
                }
                bookAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "Failed to load books from Firebase.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void searchBooksFromFirebase(String query) {
        firebaseDatabase.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DataSnapshot snapshot : task.getResult().getChildren()) {
                    Book book = snapshot.getValue(Book.class);
                    if (book != null && book.getTitle().toLowerCase().contains(query.toLowerCase())) {
                        booksList.add(book);
                    }
                }
                bookAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "Error searching books in Firebase.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void searchBooksFromGoogle(String query) {
        String requestUrl = "https://www.googleapis.com/books/v1/volumes?q=" + query;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(requestUrl, null, response -> {
            try {
                JSONArray items = response.getJSONArray("items");
                for (int i = 0; i < items.length(); i++) {
                    JSONObject item = items.getJSONObject(i);
                    JSONObject volumeInfo = item.getJSONObject("volumeInfo");

                    String title = volumeInfo.optString("title");
                    String authors = volumeInfo.has("authors") ?
                            volumeInfo.getJSONArray("authors").join(", ").replace("\"", "") :
                            "Unknown";

                    String thumbnailUrl = volumeInfo.has("imageLinks") ?
                            volumeInfo.getJSONObject("imageLinks").optString("thumbnail") :
                            null;

                    String isbn = "N/A";
                    if (volumeInfo.has("industryIdentifiers")) {
                        JSONArray identifiers = volumeInfo.getJSONArray("industryIdentifiers");
                        for (int j = 0; j < identifiers.length(); j++) {
                            JSONObject identifier = identifiers.getJSONObject(j);
                            if ("ISBN_13".equals(identifier.optString("type"))) {
                                isbn = identifier.optString("identifier");
                                break;
                            }
                        }
                    }

                    String synopsis = volumeInfo.optString("description", "No description available.");
                    String previewLink = volumeInfo.optString("previewLink", null);

                    booksList.add(new Book(title, authors, isbn, thumbnailUrl, synopsis, previewLink, "Not Set"));
                }
                bookAdapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Error processing Google Books data.", Toast.LENGTH_SHORT).show();
            }
        }, error -> Toast.makeText(this, "Error fetching data from Google Books API.", Toast.LENGTH_SHORT).show());

        requestQueue.add(jsonObjectRequest);
    }
}
