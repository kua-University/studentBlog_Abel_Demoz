package com.example.mublog;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class EnterpreneurShipActivity extends AppCompatActivity implements PostAdapter.OnItemClickListener{
    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private List<Post> postList;

    // Firestore instance
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enterpreneurship); // Set the layout for this activity

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        postList = new ArrayList<>();
        postAdapter = new PostAdapter(postList, this);
        recyclerView.setAdapter(postAdapter);

        // Fetch posts for the "Experience" category
        fetchPosts();
    }
    private void fetchPosts() {
        db.collection("posts")
                .whereEqualTo("category", "Enterpreneurship") // Fetch posts for the "Experience" category
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    postList.clear();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        // Convert Firestore document to a Post object
                        Post post = document.toObject(Post.class);
                        post.setId(document.getId()); // Set the document ID
                        postList.add(post);
                    }
                    postAdapter.notifyDataSetChanged(); // Update RecyclerView
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error fetching posts: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
    @Override
    public void onItemClick(Post post) {
        // Navigate to FullContentActivity to display the full post
        Intent intent = new Intent(this, FullContentAcitvity.class);
        intent.putExtra("post", post); // Pass the post object to the next activity
        startActivity(intent);
    }
}
