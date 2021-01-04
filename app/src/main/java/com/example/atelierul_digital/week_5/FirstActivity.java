package com.example.atelierul_digital.week_5;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.atelierul_digital.R;

public class FirstActivity extends AppCompatActivity {
    private static final String TAG = "FirstActivity";
    private Button button1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        button1 = findViewById(R.id.buttonFirst);
        button1.setOnClickListener(v->{
            Intent intent = new Intent(this,SecondActivity.class);
            startActivity(intent);
        });
        Log.d(TAG, "onCreate: called with savedInstanceState=[ "+savedInstanceState+"]");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: called");
    }
}
