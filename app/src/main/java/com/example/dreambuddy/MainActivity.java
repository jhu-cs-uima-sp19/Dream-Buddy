package com.example.dreambuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_learn:
                    launchLearn();
                    overridePendingTransition(0, 0);
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

    public void launchLearn(){
        Intent intent = new Intent(this, Learn.class);
        MainActivity.this.startActivity(intent);
    }

    public void launchWaves(){
        Intent intent = new Intent(this, Waves.class);
        MainActivity.this.startActivity(intent);
    }

    public void launchProfile(){
        Intent intent = new Intent(this, Profile.class);
        MainActivity.this.startActivity(intent);
    }

    public void launchNewPost(){
        Intent intent = new Intent(this, NewPost.class);
        MainActivity.this.startActivity(intent);
    }

    private void setupViewPager(ViewPager v) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new Public_frag(), "Public");
        adapter.addFragment(new Private_frag(), "Private");
        v.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        SectionsPageAdapter m = new SectionsPageAdapter(getSupportFragmentManager());
        ViewPager vp = findViewById(R.id.pager);
        setupViewPager(vp);

        TabLayout tl = findViewById(R.id.tabs);
        tl.setupWithViewPager(vp);

    }

}
