package com.example.real_timedbaccount;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String id;
    private String username;
    private String password;
    private String confirmPassword;
    private List<Book> bookshelf;

    private User(){}

    public User(String id, String name, String password) {
        this.id = id;
        this.username = name;
        this.password = password;
        this.bookshelf = new ArrayList<Book>();
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

    public List<Book> getBooks (){
        return bookshelf;
    }

}
