package com.example.lucere;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProductsActivity extends AppCompatActivity {

    private ImageView firstProductImageView, secondProductImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        firstProductImageView = findViewById(R.id.first_product_imageview);
        secondProductImageView = findViewById(R.id.second_product_imageview);




}}
