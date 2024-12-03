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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class BorrowBookActivity extends AppCompatActivity {
    private GridView gridView;
    private SearchView searchView;
    private ArrayList<Book> booksList = new ArrayList<>();
    private BookAdapter bookAdapter;
    private RequestQueue requestQueue;
    private ArrayList<Book> cartList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow_book);

        gridView = findViewById(R.id.gridView);
        searchView = findViewById(R.id.searchView);
        Button viewCartButton = findViewById(R.id.btn_viewCart);

        requestQueue = Volley.newRequestQueue(this);


        bookAdapter = new BookAdapter(this, booksList, cartList);
        gridView.setAdapter(bookAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                booksList.clear();
                searchBooks(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        viewCartButton.setOnClickListener(v -> {
            Intent intent = new Intent(BorrowBookActivity.this, CartActivity.class);
            intent.putParcelableArrayListExtra("cartList", cartList);
            startActivity(intent);
        });
    }

    //1

    private void searchBooks(String query) {
        String apiKey = "AIzaSyAp0wNzgYVoYZeokaaGocIW2_fBM1yynY4";
        String requestUrl = "https://www.googleapis.com/books/v1/volumes?q=" + query + "&key=" + apiKey;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                requestUrl, null,
                response -> {
                    try {
                        JSONArray items = response.getJSONArray("items");
                        for (int i = 0; i < items.length(); i++) {
                            JSONObject item = items.getJSONObject(i);
                            JSONObject volumeInfo = item.getJSONObject("volumeInfo");

                            String title = volumeInfo.getString("title");
                            String authors = volumeInfo.has("authors") ?
                                    volumeInfo.getJSONArray("authors").join(", ").replace("\"", "") : "Unknown Author";
                            String thumbnailUrl = volumeInfo.has("imageLinks") ?
                                    volumeInfo.getJSONObject("imageLinks").getString("thumbnail") : null;

                            String isbn = "N/A";
                            if (volumeInfo.has("industryIdentifiers")) {
                                JSONArray identifiers = volumeInfo.getJSONArray("industryIdentifiers");
                                for (int j = 0; j < identifiers.length(); j++) {
                                    JSONObject identifier = identifiers.getJSONObject(j);
                                    if (identifier.getString("type").equals("ISBN_13")) {
                                        isbn = identifier.getString("identifier");
                                        break;
                                    }
                                }
                            }

                            String synopsis = volumeInfo.has("description") ?
                                    volumeInfo.getString("description") : "No description available.";

                            booksList.add(new Book(title, authors, isbn, thumbnailUrl, synopsis, null, null));
                        }
                        bookAdapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        Toast.makeText(BorrowBookActivity.this, "Error parsing data", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(BorrowBookActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show()
        );

        requestQueue.add(jsonObjectRequest);
    }

}
