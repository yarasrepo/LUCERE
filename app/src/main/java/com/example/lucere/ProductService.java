package com.example.lucere;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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
