package com.example.mublog;



import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements PostAdapter.OnItemClickListener {

    private EditText searchEditText;
    private RecyclerView searchRecyclerView;
    private PostAdapter postAdapter;  // Assuming PostAdapter is used for RecyclerView
    private List<Post> postList;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search); // Assuming your XML file is named activity_main.xml

        db = FirebaseFirestore.getInstance();

        // Initialize UI elements
        searchEditText = findViewById(R.id.searchEditText);
        searchRecyclerView = findViewById(R.id.searchRecyclerView);

        // Set up RecyclerView
        postList = new ArrayList<>();
        postAdapter = new PostAdapter(postList,this);
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchRecyclerView.setAdapter(postAdapter);

        // Add text change listener to the search EditText
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Only search if the text is not empty
                if (charSequence.length() > 0) {
                    searchPosts(charSequence.toString());
                } else {
                    // If search text is empty, clear the results
                    postList.clear();
                    postAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    // Search posts from Firestore
    private void searchPosts(String query) {
        // Query Firestore for posts where the headline contains the search query
        db.collection("posts")
                .whereGreaterThanOrEqualTo("title", query)  // Searches for headlines starting with the query
                .whereLessThanOrEqualTo("title", query + '\uf8ff')  // Wildcard query for Firebase
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null && !querySnapshot.isEmpty()) {
                            // Clear the current list and add new results
                            postList.clear();
                            for (var document : querySnapshot) {
                                Post post = document.toObject(Post.class);
                                postList.add(post);
                            }
                            postAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(SearchActivity.this, "No posts found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SearchActivity.this, "Error fetching posts: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    public void onItemClick(Post post) {

    }
}
