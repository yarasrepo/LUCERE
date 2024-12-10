package com.example.lucere;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class ProductListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<ProductModel> productList = new ArrayList<>();
    private ImageButton btnNext, btnPrevious;
    private TextView textViewPageInfo;

    private int currentPage = 1;
    private static final int ITEMS_PER_PAGE = 6;
    private List<ProductModel> allProducts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        btnNext = findViewById(R.id.btnNext);
        btnPrevious = findViewById(R.id.btnPrevious);
        textViewPageInfo = findViewById(R.id.page_info);


        // Set Adapter
        productAdapter = new ProductAdapter(productList);
        recyclerView.setAdapter(productAdapter);

//        // Fetch products
//        fetchAllProducts();
        fetchProductsByIngredient("Glycerin");

        //Button listeners for pagination
        btnNext.setOnClickListener(v -> {
            if(currentPage < getTotalPages()){
                currentPage++;
                updateUI();
            }
        });
        btnPrevious.setOnClickListener(v -> {
            if(currentPage > 1){
                currentPage--;
                updateUI();
            }
        });
    }
    private void fetchAllProducts() {
        RetrofitClient.getInstance().getProductService().getAllProducts()
                .enqueue(new Callback<List<ProductModel>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<ProductModel>> call, @NonNull Response<List<ProductModel>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            /*List<ProductModel> newProductList = response.body();
                            Log.d("API Data", "Parsed products: " + newProductList.size());

                            runOnUiThread(() -> {
                                productList.clear();
                                productList.addAll(newProductList);
                                productAdapter.notifyDataSetChanged();
                            });

                            // Log the response for debugging
                            Log.d("ProductListActivity", "Products fetched: " + productList.toString());

                            // Notify the user if the data is successfully fetched
                            Toast.makeText(ProductListActivity.this, "Products loaded successfully.", Toast.LENGTH_SHORT).show();
                        } else {
                            // Handle error responses from the server
                            Log.e("ProductListActivity", "Server error: " + response.code());
                            Toast.makeText(ProductListActivity.this, "Failed to fetch data. Error: " + response.code(), Toast.LENGTH_SHORT).show();
                        }*/

                            allProducts = response.body();
                            currentPage = 1;
                            updateUI();}
                        else{
                            Log.e("ProductListActivity", "Failed to fetch products: " + response.code());
                            Toast.makeText(ProductListActivity.this, "Failed to fetch products.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<ProductModel>> call, @NonNull Throwable t) {
                        Log.e("ProductListActivity", "Network error: " + response.code());
                        Toast.makeText(ProductListActivity.this, "Unable to fetch data.", Toast.LENGTH_SHORT).show();                    }
                });
    }
    private void fetchProductsByIngredient(String ingredient) {
        RetrofitClient.getInstance().getProductService().getProductsByIngredient(ingredient)
                .enqueue(new Callback<List<ProductModel>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<ProductModel>> call, @NonNull Response<List<ProductModel>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            /*List<ProductModel> filteredProductList = response.body();
                            Log.d("API Data", "Filtered products: " + filteredProductList.size());

                            runOnUiThread(() -> {
                                productList.clear();
                                productList.addAll(filteredProductList);
                                productAdapter.notifyDataSetChanged();
                            });

                            // Log the response for debugging
                            Log.d("ProductListActivity", "Filtered products fetched: " + productList.toString());

                            // Notify the user if the data is successfully fetched
                            Toast.makeText(ProductListActivity.this, "Filtered products loaded successfully.", Toast.LENGTH_SHORT).show();
                        } else {
                            // Handle error responses from the server
                            Log.e("ProductListActivity", "Server error: " + response.code());
                            Toast.makeText(ProductListActivity.this, "Failed to fetch data. Error: " + response.code(), Toast.LENGTH_SHORT).show();
                        }*/

                            allProducts = response.body();
                            currentPage = 1;
                            updateUI();}
                        else{
                            Log.e("ProductListActivity", "Failed to fetch products: " + response.code());
                            Toast.makeText(ProductListActivity.this, "Failed to fetch filtered products.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<ProductModel>> call, @NonNull Throwable t) {
                        Log.e("ProductListActivity", "Network failure: " + t.getMessage());
                        Toast.makeText(ProductListActivity.this, "Unable to fetch data", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updateUI(){
        int startIndex = (currentPage - 1) * ITEMS_PER_PAGE;
        int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, allProducts.size());
        List<ProductModel> productsForCurrentPage = allProducts.subList(startIndex, endIndex);

        //update the adapter with the current page data
        productAdapter.updateData(productsForCurrentPage);

        //update page info
        textViewPageInfo.setText(current + "/" + getTotalPages());

        //enable/disable buttons
        btnNext.setEnabled(currentPage < getTotalPages());
        btnPrevious.setEnables(currentPage > 1);

        }
    private int getTotalPages(){
        return (int) Math.ceil((double) allProducts.size() / ITEMS_PER_PAGE);
    }

}