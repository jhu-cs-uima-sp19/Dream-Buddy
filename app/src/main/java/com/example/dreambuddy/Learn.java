package com.example.dreambuddy;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class Learn extends AppCompatActivity {

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
                    return true;
                case R.id.navigation_new_post:
                    launchNewPost();
                    return true;
                case R.id.navigation_waves:
                    launchWaves();
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.navigation_profile:
                    launchProfile();
                    overridePendingTransition(0, 0);
                    return true;
            }
            return false;
        }
    };

    public void launchHome(){
        Intent intent = new Intent(this, MainActivity.class);
        Learn.this.startActivity(intent);
    }

    public void launchWaves(){
        Intent intent = new Intent(this, Waves.class);
        Learn.this.startActivity(intent);
    }

    public void launchProfile(){
        Intent intent = new Intent(this, Profile.class);
        Learn.this.startActivity(intent);
    }

    public void launchNewPost(){
        Intent intent = new Intent(this, NewPost.class);
        Learn.this.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);
        TextView title = findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));
        title.setText(R.string.title_learn);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }
}
