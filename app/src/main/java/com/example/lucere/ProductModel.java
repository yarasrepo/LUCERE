package com.example.lucere;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductModel {
    @SerializedName("product_name")
    private String product_name;
    @SerializedName("product_url")// The name of the product
    private String product_url;
    @SerializedName("product_type")// URL for the product
    private String product_type; // The type/category of the product
    private String clean_ingreds; // List of clean ingredients

    // Getters and Setters
    public String getProductName() {
        return product_name;
    }


    public String getProductUrl() {
        return product_url;
    }


    public String getProductType() {
        return product_type;
    }


}
