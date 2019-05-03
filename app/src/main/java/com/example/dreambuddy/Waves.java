package com.example.dreambuddy;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class Waves extends AppCompatActivity {

    private AudioAdapter adapter;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    launchHome();
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.navigation_learn:
                    launchLearn();
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.navigation_new_post:
                    launchNewPost();
                    return true;
                case R.id.navigation_waves:
                    return true;
                case R.id.navigation_profile:
                    launchProfile();
                    overridePendingTransition(0, 0);
                    return true;
            }
            return false;
        }
    };

    public void launchLearn(){
        Intent intent = new Intent(this, Learn.class);
        Waves.this.startActivity(intent);
    }

    public void launchHome(){
        Intent intent = new Intent(this, MainActivity.class);
        Waves.this.startActivity(intent);
    }

    public void launchProfile(){
        Intent intent = new Intent(this, Profile.class);
        Waves.this.startActivity(intent);
    }

    public void launchNewPost(){
        Intent intent = new Intent(this, NewPost.class);
        Waves.this.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waves);;
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);
        TextView title = findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));
        title.setText(R.string.title_waves);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        ArrayList<AudioFile> audioList = new ArrayList<AudioFile>();
        AudioFile T1 = new AudioFile("Track1");
        audioList.add(T1);
        AudioFile T2 = new AudioFile("Track2");
        audioList.add(T2);
        AudioFile T3 = new AudioFile("Track3");
        audioList.add(T3);
        AudioFile T4 = new AudioFile("Track4");
        audioList.add(T4);

        RecyclerView recyclerView = findViewById(R.id.audio_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AudioAdapter(audioList, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        adapter.pauseAudio();
    }
}
