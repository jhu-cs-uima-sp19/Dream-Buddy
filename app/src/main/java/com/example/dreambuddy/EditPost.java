package com.example.dreambuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
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

import com.example.dreambuddy.R;

public class EditPost extends AppCompatActivity {

    private static final int DELETE_POST = 1;
    private static final int DELETE_EDITS = 2;


    JournalEntry post;
    EditText titleEditTextView;
    EditText bodyEditTextView;
    Switch togglePublicPrivate;
    Button saveButton;
    ImageButton deleteButton;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.exit_menu, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check that it's the PopUpChecker activity with an OK result
        if (requestCode == DELETE_EDITS) { // if we came from delete edit button
            if (resultCode == RESULT_OK) {

                // get boolean data from Intent
                if (data.getBooleanExtra("toDelete", false)) {
                    finish();
                }
            }
        } else if (requestCode == DELETE_POST) { // if we came from delete post button
            if (resultCode == RESULT_OK) {

                if (data.getBooleanExtra("toDelete", false)) {
                    post.deleteFromFirebase();
                    finish();
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_exit) {

            Intent intent = new Intent(this, PopUpChecker1.class);
            startActivityForResult(intent, DELETE_EDITS);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);
        TextView title = findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));
        title.setText(R.string.title_edit_post);

        this.post = (JournalEntry) getIntent().getSerializableExtra("journalEntry");

        titleEditTextView = this.findViewById(R.id.editPostTitle);
        titleEditTextView.setText(post.getTitle());

        bodyEditTextView = this.findViewById(R.id.mainText);
        bodyEditTextView.setText(post.getBody());

        bodyEditTextView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        bodyEditTextView.setRawInputType(InputType.TYPE_CLASS_TEXT);

        togglePublicPrivate = this.findViewById(R.id.toggle);
        togglePublicPrivate.setChecked(post.getIsPrivate());

        deleteButton = this.findViewById(R.id.deleteButton);
        final EditPost this_ = this;
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(this_, PopUpChecker1.class);
                startActivityForResult(intent, DELETE_POST);
            }
        });

        saveButton = this.findViewById(R.id.saveEditPostButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newTitle = titleEditTextView.getText().toString();
                String newBody = bodyEditTextView.getText().toString();

                if (!newTitle.isEmpty() && !newBody.isEmpty()) {
                    //edit post object
                    post.setTitle(newTitle);
                    post.setBody(newBody);
                    post.setIsPrivate(togglePublicPrivate.isChecked());

                    //update firebase
                    post.updateToFirebase();

                    //close out this view
                    finish();
                }
                else {
                    final String ErrorMsg = "Post title and body are required please!";
                    Toast.makeText(getApplicationContext(),ErrorMsg,Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
