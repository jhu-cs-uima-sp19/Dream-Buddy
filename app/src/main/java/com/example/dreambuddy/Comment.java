package com.example.dreambuddy;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * This class stores data about singular comments given to posts.
 */
public class Comment implements Serializable {

    /** Date of creation/last edit date. */
    private String date;

    /** The body of the comment. */
    private String body;

    /** Unique user ID of the author. */
    private String author_id;

    public Comment(String body, String author_id) {
        String pattern = "MM/dd/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        this.date = simpleDateFormat.format(new Date());
        this.body = body;
        this.author_id = author_id;
    }

    public Comment() {

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAuthor_id() {
        return author_id;
    }

//
//    @Override
//    public String toString() {
//        return username + ": " + body;
//    }
}
