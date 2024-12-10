package com.example.lucere;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class SkinResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skinresults);  // Load the skinresults layout

        String predictionResult = getIntent().getStringExtra("prediction_result");

        // Find the profile button and eczema button by their IDs
        ImageButton profileButton = findViewById(R.id.profileButton);
        Button buttonEczema = findViewById(R.id.buttonEczema);

        // Set an OnClickListener to open DiagnosisActivity
        buttonEczema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SkinResultsActivity.this, DiagnosisActivity.class);
                startActivity(intent);  // Start DiagnosisActivity
            }
        });

        // Set an OnClickListener to open ProfileActivity
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProfilePage();
            }
        });
    }

    private void openProfilePage() {
        Intent intent = new Intent(SkinResultsActivity.this, ProfileActivity.class);
        startActivity(intent);
    }
}
