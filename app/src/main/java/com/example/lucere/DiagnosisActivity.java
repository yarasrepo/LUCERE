package com.example.lucere;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class DiagnosisActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageButton profileButton = findViewById(R.id.userButton);
        profileButton.setOnClickListener(v -> {
            // Navigate to the ProfileActivity
            Intent intent = new Intent(DiagnosisActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        // Find the ingredient button and set its click listener
        TextView ingredientButton = findViewById(R.id.ingredient_btn);
        ingredientButton.setOnClickListener(v -> {
            // Navigate to the ProductsActivity
            Intent intent = new Intent(DiagnosisActivity.this, ProductsActivity.class);
            startActivity(intent);
        });
    }
}
