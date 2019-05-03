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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    Spinner spinner;

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
                final String new_name = publicNameText.getText().toString();
                editor.putString("username", new_name);
                editor.apply();

                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference cur_user = database.getReference("users");

                final String uid = preferences.getString("user_id", "default user");
                cur_user.child(uid).child("username").setValue(new_name);

                final DatabaseReference posts = database.getReference("posts");

                posts.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot d : dataSnapshot.getChildren()) {
                            JournalEntry post = d.getValue(JournalEntry.class);
                            if (post.getAuthor_id().equals(uid)) {
                                post.setUsername(new_name);
                                post.updateToFirebase();
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });

        spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
            R.array.durations_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        int dur = preferences.getInt("sound_wave_duration", 2);
        spinner.setSelection(dur/2 - 1);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();
                if (selected.equals("8:00")) {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt("sound_wave_duration", 8);
                    spinner.setSelection(3);
                    editor.commit();
                } else if (selected.equals("6:00")) {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt("sound_wave_duration", 6);
                    spinner.setSelection(2);
                    editor.commit();
                } else if (selected.equals("4:00")) {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt("sound_wave_duration", 4);
                    spinner.setSelection(1);
                    editor.commit();
                } else if (selected.equals("2:00")) {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt("sound_wave_duration", 2);
                    spinner.setSelection(0);
                    editor.commit();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}
