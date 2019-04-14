package com.example.dreambuddy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class Profile extends AppCompatActivity {

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
                    launchWaves();
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.navigation_profile:
                    return true;
            }
            return false;
        }
    };

    public void launchLearn(){
        Intent intent = new Intent(this, Learn.class);
        Profile.this.startActivity(intent);
    }

    public void launchWaves(){
        Intent intent = new Intent(this, Waves.class);
        Profile.this.startActivity(intent);
    }

    public void launchHome(){
        Intent intent = new Intent(this, MainActivity.class);
        Profile.this.startActivity(intent);
    }

    public void launchNewPost(){
        Intent intent = new Intent(this, NewPost.class);
        Profile.this.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);
        TextView title = findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));
        title.setText(R.string.title_profile);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        final EditText publicNameText = this.findViewById(R.id.publicNameText);
        Context context = getApplicationContext();

        final SharedPreferences preferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        publicNameText.setText(preferences.getString("username", ""));
        publicNameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //--SAVE Data
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("username", publicNameText.getText().toString());
                editor.commit();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });
    }
}
