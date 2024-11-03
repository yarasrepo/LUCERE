package com.example.lucere;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;

public class CreateAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        // Initialize UI elements
        EditText emailEditText = findViewById(R.id.editEmail);
        EditText passwordEditText = findViewById(R.id.editPassword);
        EditText confirmPasswordEditText = findViewById(R.id.editConfirmPassword);
        TextView createAccountButton = findViewById(R.id.create_btn);
        TextView footerTextView = findViewById(R.id.createAccount_footer);

        // Create the spannable string for "Already have an account? Login"
        SpannableString spannableString = new SpannableString("Already have an account? Login");

        // Define the clickable span for "Login"
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                // Redirect to login screen when "Login" is clicked
                Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        };

        // Set the span for "Login"
        spannableString.setSpan(clickableSpan, 25, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        footerTextView.setText(spannableString);
        footerTextView.setMovementMethod(LinkMovementMethod.getInstance());

        // Set click listener for the Create Account button
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();

                // Basic form validation
                if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(CreateAccountActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirmPassword)) {
                    Toast.makeText(CreateAccountActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else {
                    // Handle account creation (e.g., saving user data, calling APIs, etc.)
                    Toast.makeText(CreateAccountActivity.this, "Account created successfully!", Toast.LENGTH_SHORT).show();

                    // Redirect to HomeScreenActivity after account creation
                    Intent intent = new Intent(CreateAccountActivity.this, HomeScreenActivity.class);
                    startActivity(intent);
                    finish(); // Close the CreateAccountActivity
                }
            }
        });
    }

}