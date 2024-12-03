package com.example.real_timedbaccount;

import static okhttp3.internal.Internal.instance;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String id;
    private String username;
    private String password;
    private String confirmPassword;
    private ArrayList<Book> bookshelf;
    private ArrayList<Book> cartList;

    private User(){}

    public User(String id, String name, String password) {
        this.id = id;
        this.username = name;
        this.password = password;
        this.bookshelf = new ArrayList<>();
        this.cartList = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Book> getBookshelf() {
        return bookshelf;
    }

    public ArrayList<Book> getCartList() {
        return cartList;
    }

    public void addBookToBookshelf(Book book) {
        bookshelf.add(book);
    }

    public void removeBookFromBookshelf(Book book) {
        bookshelf.remove(book);
    }



}
