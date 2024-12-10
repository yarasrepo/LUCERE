package com.example.lucere;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class HomeScreenActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private Bitmap capturedImageBitmap;
    private ImageView cameraIcon;
    private ImageView profileIcon;

    // ActivityResultLauncher for the camera
    private final ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        Bundle extras = data.getExtras();
                        capturedImageBitmap = (Bitmap) extras.get("data");
                        if (capturedImageBitmap != null) {
                            // Send the image directly to the ML server without showing it in an ImageView
                            sendImageToMLServer(capturedImageBitmap);
                        } else {
                            Log.e("HomeScreenActivity", "Captured image is null");
                        }
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);

        cameraIcon = findViewById(R.id.image_camera);
        profileIcon = findViewById(R.id.profileIcon);

        // Request permissions at runtime if necessary
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, 101);
        }

        // Set the OnClickListener for the camera icon
        cameraIcon.setOnClickListener(v -> openCamera());
        profileIcon.setOnClickListener(v -> openProfilePage());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with camera usage
                openCamera();
            } else {
                Toast.makeText(this, "Camera permission is required", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            cameraLauncher.launch(takePictureIntent);
        }
    }

    private void openProfilePage() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    private void sendImageToMLServer(Bitmap bitmap) {
        if (bitmap == null) return;

        // Convert the bitmap to byte array
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        // Prepare the request body for the image
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), byteArray);
        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("file", "captured_image.png", requestBody);

        // Send the image to the ML server
        MLRetrofitClient.getInstance().getMLService().uploadImage(imagePart)
                .enqueue(new Callback<PredictionResponse>() {
                    @Override
                    public void onResponse(Call<PredictionResponse> call, Response<PredictionResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            String prediction = response.body().getPrediction();
                            Log.d("Prediction", "Skin type prediction: " + prediction);

                            // Pass the prediction result to the next activity
                            Intent intent = new Intent(HomeScreenActivity.this, SkinResultsActivity.class);
                            intent.putExtra("prediction_result", prediction);
                            startActivity(intent);
                        } else {
                            Toast.makeText(HomeScreenActivity.this, "Failed to get prediction", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<PredictionResponse> call, Throwable t) {
                        Log.e("ImageUpload", "Failure: " + t.getMessage());
                        Toast.makeText(HomeScreenActivity.this, "Error uploading image", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}