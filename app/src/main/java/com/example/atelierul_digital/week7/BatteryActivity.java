package com.example.atelierul_digital.week7;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.atelierul_digital.R;

public class BatteryActivity extends AppCompatActivity {
    private ImageView batteryImageView;
    private Button buttonMinus, buttonPlus;

    private int currentLevel = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery);
        batteryImageView = findViewById(R.id.batteryImageView);
        buttonMinus = findViewById(R.id.minus_button);
        buttonPlus = findViewById(R.id.plus_btn);

        updateBatteryLife(currentLevel);
    }

    public void OnBatteryMinusChange(View view) {
        updateBatteryLife(--currentLevel);
        updateButtonState(currentLevel);
    }

    public void OnBatteryPlusChange(View view) {
        updateBatteryLife(++currentLevel);
        updateButtonState(currentLevel);
    }

    private void updateBatteryLife(int level) {
        batteryImageView.setImageLevel(level);
    }

    private void updateButtonState(int currentLevel) {
        buttonMinus.setEnabled(currentLevel > 1);
        buttonPlus.setEnabled(currentLevel < 7);
    }
}
