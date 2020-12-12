package com.example.atelierul_digital.week3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.atelierul_digital.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class loginActivity extends AppCompatActivity {

    private TextInputLayout emailInputLayout, phoneInputLayout;
    private TextInputEditText textInputEditText, phoneEditText;
    private Button submitButton;
    private CheckBox validateCB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailInputLayout = findViewById(R.id.textInputLayout);
        phoneInputLayout = findViewById(R.id.textInputPhone);

        submitButton = findViewById(R.id.submitButton);
        textInputEditText = findViewById(R.id.emailEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        validateCB = findViewById(R.id.validateCheckBox);

        submitButton.setEnabled(validateCB.isChecked());
        submitButton.setOnClickListener(v -> {
            validate();
        });

        validateCB.setOnCheckedChangeListener((buttonView, isChecked) -> {
            submitButton.setEnabled(isChecked);
        });

    }

    private void validate() {
        boolean isValid = true;
        if (phoneEditText.getText().toString().isEmpty()) {
            phoneInputLayout.setError("Invalid phone address");
            isValid = false;
        } else {
            clearError(phoneInputLayout);
        }
        if (textInputEditText.getText().toString().isEmpty()) {
            emailInputLayout.setError("Invalid email address");
            isValid = false;
        } else {
            clearError(emailInputLayout);
        }
        if (!isValidateEmailAddress(textInputEditText.getText().toString())) {
            emailInputLayout.setError("Invalid format email address");
            isValid = false;
        } else {
            clearError(emailInputLayout);
        }
        if (isValid) {
            Toast.makeText(this, "Valid data", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearError(TextInputLayout textInputLayout) {
        if (textInputLayout != null && textInputLayout.isErrorEnabled()) {
            textInputLayout.setError(null);
        }
    }

    //TODO:Add check validate email address
    private boolean isValidateEmailAddress(String emailAddressText) {
        if (emailAddressText.contains("@gmail.com") || (emailAddressText.contains("@yahoo.com"))
                || emailAddressText.contains("@")) {
            return true;
        }
        return false;
    }
}
