package com.example.dreambuddy;

import android.media.MediaPlayer;
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

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Public_frag extends Fragment{

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<JournalEntry> myDataset;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.public_tab, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.public_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        myDataset = new ArrayList<JournalEntry>(10);
        myDataset.add(new JournalEntry("Super cool", "evanmays", "Cool", null, false));
        myDataset.add(new JournalEntry("Almost cool", "evanmays", "Cool", null, false));
        myDataset.add(new JournalEntry("not as cool", "evanmays", "Cool", null, false));
        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(myDataset);
        recyclerView.setAdapter(mAdapter);

        startDatabaseListener();

        return view;
    }

    // Read from the database
    private void startDatabaseListener() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("posts");
        myRef.push().setValue(new JournalEntry("Super cool", "evanmays", "Cool", null, false));
        Toast.makeText(getContext(), "Here", Toast.LENGTH_SHORT).show();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                myDataset.clear();
                Toast.makeText(getContext(), "Loaded", Toast.LENGTH_SHORT).show();
                for(DataSnapshot d: dataSnapshot.getChildren()) {
                    JournalEntry cur = d.getValue(JournalEntry.class);
                    myDataset.add(cur);
                }

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

