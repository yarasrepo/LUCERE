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
        textViewIngredient = findViewById(R.id.ingredient_title);
        textViewPageInfo = findViewById(R.id.page_info);
        btnNext = findViewById(R.id.btnNext);
        btnPrevious = findViewById(R.id.btnPrevious);

        String ingredient = getIntent().getStringExtra("ingredient_name");
//        String ingredient = "salicylic acid";

        if (ingredient != null) {
            loadProductsByIngredient(ingredient);
            textViewIngredient.setText("containing: " + ingredient);
        } else {
            loadAllProducts();
        }

        btnNext.setOnClickListener(v -> {
            if (currentPage < getTotalPages()){
                currentPage++;
                updateUI();
            }
        });

        btnPrevious.setOnClickListener(v -> {
            if (currentPage > 1){
                currentPage--;
                updateUI();
            }
        });
    }

    private void loadProductsByIngredient(String ingredient) {
        List<Product> products = dbHelper.getProductsByIngredient(ingredient);

        if (products.isEmpty()) {
            Toast.makeText(this, "No products found for " + ingredient, Toast.LENGTH_SHORT).show();
        }
        updateUI();
    }

    private void loadAllProducts() {
        // Load all products if no ingredient filter is applied
        allProducts = dbHelper.getAllProducts();
        if(allProducts.isEmpty()){
            Toast.makeText(this, "No products found", Toast.LENGTH_SHORT).show();
        }
        updateUI();
    }

    private void updateUI(){
        int startIndex = (currentPage - 1) * ITEMS_PER_PAGE;
        int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, allProducts.size());
        List<Product> productsForCurrentPage = allProducts.subList(startIndex, endIndex);

        ProductAdapter adapter = new ProductAdapter(this, productsForCurrentPage);
        productRecyclerView.setAdapter(adapter);

        //update page info
        textViewPageInfo.setText(currentPage + "/" + getTotalPages() + " pages");

        //Disable/Enable buttons
        btnNext.setEnabled(currentPage > getTotalPages());
        btnPrevious.setEnabled(currentPage > 1);

    }

    private int getTotalPages(){
        return (int) Math.ceil((double) allProducts.size() / ITEMS_PER_PAGE);
    }

}
