package com.example.dreambuddy;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class stores data about singular comments given to posts.
 */
public class Comment implements Serializable {

    /** Date of creation/last edit date. */
    private String date;

    /** The body of the comment. */
    private String body;

    /** Username of author. */
    private String username;

    /** Unique user ID of the author. */
    private String author_id;

    public Comment(String body, String username, String author_id) {
        String pattern = "MMMMM dd, yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        this.date = simpleDateFormat.format(new Date());
        this.body = body;
        this.username = username;
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

    public void updateDate() {
        String pattern = "MMMMM dd, yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        this.date = simpleDateFormat.format(new Date());
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthor_id() {
        return author_id;
    }

    @Override
    public String toString() {
        return username + ": " + body;
    }
}
