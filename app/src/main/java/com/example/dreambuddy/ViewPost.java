package com.example.dreambuddy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ViewPost extends AppCompatActivity {
    JournalEntry post;
    TextView postTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);

        this.post = (JournalEntry) getIntent().getSerializableExtra("journalEntry");

        postTitle = this.findViewById(R.id.Post_Title);
        postTitle.setText(post.getTitle());
    }
}
