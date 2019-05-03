package com.example.dreambuddy;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

public class ViewPost extends AppCompatActivity {
    JournalEntry post;
    ImageView filled_heart;
    ImageView empty_heart;
    TextView like_count;

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
        TextView date = findViewById(R.id.Date);
        TextView post_user = findViewById(R.id.Post_Creator);
        TextView comment_count = findViewById(R.id.commentCount);
        like_count = findViewById(R.id.likesCount);
        TextView body = findViewById(R.id.Post_Text);
        body.setMovementMethod(new ScrollingMovementMethod());


        post_user.setText(this.post.getUsername());
        date.setText(this.post.getDate());
        post_title.setText(this.post.getTitle());
        String like_num = "" + this.post.getLikes();
        like_count.setText(like_num);
        body.setText(this.post.getBody());


        Button like_area = findViewById(R.id.like_area);

        Context context = this;
        SharedPreferences preferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        final String curUser_id = preferences.getString("user_id", "default user");

        boolean post_liked = post.liked_by_user(curUser_id);

        filled_heart = findViewById(R.id.filled_heart);
        empty_heart = findViewById(R.id.empty_heart);

        if (post_liked) {
            filled_heart.setVisibility(View.VISIBLE);
            empty_heart.setVisibility(View.INVISIBLE);

            unlike(like_area, curUser_id);
        } else {
            empty_heart.setVisibility(View.VISIBLE);
            filled_heart.setVisibility(View.INVISIBLE);

            like(like_area, curUser_id);
        }

    }

    private void like(final Button like_area, final String curUser_id) {
        like_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post.addUserLike(curUser_id);
                post.setLikes(post.getLikes() + 1);
                post.updateToFirebase();

                empty_heart.setVisibility(View.INVISIBLE);
                filled_heart.setVisibility(View.VISIBLE);
                like_count.setText(String.format(Locale.getDefault(), "%d", post.getLikes()));
                unlike(like_area, curUser_id);
            }
        });
    }

    private void unlike(final Button like_area, final String curUser_id) {
        like_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post.removeUserLike(curUser_id);
                post.setLikes(post.getLikes() - 1);
                post.updateToFirebase();

                empty_heart.setVisibility(View.VISIBLE);
                filled_heart.setVisibility(View.INVISIBLE);
                like_count.setText(String.format(Locale.getDefault(), "%d", post.getLikes()));
                like(like_area, curUser_id);
            }
        });
    }
}