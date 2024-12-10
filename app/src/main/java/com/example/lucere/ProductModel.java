package com.example.lucere;

import java.util.List;

public class Product {
    private String product_name; // The name of the product
    private String product_url;  // URL for the product
    private String product_type; // The type/category of the product
    private List<String> clean_ingreds; // List of clean ingredients

    // Getters and Setters
    public String getProductName() {
        return product_name;
    }

    public void setProductName(String product_name) {
        this.product_name = product_name;
    }

    public String getProductUrl() {
        return product_url;
    }

    public void setProductUrl(String product_url) {
        this.product_url = product_url;
    }

    public String getProductType() {
        return product_type;
    }

    public void setProductType(String product_type) {
        this.product_type = product_type;
    }

    public List<String> getCleanIngreds() {
        return clean_ingreds;
    }

    public void setCleanIngreds(List<String> clean_ingreds) {
        this.clean_ingreds = clean_ingreds;
    }

    // Optional: Add toString() for easier debugging
    @Override
    public String toString() {
        return "Product{" +
                "product_name='" + product_name + '\'' +
                ", product_url='" + product_url + '\'' +
                ", product_type='" + product_type + '\'' +
                ", clean_ingreds=" + clean_ingreds +
                '}';
    }
}
