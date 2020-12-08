package com.example.atelierul_digital.week3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.atelierul_digital.R;

public class ScrollView2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_view2);

    }

    public void onTestClick(View view) {
        ((TextView)findViewById(R.id.longTextView)).setText(R.string.very_long_string);
    }
}
