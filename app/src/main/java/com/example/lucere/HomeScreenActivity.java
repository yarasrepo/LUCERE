package com.example.lucere;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.io.ByteArrayOutputStream;

public class HomeScreenActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView imageView;
    private ImageView cameraIcon;

    private Bitmap capturedImageBitmap;  // Variable to store the captured image
    private ImageView profileIcon;

    // ActivityResultLauncher for the camera
    private final ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        // Capture the image and display
                        Bundle extras = data.getExtras();
                        capturedImageBitmap = (Bitmap) extras.get("data");
                        imageView.setImageBitmap(capturedImageBitmap);

                        // Convert to byte array and pass to the next activity
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        capturedImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] byteArray = stream.toByteArray();

                        Intent intent = new Intent(HomeScreenActivity.this, SkinResultsActivity.class);
                        intent.putExtra("captured_image", byteArray);
                        startActivity(intent);
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);

        imageView = findViewById(R.id.background2);
        cameraIcon = findViewById(R.id.image_camera);

        profileIcon = findViewById(R.id.profileIcon);

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

    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private void openProfilePage() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }
}