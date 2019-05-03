package com.example.dreambuddy;

import android.media.MediaPlayer;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class is used to facilitate working with data for a journal post.
 */
public class User implements Serializable {

    /** The username of the user. */
    private String username;

    /** Internal ID code of the user. */
    private String user_id;

    /** Posts liked by the user. */
    private List<String> liked_posts;

    /** Posts made by the user. */
    private List<String> owned_posts;

    private User() {
        this.liked_posts = new ArrayList<>();
    }

    public User(String username) {
        this();
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public List<String> getLiked_posts() {
        return liked_posts;
    }

    public void setLiked_posts(List liked_posts) {
        this.liked_posts = liked_posts;
    }

    public void addToLikedPosts(String post_id) {
        this.liked_posts.add(post_id);
    }

    public void removeFromLikedPosts(String post_id) {
        this.liked_posts.remove(post_id);
    }

    public boolean likedPost(String post_id) {
        return liked_posts.contains(post_id);
    }

    public List<String> getOwned_posts() {
        return owned_posts;
    }

    public void setOwned_posts(List liked_posts) {
        this.owned_posts = owned_posts;
    }

    public void addToOwnedPosts(String post_id) {
        this.owned_posts.add(post_id);
    }

    public void removeFromOwnedPosts(String post_id) {
        this.owned_posts.remove(post_id);
    }

    public boolean ownsPost(String post_id) {
        return owned_posts.contains(post_id);
    }

    public void createToFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");

        DatabaseReference pushedUserRef = myRef.push();
        this.user_id = pushedUserRef.getKey();
        pushedUserRef.setValue(this);
    }

    public void updateToFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");

        myRef.child(this.user_id).setValue(this);
    }

    public void deleteFromFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");

        myRef.child(this.user_id).removeValue();
    }
}
