package com.example.lucere;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SetUpProfileActivity extends AppCompatActivity {
    private Spinner skinTypeSpinner;
    private Spinner birthYearSpinner;
    private EditText editFullName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up_profile);

        skinTypeSpinner = findViewById(R.id.spinnerSkinType);
        birthYearSpinner = findViewById(R.id.spinnerBirthYear);
        editFullName = findViewById(R.id.editFullName);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.skin_type_options, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        skinTypeSpinner.setAdapter(adapter);

        ArrayList<String> birthYears = new ArrayList<>();
        for (int year = 2014; year >= 1950; year--) {
            birthYears.add(String.valueOf(year));
        }

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this,
                R.layout.spinner_item, birthYears); // Use your custom item layout

        // Set the layout for the dropdown items
        adapter2.setDropDownViewResource(R.layout.spinner_item); // Use the same layout for dropdown

        // Apply the adapter to the spinner
        birthYearSpinner.setAdapter(adapter2);

        TextView skipBtn = findViewById(R.id.skip_btn);
        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHome();
            }
        });

        TextView nextBtn = findViewById(R.id.next_btn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateForm()) {
                    goToHome();
                }
            }
        });
    }

    private boolean validateForm() {
        String fullName = editFullName.getText().toString().trim();

        if (TextUtils.isEmpty(fullName)) {
            Toast.makeText(this, "Full Name is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true; // Return true if all validations pass
    }

    private void goToHome() {
        Intent intent = new Intent(SetUpProfileActivity.this, HomeScreenActivity.class); // Adjust this to your home activity
        startActivity(intent);
        finish(); // Optional: finish this activity if you want to remove it from the back stack
    }
}
