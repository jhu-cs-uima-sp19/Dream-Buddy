package com.example.dreambuddy;

import android.media.MediaPlayer;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is used to facilitate working with data for a journal post.
 */
public class JournalEntry implements Serializable {

    /** The date created/last edited. */
    private String date;

    /** The title of the journal entry. */
    private String title;

    /** The body of the journal entry. */
    private String body;

    /** The user_id of the author. */
    private String author_id;

    /** The number of likes given to the post. */
    private int likes;

    /** The username of the author. */
    private String username;

    /** The audio content of the post. */
    private MediaPlayer audioContent;

    /** A list of comments given to the post. */
    private List<Comment> comments;

    /** Number of comments given to the post. */
    private int numComments;

    /** A list of user ids who liked the post. */
    private List<String> liked_by_whom;

    /** Whether the post is private or public. */
    private boolean isPrivate;

    /** Internal ID code of the journal post. */
    private String id;

    /** Pattern to use to format dates. */
    private static String pattern = "MM/dd/yyyy";

    private JournalEntry() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        this.date = simpleDateFormat.format(new Date());
        this.title = "";
        this.body = "";
        this.likes = 0;
        comments = new ArrayList<>();
        liked_by_whom = new ArrayList<>();
        //id is set when we save to firebase for the first time
    }

    public JournalEntry(String title, String author_id, String body, MediaPlayer audioContent, boolean isPrivate) {
        this();
        this.title = title;
        this.body = body;
        this.author_id = author_id;
        this.audioContent = audioContent;
        this.isPrivate = isPrivate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void updateDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        this.date = simpleDateFormat.format(new Date());
    }

    public String getAuthor_id() {
        return author_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLikes() { return likes; }

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

    public int getNumComments() {
        return numComments;
    }

    public void setNumComments(int numComments) {
        this.numComments = numComments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public boolean liked_by_user(String user_id) {
        return liked_by_whom.contains(user_id);
    }

    public void addUserLike(String user_id) {
        this.liked_by_whom.add(user_id);
    }

    public void removeUserLike(String user_id) {
        this.liked_by_whom.remove(user_id);
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
        numComments = comments.size();
    }

    public Comment deleteComment(Comment comment) {
        this.comments.remove(comment);
        numComments = comments.size();
        return comment;
    }

    public List<String> getLiked_by_whom() {
        return liked_by_whom;
    }

    public void setLiked_by_whom(List<String> liked_by_whom) {
        this.liked_by_whom = liked_by_whom;
    }


    public boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public void createToFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("posts");

        //if no comments exist, this won't save comments. So no comments field shows in firebase

        DatabaseReference pushedPostRef = myRef.push();
        this.id = pushedPostRef.getKey();
        pushedPostRef.setValue(this);
    }

    public void updateToFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("posts");

        myRef.child(this.id).setValue(this);
    }

    public void deleteFromFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        myRef.child("users").child(this.author_id).child("owned_posts").child(this.id).removeValue();
        myRef.child("posts").child(this.id).removeValue();
    }
}
