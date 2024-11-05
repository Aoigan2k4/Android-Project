package com.example.libraryapp.classes;

public class Book {
    private String imageBook;
    private String title;
    private String author;
    private String synopsis;
    private String isbn;

    public Book(String imageBook, String title, String author, String synopsis, String isbn) {
        this.imageBook = imageBook;
        this.title = title;
        this.author = author;
        this.synopsis = synopsis;
        this.isbn = isbn;
    }
    // getters for the attribute of the book
    public String getIsbn() {
        return isbn;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getImageBook() {
        return imageBook;
    }
}
