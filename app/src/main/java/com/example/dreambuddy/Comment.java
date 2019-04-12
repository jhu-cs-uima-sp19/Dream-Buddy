package com.example.dreambuddy;

import java.util.Date;

/**
 * This class stores data about singular comments given to posts.
 */
public class Comment {

    /** Date of creation/last edit date. */
    private Date date;

    /** The body of the comment. */
    private String body;

    /** Username of author. */
    private String username;

    public Comment(String body, String username) {
        this.date = new Date();
        this.body = body;
        this.username = username;
    }

    public Comment() {

    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    @Override
    public String toString() {
        return username + ": " + body;
    }
}
