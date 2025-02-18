package com.example.mublog;

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
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    // Declare UI components
    private TextView tvName, tvEmail;
    private EditText etYearCompleted, etOccupation, etBio;
    private Button btnSaveProfile, btnEditProfile;

    // Firebase instances
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile); // Set the layout for this activity

        // Initialize Firebase Auth and Firestore
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Initialize UI components
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        etYearCompleted = findViewById(R.id.etYearCompleted);
        etOccupation = findViewById(R.id.etOccupation);
        etBio = findViewById(R.id.etBio);
        btnSaveProfile = findViewById(R.id.btnSaveProfile);
        btnEditProfile = findViewById(R.id.btnEditProfile);

        // Fetch and display user data (name and email)
        fetchUserData();

        // Load saved profile data (year completed, occupation, bio)
        loadProfileData();

        // Set click listener for the Save Profile button
        btnSaveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfileData();
            }
        });

        // Set click listener for the Edit Profile button
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableEditing();
            }
        });
    }

    // Method to fetch user data (name and email) from Firestore
    private void fetchUserData() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            db.collection("users").document(userId)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                // Fetch name and email
                                String name = document.getString("name");
                                String email = document.getString("email");

                                // Display name and email
                                tvName.setText("Name: " + name);
                                tvEmail.setText("Email: " + email);
                            }
                        } else {
                            Toast.makeText(ProfileActivity.this, "Failed to fetch user data", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    // Method to load saved profile data (year completed, occupation, bio)
    private void loadProfileData() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            db.collection("users").document(userId)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                // Fetch additional profile data
                                String yearCompleted = document.getString("yearCompleted");
                                String occupation = document.getString("occupation");
                                String bio = document.getString("bio");

                                // Populate the fields
                                etYearCompleted.setText(yearCompleted);
                                etOccupation.setText(occupation);
                                etBio.setText(bio);
                            }
                        } else {
                            Toast.makeText(ProfileActivity.this, "Failed to load profile data", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    // Method to save profile data (year completed, occupation, bio) to Firestore
    private void saveProfileData() {
        // Get user input
        String yearCompleted = etYearCompleted.getText().toString().trim();
        String occupation = etOccupation.getText().toString().trim();
        String bio = etBio.getText().toString().trim();

        // Validate inputs
        if (yearCompleted.isEmpty() || occupation.isEmpty() || bio.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        } else {
            FirebaseUser user = mAuth.getCurrentUser();
            if (user != null) {
                String userId = user.getUid();

                // Create a map to store the additional profile data
                Map<String, Object> profileData = new HashMap<>();
                profileData.put("yearCompleted", yearCompleted);
                profileData.put("occupation", occupation);
                profileData.put("bio", bio);

                // Save the data to Firestore
                db.collection("users").document(userId)
                        .update(profileData)
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(ProfileActivity.this, "Profile saved successfully!", Toast.LENGTH_SHORT).show();
                            disableEditing(); // Disable editing after saving
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(ProfileActivity.this, "Failed to save profile data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            }
        }
    }

    // Method to enable editing
    private void enableEditing() {
        etYearCompleted.setEnabled(true);
        etOccupation.setEnabled(true);
        etBio.setEnabled(true);
        btnSaveProfile.setVisibility(View.VISIBLE);
    }

    // Method to disable editing
    private void disableEditing() {
        etYearCompleted.setEnabled(false);
        etOccupation.setEnabled(false);
        etBio.setEnabled(false);
        btnSaveProfile.setVisibility(View.GONE);
    }
}