package com.example.mublog;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Declare UI components
    private ImageView icMenu, ic_search, icHome, icBookmark, icUser, icAdd,
            ic_scholar, ic_experience, ic_enterpreneurship, ic_life, ic_mentorship, ic_donation, ic_research;

    private TextView tvContent ;
        //ivBox1, ivBox2, ivBox3, ivBox4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Set the layout for this activity

        // Initialize UI components
        icMenu = findViewById(R.id.icMenu);
        ic_search = findViewById(R.id.ic_search);
        icHome = findViewById(R.id.icHome);
        icBookmark = findViewById(R.id.icBookmark);
        icUser = findViewById(R.id.icUser);
        icAdd = findViewById(R.id.icAdd);
        ic_scholar = findViewById(R.id.ic_scholar);
        ic_experience = findViewById(R.id.ic_experience);
        ic_enterpreneurship = findViewById(R.id.ic_enterpreneurship);
        ic_life = findViewById(R.id.ic_life);
        ic_research = findViewById(R.id.ic_research);
        ic_mentorship = findViewById(R.id.ic_mentorship);
        ic_donation = findViewById(R.id.ic_donation);


        // Set click listeners for header icons
        icMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( MainActivity.this, MenuActivity.class);
                startActivity(intent); }
        });

        ic_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Menu Clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent( MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        // Set click listeners for footer icons
        icHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Home Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        icBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Bookmark Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        icUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class );
                startActivity(intent);
                  }
        });

        icAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PostMainActivity.class);
                startActivity(intent);
                   }
        });

        ic_scholar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ScholarActivity.class);
                startActivity(intent);
            }
        });

        ic_experience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ExperienceActivity.class);
                startActivity(intent);
            }
        });

        ic_enterpreneurship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EnterpreneurShipActivity.class);
                startActivity(intent);
            }
        });

        ic_life.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CampusLife.class);
                startActivity(intent);
            }
        });
        ic_research.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ResearchActivity.class);
                startActivity(intent);
            }
        });
        ic_mentorship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MentorShipActivity.class);
                startActivity(intent);
            }
        });
        ic_donation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DonationActivity.class);
                startActivity(intent);
            }
        });
    }

}