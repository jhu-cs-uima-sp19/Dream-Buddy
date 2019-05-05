package com.example.dreambuddy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private ArrayList<JournalEntry> mDataset;
    private Context context;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView titleTextView;
        public TextView likesTextView;
        public TextView commentsTextView;
        public ImageButton editImageButton;
        public AppCompatImageView empty_heart;
        public AppCompatImageView filled_heart;
        public Button like_area;

        public MyViewHolder(View v) {
            super(v);
            titleTextView = v.findViewById(R.id.postTitle);
            likesTextView = v.findViewById(R.id.likesCount);
            editImageButton = v.findViewById(R.id.editButton);
            like_area = v.findViewById(R.id.like_area);
            filled_heart = v.findViewById(R.id.filled_heart);
            empty_heart = v.findViewById(R.id.empty_heart);
            commentsTextView = v.findViewById(R.id.commentCount);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList<JournalEntry> myDataset, Context context) {
        this.mDataset = myDataset;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.posts_item, parent, false);

        MyViewHolder vh = new MyViewHolder(view);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        SharedPreferences preferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        final String curUser_id = preferences.getString("user_id", "default user");

        final JournalEntry curEntry = mDataset.get(position);

        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.titleTextView.setText(curEntry.getTitle());
        holder.likesTextView.setText(String.valueOf(curEntry.getLikes()));
        holder.commentsTextView.setText(String.valueOf(curEntry.getNumComments()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewPost.class);
                intent.putExtra("journalEntry", curEntry);
                context.startActivity(intent);
            }
        });

        if (curEntry.getAuthor_id().equals(curUser_id)) {
            //this user owns this post
            holder.editImageButton.setVisibility(View.VISIBLE);
            holder.editImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, EditPost.class);
                    intent.putExtra("journalEntry", curEntry);
                    context.startActivity(intent);
                }
            });
        }
        else {
            //this post belongs to another user
            holder.editImageButton.setVisibility(View.INVISIBLE);
        }

        final int likes = curEntry.getLikes();
        final boolean post_liked = curEntry.liked_by_user(curUser_id);

        if (post_liked) {
            holder.filled_heart.setVisibility(View.VISIBLE);
            holder.empty_heart.setVisibility(View.INVISIBLE);

            holder.like_area.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    curEntry.removeUserLike(curUser_id);
                    curEntry.setLikes(likes - 1);
                    curEntry.updateToFirebase();
                }
            });
        } else {
            holder.empty_heart.setVisibility(View.VISIBLE);
            holder.filled_heart.setVisibility(View.INVISIBLE);

            holder.like_area.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    curEntry.addUserLike(curUser_id);
                    curEntry.setLikes(likes + 1);
                    curEntry.updateToFirebase();
                }
            });
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
