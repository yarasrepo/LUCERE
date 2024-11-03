package com.example.lucere;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class SkinResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skinresults);  // Load the skinresults layout
    }

    // You can remove this method for now if it's not needed
    private void openProfilePage() {
        Intent intent = new Intent(this, SetUpProfileActivity.class);
        startActivity(intent);
    }

}
