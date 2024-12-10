package com.example.lucere;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<ProductModel> productList;

    // Constructor to initialize the product list
    public ProductAdapter(List<ProductModel> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each item in the RecyclerView
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_product_card, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        // Get the current product
        ProductModel product = productList.get(position);
        Log.d("ProductAdapter", "Binding Product: " + product.toString());

        // Bind data to the views
        holder.productName.setText(product.getProductName());
        holder.productType.setText(product.getProductType());
        holder.productUrl.setText(product.getProductUrl());
    }

    @Override
    public int getItemCount() {
        // Return the size of the product list
        return productList.size();
    }

    // ViewHolder class to hold references to the views in each item
    public static class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView productName, productType, productUrl;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productType = itemView.findViewById(R.id.productType);
            productUrl = itemView.findViewById(R.id.productUrl);
        }
    }
}