package com.example.lucere;

import android.os.Bundle;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    private RecyclerView productRecyclerView;
    private DatabaseHelper dbHelper;
    private TextView textViewIngredient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        productRecyclerView = findViewById(R.id.product_recycler_view);
        productRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        dbHelper = new DatabaseHelper(this);
        textViewIngredient = findViewById(R.id.ingredient_title);

        String ingredient = getIntent().getStringExtra("ingredient_name");
//        String ingredient = "salicylic acid";

        if (ingredient != null) {
            loadProductsByIngredient(ingredient);
            textViewIngredient.setText("containing: " + ingredient);
        } else {
            loadAllProducts();
        }
    }

    private void loadProductsByIngredient(String ingredient) {
        List<Product> products = dbHelper.getProductsByIngredient(ingredient);

        if (products.isEmpty()) {
            Toast.makeText(this, "No products found for " + ingredient, Toast.LENGTH_SHORT).show();
        } else {
            ProductAdapter adapter = new ProductAdapter(this, products);
            productRecyclerView.setAdapter(adapter);
            Toast.makeText(this, " found for " + ingredient, Toast.LENGTH_SHORT).show();

        }
    }

    private void loadAllProducts() {
        // Load all products if no ingredient filter is applied
        List<Product> products = dbHelper.getAllProducts();
        ProductAdapter adapter = new ProductAdapter(this, products);
        productRecyclerView.setAdapter(adapter);
    }
}
