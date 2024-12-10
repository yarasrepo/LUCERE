package com.example.lucere;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    private RecyclerView productRecyclerView;
    private DatabaseHelper dbHelper;
    private TextView textViewIngredient, textViewPageInfo;
    private ImageButton btnNext, btnPrevious;
    private List<Product> allProducts;
    private int currentPage = 1;
    private static final int ITEMS_PER_PAGE = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        productRecyclerView = findViewById(R.id.product_recycler_view);
        productRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        dbHelper = new DatabaseHelper(this);


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
        }
    }

    private void loadAllProducts() {
        // Load all products if no ingredient filter is applied
        allProducts = dbHelper.getAllProducts();
        if(allProducts.isEmpty()){
            Toast.makeText(this, "No products found", Toast.LENGTH_SHORT).show();
        }
    }


}
