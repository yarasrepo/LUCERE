package com.example.lucere;

import com.example.lucere.ProductService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class RetrofitClient {
    private static RetrofitClient instance = null;
    private final ProductService productService;

    private RetrofitClient() {
        OkHttpClient unsafeClient = getUnsafeOkHttpClient();

        retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl("https://lucere-backend.vercel.app/") // Ensure you are using https
                .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
                .client(unsafeClient) // Use the unsafe OkHttpClient here
                .build();

        productService = retrofit.create(ProductService.class);
    }

    public static synchronized RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    public ProductService getProductService() {
        return productService;
    }

    private OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}

                        @Override
                        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}

                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            // Build OkHttpClient with unsafe SSL settings
            return new OkHttpClient.Builder()
                    .sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0])
                    .hostnameVerifier((hostname, session) -> true) // Trust all hostnames
                    .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)) // Logging
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}