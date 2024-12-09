package com.example.lucere;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private TextView loginButton;
    private TextView footerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        CSVImporter.importCSV(this, R.raw.skincare_products_clean, dbHelper);

        emailEditText = findViewById(R.id.editTextTextEmailAddress2); // Replace with your EditText ID
        passwordEditText = findViewById(R.id.editTextTextEmailAddress); // Replace with your EditText ID
        loginButton = findViewById(R.id.login_btn); // Replace with your Button ID
        footerTextView = findViewById(R.id.login_footer); // TextView for footer

        setupClickableFooter();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performLogin();
            }
        });
    }

    private void performLogin() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (email.equals("test@example.com") && password.equals("password123")) {
            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupClickableFooter() {
        String text = "Don’t have an account? Create Account";
        SpannableString spannableString = new SpannableString(text);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
                startActivity(intent);
            }
        };

        int startIndex = text.indexOf("Create Account");
        int endIndex = startIndex + "Create Account".length();
        spannableString.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        footerTextView.setText(spannableString);
        footerTextView.setMovementMethod(LinkMovementMethod.getInstance());
    }
}