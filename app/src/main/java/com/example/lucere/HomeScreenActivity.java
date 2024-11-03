package com.example.lucere;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.io.ByteArrayOutputStream;

public class HomeScreenActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView imageView;
    private ImageView cameraIcon;
    private Toolbar toolbar;
    private Bitmap capturedImageBitmap;  // Variable to store the captured image
    private ImageView profileIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);

        imageView = findViewById(R.id.background2);
        cameraIcon = findViewById(R.id.image_camera);
        toolbar = findViewById(R.id.toolbar);
        profileIcon = findViewById(R.id.profileIcon);
        // Set the OnClickListener for the camera icon
        cameraIcon.setOnClickListener(v -> openCamera());
        profileIcon.setOnClickListener(v -> openProfilePage());
        if (toolbar != null) {
            int statusBarHeight = getStatusBarHeight();
            toolbar.setPadding(0, statusBarHeight, 0, 0);
        }
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            capturedImageBitmap = (Bitmap) extras.get("data");  // Save the image in a variable
//            imageView.setImageBitmap(capturedImageBitmap);  // Display the image
//
//            // Convert the Bitmap to ByteArray and pass it to the next activity
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            capturedImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//            byte[] byteArray = stream.toByteArray();

            Intent intent = new Intent(HomeScreenActivity.this, SkinResultsActivity.class);//yasmin add your page
           // intent.putExtra("captured_image", byteArray);  // Pass the ByteArray to the next activity
            startActivity(intent);
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
    private void openProfilePage(){
        Intent intent = new Intent(this, SetUpProfileActivity.class);
        startActivity(intent);
    }
}
