package com.example.lucere;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SkinResultsActivity extends AppCompatActivity {

    // Declare the TextView field
    private TextView tvSkinCondition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skinresults);  // Load the skinresults layout

        // Initialize TextView
        tvSkinCondition = findViewById(R.id.tvSkinCondition);

        // Get data from intent
        String prediction = getIntent().getStringExtra("prediction");

        tvSkinCondition.setText("Predicted Skin Condition: " + prediction);

        // Display the prediction
//        if (prediction != null && !prediction.isEmpty()) {
//        } else {
//            tvSkinCondition.setText("No prediction received.");
//        }

        // Initialize buttons
        ImageButton profileButton = findViewById(R.id.profileButton);
        Button buttonEczema = findViewById(R.id.buttonEczema);

        // Set OnClickListener for eczema button
        buttonEczema.setOnClickListener(v -> {
            Intent intent = new Intent(SkinResultsActivity.this, DiagnosisActivity.class);
            startActivity(intent);  // Start DiagnosisActivity
        });

        // Set OnClickListener for profile button
        profileButton.setOnClickListener(v -> openProfilePage());
    }

    // Method to open ProfileActivity
    private void openProfilePage() {
        Intent intent = new Intent(SkinResultsActivity.this, ProfileActivity.class);
        startActivity(intent);
    }
}
