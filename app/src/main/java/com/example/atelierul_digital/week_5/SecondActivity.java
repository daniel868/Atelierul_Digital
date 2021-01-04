package com.example.atelierul_digital.week_5;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.atelierul_digital.R;

public class SecondActivity extends AppCompatActivity {
    private static final String TAG = "SecondActivity";
    private TextView tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        tv = findViewById(R.id.secondActivityTextView);

        int stringRes = 0;
        String stringText=null;
        Intent intent = getIntent();
        if (intent != null) {
            stringRes = intent.getIntExtra(ThreeButtonsActivity.EXTRA_STRING_RES_LONG, 0);
            stringText = intent.getStringExtra(ThreeButtonsActivity.EXTRA_TEXT_LONG);
        }
        if (stringRes != 0) {
            tv.setText(stringRes);
        }else {
            Log.d(TAG, "onCreate: Failed parse stringRes");
        }
        if(stringText!=null){
            tv.append("\n"+stringText);
        }
        Log.d(TAG, "Failed parse stringText");
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
