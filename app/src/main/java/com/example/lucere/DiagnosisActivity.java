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
            Intent intent = new Intent(DiagnosisActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        TextView ingredientButton = findViewById(R.id.ingredient_btn);
        ingredientButton.setOnClickListener(v -> {
            String ingredient = ingredientButton.getText().toString();
            Intent intent = new Intent(DiagnosisActivity.this, ProductListActivity.class);
            intent.putExtra("ingredient_name", ingredient);
            startActivity(intent);
        });
    }
}
