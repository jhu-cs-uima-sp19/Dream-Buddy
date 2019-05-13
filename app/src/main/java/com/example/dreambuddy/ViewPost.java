package com.example.dreambuddy;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Locale;

public class ViewPost extends AppCompatActivity {
    JournalEntry post;
    ImageView filled_heart;
    ImageView empty_heart;
    TextView like_count;
    boolean post_liked;
    List<Comment> comments;
    String uid;

    CommentAdapter adapter;

    final private int ADDING_COMMENT = 1;

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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == ADDING_COMMENT && resultCode == RESULT_OK) {
            if (data.getBooleanExtra("add_success", true)) {
                String body = data.getStringExtra("body");
                if (body != null && !body.isEmpty()) {
                    comments.add(new Comment(body, uid));
                    adapter.notifyDataSetChanged();
                }
            }
        }
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
        final TextView post_user = findViewById(R.id.Post_Creator);
        final TextView comment_count = findViewById(R.id.commentCount);
        like_count = findViewById(R.id.likesCount);
        TextView body = findViewById(R.id.Post_Text);
        body.setMovementMethod(new ScrollingMovementMethod());


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("users").child(post.getAuthor_id());
        userRef.child("username").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue(String.class);
                post_user.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference postRef = database.getReference("posts").child(post.getId());
        postRef.child("numComments").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Long numComments = dataSnapshot.getValue(Long.class);
                comment_count.setText(String.valueOf(numComments));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        postRef.child("likes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Long numLikes = dataSnapshot.getValue(Long.class);
                like_count.setText(String.valueOf(numLikes));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        date.setText(this.post.getDate());
        post_title.setText(this.post.getTitle());

        body.setText(this.post.getBody());

        Button like_area = findViewById(R.id.like_area);

        final ViewPost context = this;
        SharedPreferences preferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        final String curUser_id = preferences.getString("user_id", "default user");
        uid = curUser_id;

        post_liked = post.liked_by_user(curUser_id);

        filled_heart = findViewById(R.id.filled_heart);
        empty_heart = findViewById(R.id.empty_heart);

        if (post_liked) {
            filled_heart.setVisibility(View.VISIBLE);
            empty_heart.setVisibility(View.INVISIBLE);
        } else {
            empty_heart.setVisibility(View.VISIBLE);
            filled_heart.setVisibility(View.INVISIBLE);
        }

        like_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (post_liked) {
                    unlike(curUser_id);
                } else {
                    like(curUser_id);
                }
            }
        });

        TextView comment_box = findViewById(R.id.commentBox);
        comment_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MakeComment.class);
                intent.putExtra("post", post);
                context.startActivityForResult(intent, ADDING_COMMENT);
            }
        });

//        comment_count.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, MakeComment.class);
//                intent.putExtra("post", post);
//                context.startActivity(intent);
//            }
//        });

        comments = post.getComments();
        setData(comments);

        RecyclerView rvComments = (RecyclerView) findViewById(R.id.rvComments);
        rvComments.setAdapter(adapter);
        rvComments.setLayoutManager(new LinearLayoutManager(this));

        postRef.child("comments").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setData(List<Comment> data){
        adapter = new CommentAdapter(data);
        adapter.notifyDataSetChanged();
    }


    private void like(final String curUser_id) {
        post.addUserLike(curUser_id);
        post.setLikes(post.getLikes() + 1);
        post.updateToFirebase();
        post_liked = true;

        empty_heart.setVisibility(View.INVISIBLE);
        filled_heart.setVisibility(View.VISIBLE);
    }

    private void unlike(final String curUser_id) {
        post.removeUserLike(curUser_id);
        post.setLikes(post.getLikes() - 1);
        post.updateToFirebase();
        post_liked = false;

        empty_heart.setVisibility(View.VISIBLE);
        filled_heart.setVisibility(View.INVISIBLE);
    }

}