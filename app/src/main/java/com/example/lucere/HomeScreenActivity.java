package com.example.lucere;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.util.Log;
import android.Manifest;

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
    private ImageView imageView;
    private ImageView cameraIcon;
    private Bitmap capturedImageBitmap;
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
                            imageView.setImageBitmap(capturedImageBitmap);
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
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 101);
        }

        // Set the OnClickListener for the camera icon
        cameraIcon.setOnClickListener(v -> openCamera());
        profileIcon.setOnClickListener(v -> openProfilePage());
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

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), byteArray);
        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", "captured_image.png", requestBody);

        MLRetrofitClient.getInstance().getMLService().uploadImage(imagePart)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            try {
                                String prediction = response.body().string();
                                Log.d("Prediction", "Skin type prediction: " + prediction);

                                Intent intent = new Intent(HomeScreenActivity.this, SkinResultsActivity.class);
                                intent.putExtra("prediction_result", prediction);
                                startActivity(intent);

                            } catch (IOException e) {
                                Log.e("ImageUpload", "Error processing response: " + e.getMessage());
                            }
                        } else {
                            Log.d("ImageUpload", "Error: " + response.errorBody());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("ImageUpload", "Failure: " + t.getMessage());
                    }
                });
    }

    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
