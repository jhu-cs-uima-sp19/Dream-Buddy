package com.example.dreambuddy;

import android.media.MediaPlayer;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used to facilitate working with data for a journal post.
 */
public class JournalEntry {

    /** The date created/last edited. */
    private Date date;

    /** The title of the journal entry. */
    private String title;

    /** The body of the journal entry. */
    private String body;

    /** The username of the author. */
    private String username;

    /** The number of likes given to the post. */
    private int likes;

    /** The audio content of the post. */
    private MediaPlayer audioContent;

    /** A list of comments given to the post. */
    private List<Comment> comments;

    /** Whether the post is private or public. */
    private boolean isPrivate;

    /** Internal ID number of the journal post. */
    private int id;

    private JournalEntry() {
        this.date = new Date();
        this.title = "";
        this.body = "";
        this.likes = 0;
        comments = new ArrayList<>();
        // id = last id in database + 1?
    }

    public JournalEntry(String title, String username, String body, MediaPlayer audioContent, boolean isPrivate) {
        this();
        this.title = title;
        this.body = body;
        this.username = username;
        this.audioContent = audioContent;
        this.isPrivate = isPrivate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public MediaPlayer getAudioContent() {
        return audioContent;
    }

    public void setAudioContent(MediaPlayer audioContent) {
        this.audioContent = audioContent;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public Comment deleteComment(Comment comment) {
        this.comments.remove(comment);
        return comment;
    }

    public boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public void saveToFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        //doesnt save comments yet

        //myRef.setValue("Hello, World!");
        myRef.push().setValue(this);
    }
}
