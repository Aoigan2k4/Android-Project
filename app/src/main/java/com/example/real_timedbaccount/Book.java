package com.example.real_timedbaccount;


import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {
    private String title;
    private String author;
    private String isbn;
    private String imageURL;
    private String synopsis;
    private String previewLink;
    private String dueDate;
    private boolean isSelected;

    public Book() {}


    // Constructor for the Book class
    public Book(String title, String author, String isbn, String imageURL, String synopsis, String previewLink, String dueDate) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.imageURL = imageURL;
        this.synopsis = synopsis;
        this.previewLink = previewLink;
        this.dueDate = dueDate;
    }

    // Parcelable constructor
    protected Book(Parcel in) {
        title = in.readString();
        author = in.readString();
        isbn = in.readString();
        imageURL = in.readString();
        synopsis = in.readString();
        previewLink = in.readString();
        dueDate = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(isbn);
        dest.writeString(imageURL);
        dest.writeString(synopsis);
        dest.writeString(previewLink);
        dest.writeString(dueDate);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Getter and Setter methods for the fields

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getPreviewLink() {
        return previewLink;
    }

    public void setPreviewLink(String previewLink) {
        this.previewLink = previewLink;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


}

