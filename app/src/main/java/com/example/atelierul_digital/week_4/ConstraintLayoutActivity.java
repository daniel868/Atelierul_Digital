package com.example.atelierul_digital.week_4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.atelierul_digital.R;

public class ConstraintLayoutActivity extends AppCompatActivity {
    private Button btn1,btn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constrait_layout);
        btn1 = findViewById(R.id.b1);
        btn2 = findViewById(R.id.b2);
        btn1.setOnClickListener((v-> Toast.makeText(this, "Button 1 ", Toast.LENGTH_SHORT).show()));
        btn2.setOnClickListener((v-> Toast.makeText(this, "Button 2 ", Toast.LENGTH_SHORT).show()));

    }
}
