package com.example.dreambuddy;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;

import java.util.ArrayList;

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

        ArrayList<WebSite> siteList = new ArrayList<WebSite>();
        WebSite W1 = new WebSite("https://www.reddit.com/r/LucidDreaming/comments/28x5fj/how_to_wbtb/", "WBTB", "Wake Back to Bed Method");
        siteList.add(W1);
        WebSite W2 = new WebSite("https://www.reddit.com/r/LucidDreaming/comments/3fqfz9/mild_tips/", "MILD", "Mnemonically Induced Lucid Dream Method");
        siteList.add(W2);
        WebSite W3 = new WebSite("https://www.reddit.com/r/LucidDreaming/comments/trz9z/beginners_wild_method_adjustments_tricks_and_tips/", "WILD", "Wake-Induced Lucid Dream Method");
        siteList.add(W3);
        WebSite W4 = new WebSite("https://www.reddit.com/r/LucidDreaming/comments/1dn2y8/detailed_tutorial_on_how_to_perform_the_fild/", "FILD", "Finger-Induced Lucid Dream Method");
        siteList.add(W4);
        WebSite W5 = new WebSite("https://www.reddit.com/r/LucidDreaming/comments/tnzmc/beginners_vild_visually_incubated_lucid_dream/", "VILD", "Visually Incubated Lucid Dream Method");
        siteList.add(W5);

        RecyclerView recyclerView = findViewById(R.id.learn_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        WebAdapter adapter = new WebAdapter(siteList, this);
        recyclerView.setAdapter(adapter);
    }
}
