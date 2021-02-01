package com.example.atelieruldigitalfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SplashScreenActivity extends AppCompatActivity {
    private TextInputLayout emailLayout, passwordLayout;
    private TextInputEditText emailEditText, passwordEditText;
    private Button loginButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();

        loginButton.setOnClickListener(v -> {
            if (check()) {
                Toast.makeText(this, "Login successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class));
            }
        });
    }

    private void initViews() {
        emailLayout = findViewById(R.id.email_layout);
        passwordLayout = findViewById(R.id.layout_password);

        emailEditText = findViewById(R.id.email_editText);
        passwordEditText = findViewById(R.id.password_editText);
        loginButton = findViewById(R.id.login_btn);
    }

    private boolean check() {
        boolean isOk = true;
        if (emailEditText.getText().toString().equals("")) {
            emailLayout.setError("Empty field. Insert your email here");
            isOk = false;
        } else {
            emailLayout.setError(null);
        }
        if (passwordEditText.getText().toString().equals("")) {
            emailLayout.setError("Empty field. Insert your password here");
            isOk = false;
        } else {
            emailLayout.setError(null);
        }
        return isOk;
    }
}
