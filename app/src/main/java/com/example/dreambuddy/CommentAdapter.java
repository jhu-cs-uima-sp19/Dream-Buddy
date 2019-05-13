package com.example.dreambuddy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView usernameTextView;
        public TextView commentBodyTextView;

        public ViewHolder(View v) {
            super(v);

            usernameTextView = (TextView) itemView.findViewById(R.id.post_Creator);
            commentBodyTextView = (TextView) itemView.findViewById(R.id.commentBody);

        }

    }

    // member variable for comments
    private List<Comment> mComments;

    public CommentAdapter(List<Comment> comments) {
        mComments = comments;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View commentView = inflater.inflate(R.layout.comment_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(commentView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(CommentAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Comment comment = mComments.get(position);

        final TextView nameTextView = viewHolder.usernameTextView;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("users").child(comment.getAuthor_id());
        userRef.child("username").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue(String.class);
                nameTextView.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final TextView commentBody = viewHolder.commentBodyTextView;
        commentBody.setText(comment.getBody());
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mComments.size();
    }
}
