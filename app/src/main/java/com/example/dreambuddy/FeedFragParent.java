package com.example.dreambuddy;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class FeedFragParent extends Fragment{

    protected RecyclerView recyclerView;
    protected RecyclerView.Adapter mAdapter;
    protected RecyclerView.LayoutManager layoutManager;
    protected ArrayList<JournalEntry> myDataset;

    protected void setUpRecyclerView(final boolean isPrivate) {
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        myDataset = new ArrayList<JournalEntry>(10);

        mAdapter = new MyAdapter(myDataset, this.getContext());
        recyclerView.setAdapter(mAdapter);

        startDatabaseListener(isPrivate);
    }

    // Read from the database
    protected void startDatabaseListener(final boolean isPrivate) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("posts");

        SharedPreferences preferences = getActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        final String curUsername = preferences.getString("username", "default user");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                myDataset.clear();
                //Toast.makeText(getContext(), "Loaded", Toast.LENGTH_SHORT).show();
                for(DataSnapshot d: dataSnapshot.getChildren()) {
                    JournalEntry curPost = d.getValue(JournalEntry.class);
                    if (isPrivate) {
                        //we are on private feed
                        //show ALL posts belonging to this user
                        if (curPost.getUsername().equals(curUsername)) {
                            myDataset.add(curPost);
                        }
                    }
                    else {
                        //we are on public feed
                        if (curPost.getIsPrivate() == false) {
                            myDataset.add(curPost);
                        }
                    }
                }
                Collections.reverse(myDataset);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("logTag", "Failed to read value.", error.toException());
            }
        });
    }
}

