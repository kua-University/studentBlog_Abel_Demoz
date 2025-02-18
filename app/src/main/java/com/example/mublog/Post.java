package com.example.mublog;

import java.io.Serializable;

public class Post implements Serializable {
    private String id; // Add ID field
    private String topic;
    private String title;
    private String content;
    private String date;
    private String author;
    private int favorites;
    private boolean favorited; // Track if the post is favorited

    // Default constructor required for Firestore
    public Post() {}

    public Post(String topic, String headline, String article, String date, String author, int favorites, boolean favorited) {
        this.topic = topic;
        this.title = title;
        this.content = content;
        this.date = date;
        this.author = author;
        this.favorites = favorites;
        this.favorited = favorited;
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTopic() { return topic; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getDate() { return date; }
    public String getAuthor() { return author; }
    public int getFavorites() { return favorites; }
    public void setFavorites(int favorites) { this.favorites = favorites; }
    public boolean isFavorited() { return favorited; }
    public void setFavorited(boolean favorited) { this.favorited = favorited; }
}
