package com.example.dreambuddy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

public class NewPost extends AppCompatActivity {

    private static final int DELETE_POST_REQUEST = 1;

    ImageButton deleteAud;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.exit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_exit) {

            Intent intent = new Intent(this, PopUpChecker1.class);
            startActivityForResult(intent, DELETE_POST_REQUEST);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check that it is the SecondActivity with an OK result
        if (requestCode == DELETE_POST_REQUEST) {
            if (resultCode == RESULT_OK) {

                // Get String data from Intent
                if (data.getBooleanExtra("toDelete", false)) {
                    finish();
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);
        TextView title = findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));
        title.setText(R.string.title_new_post);

        deleteAud = (ImageButton) findViewById(R.id.deleteAud);

        deleteAud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PopUpChecker1.class);
                startActivity(intent);
            }
        });

        final EditText titleEditTextView = this.findViewById(R.id.editPostTitle);
        final EditText bodyEditTextView = this.findViewById(R.id.mainText);
        final Switch togglePublicPrivate = this.findViewById(R.id.toggle);
        SharedPreferences preferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        final String curUsername = preferences.getString("username", "default user");


        Button postButton = this.findViewById(R.id.saveEditPostButton);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                JournalEntry post = new JournalEntry(titleEditTextView.getText().toString(), curUsername, bodyEditTextView.getText().toString(), null, togglePublicPrivate.isChecked());

                //update firebase
                post.createToFirebase();

                //close out this view
                finish();
            }
        });
    }


}
