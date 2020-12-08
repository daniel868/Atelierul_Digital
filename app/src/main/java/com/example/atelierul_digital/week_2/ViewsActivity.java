package com.example.atelierul_digital.week_2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.atelierul_digital.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ViewsActivity extends AppCompatActivity {
    private static final String TAG = "ViewsActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_views2);

        OkHttpClient client = new OkHttpClient();
        Request request =  new Request.Builder()
                .url("https://aerodatabox.p.rapidapi.com/flights/airports/icao/UUEE/2020-12-26T12:00/2020-12-27T00:00")
                .get()
                .addHeader("x-rapidapi-key","f13ff5b7a1msh3f1710c9b193452p14f4cfjsn3d105d53f183")
                .addHeader("x-rapidapi-host", "aerodatabox.p.rapidapi.com")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: Failed");
                Log.e(TAG, "onFailure: ",e );
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code()==200){
                    Log.d(TAG, "onResponse: "+response.body().string());
                }
            }
        });

    }
}
