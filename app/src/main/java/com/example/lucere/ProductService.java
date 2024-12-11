package com.example.lucere;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

public interface ProductService {
    @GET("/")
    Call<Object> readRoot();

    @GET("/products")
    Call<List<ProductModel>> getAllProducts();

    @GET("/products/by-ingredient/{ingredient}")
    Call<List<ProductModel>> getProductsByIngredient(@Path("ingredient") String ingredient);

    @GET("/products/by-type/{product_type}")
    Call<List<ProductModel>> getProductsByType(@Path("product_type") String productType);
}
