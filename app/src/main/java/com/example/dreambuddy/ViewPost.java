package com.example.dreambuddy;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;

public class ViewPost extends AppCompatActivity {
    JournalEntry post;
    TextView postTitle;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.exit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_exit) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);

        this.post = (JournalEntry) getIntent().getSerializableExtra("journalEntry");

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);
        TextView title = findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));
        title.setText(R.string.title_view_post);

        TextView post_title = findViewById(R.id.Post_Title);
        TextView post_user = findViewById(R.id.Post_Creator);
        TextView comment_count = findViewById(R.id.commentCount);
        TextView like_count = findViewById(R.id.likesCount);
        TextView body = findViewById(R.id.Post_Text);
        body.setMovementMethod(new ScrollingMovementMethod());


        post_user.setText(this.post.getUsername());
        post_title.setText(this.post.getTitle());
        String like_num = "" + this.post.getLikes();
        like_count.setText(like_num);
        body.setText(this.post.getBody());
    }
}