package com.example.mublog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<Post> postList; // List of posts
    private FirebaseFirestore db; // Firestore instance
    private OnItemClickListener listener; // Click listener for arrowImageView

    // Constructor to initialize the adapter with a list of posts and a listener
    public PostAdapter(List<Post> postList, OnItemClickListener listener) {
        this.postList = postList;
        this.db = FirebaseFirestore.getInstance(); // Initialize Firestore
        this.listener = listener; // Initialize the listener
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item_post layout for each item in the RecyclerView
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = postList.get(position);

        // Set article title and content
        holder.articleTextView.setText(post.getContent()); // Display the content
        holder.headlineTextView.setText(post.getTitle()); // Display the title

        // Set author and date
        holder.authorTextView.setText("By " + post.getAuthor()); // Display the author name
        holder.dateTextView.setText(post.getDate()); // Display the date

        // Set favorite count
        holder.favoritesTextView.setText(String.valueOf(post.getFavorites()));

        // Set favorite icon based on whether the post is favorited
        if (post.isFavorited()) {
            holder.favoriteImageView.setImageResource(R.drawable.ic_favorite_filled); // Filled icon
        } else {
            holder.favoriteImageView.setImageResource(R.drawable.ic_favorite_border); // Outline icon
        }

        // Handle favorite icon click
        // Handle favorite icon click
        holder.favoriteImageView.setOnClickListener(v -> {
            // Prevent multiple clicks
            holder.favoriteImageView.setClickable(false);

            int currentFavorites = post.getFavorites();
            boolean isFavorited = post.isFavorited();

            // Toggle favorite state
            if (isFavorited) {
                post.setFavorites(currentFavorites - 1); // Decrement favorites
            } else {
                post.setFavorites(currentFavorites + 1); // Increment favorites
            }
            post.setFavorited(!isFavorited); // Toggle favorite state

            // Update UI
            holder.favoritesTextView.setText(String.valueOf(post.getFavorites()));
            if (post.isFavorited()) {
                holder.favoriteImageView.setImageResource(R.drawable.ic_favorite_filled);
            } else {
                holder.favoriteImageView.setImageResource(R.drawable.ic_favorite_border);
            }

            // Update favorites in Firestore
            db.collection("posts")
                    .document(post.getId())
                    .update("favorites", post.getFavorites(), "favorited", post.isFavorited())
                    .addOnSuccessListener(aVoid -> {
                        // Successfully updated
                        holder.favoriteImageView.setClickable(true); // Re-enable click
                    })
                    .addOnFailureListener(e -> {
                        // Handle error
                        holder.favoriteImageView.setClickable(true); // Re-enable click
                    });
        });

        // Handle arrow click to view the full post
        holder.arrowImageView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(post); // Pass the post object to the listener
            }
        });
    }

    @Override
    public int getItemCount() {
        return postList.size(); // Return the number of posts in the list
    }

    // ViewHolder class to hold the views for each item
    public static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView headlineTextView, articleTextView, authorTextView, dateTextView, favoritesTextView;
        ImageView favoriteImageView, arrowImageView;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            headlineTextView = itemView.findViewById(R.id.headlineTextView); // Title
            articleTextView = itemView.findViewById(R.id.articleTextView); // Content
            authorTextView = itemView.findViewById(R.id.authorTextView); // Author
            dateTextView = itemView.findViewById(R.id.dateTextView); // Date
            favoritesTextView = itemView.findViewById(R.id.favoritesTextView); // Favorites count
            favoriteImageView = itemView.findViewById(R.id.favoriteImageView); // Favorite icon
            arrowImageView = itemView.findViewById(R.id.arrowImageView); // Arrow icon
        }
    }

    // Click listener interface for arrowImageView
    public interface OnItemClickListener {
        void onItemClick(Post post); // Pass the post object to the listener
    }
}