package com.example.mublog;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class PostMainActivity extends AppCompatActivity {

    // Declare UI components
    private TextView tvChooseCategory;
    private EditText etArticleTitle, etArticleContent;
    private Button btnSubmit;

    // Variable to store the selected category
    private String selectedCategory = "None";

    // Firestore instance
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postmain); // Set the layout for this activity

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize UI components
        tvChooseCategory = findViewById(R.id.tvChooseCategory);
        etArticleTitle = findViewById(R.id.etArticleTitle);
        etArticleContent = findViewById(R.id.etArticleContent);
        btnSubmit = findViewById(R.id.btnSubmit);

        // Set click listener for the "Select Category" TextView
        tvChooseCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCategoryDialog(); // Open the category selection dialog
            }
        });

        // Set click listener for the Submit button
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get article title and content
                String articleTitle = etArticleTitle.getText().toString().trim();
                String articleContent = etArticleContent.getText().toString().trim();

                // Validate inputs
                if (selectedCategory.equals("None")) {
                    Toast.makeText(PostMainActivity.this, "Please select a category", Toast.LENGTH_SHORT).show();
                } else if (articleTitle.isEmpty()) {
                    Toast.makeText(PostMainActivity.this, "Please enter an article title", Toast.LENGTH_SHORT).show();
                } else if (articleContent.isEmpty()) {
                    Toast.makeText(PostMainActivity.this, "Please enter article content", Toast.LENGTH_SHORT).show();
                } else {
                    // Save the post to Firestore
                    savePostToFirestore(selectedCategory, articleTitle, articleContent);
                }
            }
        });
    }

    // Method to save the post to Firestore
    private void savePostToFirestore(String category, String title, String content) {
        // Get the current user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        // Fetch the user's name from Firestore
        db.collection("users").document(user.getUid())
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String authorName = documentSnapshot.getString("name");

                        // Create a new post object
                        Map<String, Object> post = new HashMap<>();
                        post.put("category", category);
                        post.put("title", title);
                        post.put("content", content);
                        post.put("date", new java.util.Date().toString()); // Add current date
                        post.put("favorites", 0); // Initialize favorites to 0
                        post.put("favorited", false); // Initialize favorited to false
                        post.put("author", authorName); // Add the author name

                        // Add the post to Firestore
                        db.collection("posts")
                                .add(post)
                                .addOnSuccessListener(documentReference -> {
                                    Toast.makeText(PostMainActivity.this, "Post published!", Toast.LENGTH_SHORT).show();
                                    // Clear input fields
                                    etArticleTitle.setText("");
                                    etArticleContent.setText("");
                                    tvChooseCategory.setText("Select Category");
                                    selectedCategory = "None";
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(PostMainActivity.this, "Error publishing post: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                    } else {
                        Toast.makeText(this, "User profile not found", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error fetching user profile: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    // Method to show the category selection dialog
    private void showCategoryDialog() {
        // List of categories
        final String[] categories = {
                "Scholarship",
                "Academic / Research",
                "Campus Life",
                "Mentorship",
                "Experience",
                "Enterpreneurship",
                "Donation"
        };

        // Create a dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose a Category");
        builder.setItems(categories, (dialog, which) -> {
            // Update the selected category
            selectedCategory = categories[which];

            // Update the "Select Category" TextView to show the selected category
            tvChooseCategory.setText("Selected: " + selectedCategory);
        });
        builder.show();
    }
}