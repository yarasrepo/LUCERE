package com.example.lucere;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import okhttp3.MultipartBody;

public interface MLService {
    @Multipart
    @POST("/predict")
    Call<ResponseBody> uploadImage(@Part MultipartBody.Part file);
}
