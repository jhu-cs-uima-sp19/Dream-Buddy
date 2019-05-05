package com.example.dreambuddy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MakeComment extends AppCompatActivity {

    private static final int DELETE_COMMENT_REQUEST = 1;

    ImageButton closeBtn;

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
            startActivityForResult(intent, DELETE_COMMENT_REQUEST);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check that it's the PopUpChecker activity with an OK result
        if (requestCode == DELETE_COMMENT_REQUEST) {
            if (resultCode == RESULT_OK) {

                // Get boolean data from Intent
                if (data.getBooleanExtra("toDelete", false)) {
                    finish();
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_comment);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);
        TextView title = findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));
        title.setText("Add Comment");
        final JournalEntry post = (JournalEntry) getIntent().getSerializableExtra("post");

//        closeBtn = findViewById(R.id.close);
//
//        closeBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), PopUpChecker1.class);
//                startActivity(intent);
//            }
//        });

        final EditText post_title = this.findViewById(R.id.Post_Title);
        final EditText comment_box = this.findViewById(R.id.type_comment);
        final TextView send_comment = this.findViewById(R.id.send_comment);

        comment_box.setImeOptions(EditorInfo.IME_ACTION_DONE);
        comment_box.setRawInputType(InputType.TYPE_CLASS_TEXT);

        final SharedPreferences preferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        final String curUsername = preferences.getString("username", "default user");
        final String curUserID = preferences.getString("user_id", "default user");

        send_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text = comment_box.getText().toString();

                if (!text.isEmpty()) {
                    Comment comment = new Comment(text, curUserID);

                    post.addComment(comment);

                    post.updateToFirebase();
                    //close out this view
                    finish();
                }
                else {
                    final String ErrorMsg = "Comment cannot be empty!";
                    Toast.makeText(getApplicationContext(),ErrorMsg,Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}
