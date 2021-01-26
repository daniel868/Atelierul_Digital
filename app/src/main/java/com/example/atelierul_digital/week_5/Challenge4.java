package com.example.atelierul_digital.week_5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.atelierul_digital.R;

public class Challenge4 extends AppCompatActivity {
    private static final String TAG = "Challenge4";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge4);
    }

    public void openWebsite(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        EditText webSiteEditText = findViewById(R.id.websiteEditText);
        String url = webSiteEditText.getText().toString();
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    public void openLocation(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        EditText mapEditText = findViewById(R.id.locationEditText);
        String location = "http://maps.google.co.in/maps?q=" + mapEditText.getText().toString();
        intent.setData(Uri.parse(location));
        if (intent.resolveActivity(getPackageManager())!=null){
            startActivity(intent);
        }else {
            Log.d(TAG, "No app to handle");
        }
    }
}
