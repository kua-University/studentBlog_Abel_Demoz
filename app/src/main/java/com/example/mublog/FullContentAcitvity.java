package com.example.mublog;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FullContentAcitvity extends AppCompatActivity {
    private TextView fullArticleTextView, authorTextView, dateTextView, headlineTextView;
    private Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content); // Set the layout for this activity

        // Initialize views
        fullArticleTextView = findViewById(R.id.fullArticleTextView);
        authorTextView = findViewById(R.id.authorTextView);
        dateTextView = findViewById(R.id.dateTextView);
        headlineTextView = findViewById(R.id.headlineTextView);

        // Retrieve the post object from the intent
        post = (Post) getIntent().getSerializableExtra("post");

        // Display the full post content
        if (post != null) {
            headlineTextView.setText(post.getTitle());
            fullArticleTextView.setText(post.getContent());
            authorTextView.setText("By " + post.getAuthor());
            dateTextView.setText(post.getDate());
        }
    }
}
