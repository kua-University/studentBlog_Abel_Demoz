package com.example.mublog;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;


public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu); // Set the layout for this activity

        // Initialize buttons
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);

        Button logoutButton = findViewById(R.id.logoutButton);

        // Set click listeners for buttons
        button1.setOnClickListener(new View.OnClickListener() {
            // Handle button 1 click
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            // Handle button 1 click   graduates
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, GraduateActivity.class);
                startActivity(intent);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            // Handle button 1 click
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, TermsActivity.class);
                startActivity(intent);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            // Handle button 1 click
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
        logoutButton.setOnClickListener(new View.OnClickListener() {
            // Handle button 1 click
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, LogInActivity.class);
                startActivity(intent);
            }
        });


    }
}
