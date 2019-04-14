package com.example.dreambuddy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

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
        public ImageButton editImageButton;

        public MyViewHolder(View v) {
            super(v);
            titleTextView = v.findViewById(R.id.postTitle);
            likesTextView = v.findViewById(R.id.likesCount);
            editImageButton = v.findViewById(R.id.editButton);
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
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final JournalEntry curEntry = mDataset.get(position);
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.titleTextView.setText(curEntry.getTitle());
        holder.likesTextView.setText(String.valueOf(curEntry.getLikes()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewPost.class);
                intent.putExtra("journalEntry", curEntry);
                context.startActivity(intent);
            }
        });

        SharedPreferences preferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        final String curUsername = preferences.getString("username", "default user");
        if (curEntry.getUsername().equals(curUsername)) {
            //this user owns this post
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

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
