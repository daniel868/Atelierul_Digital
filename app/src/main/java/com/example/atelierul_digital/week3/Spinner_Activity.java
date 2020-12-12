package com.example.atelierul_digital.week3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.atelierul_digital.R;

import java.util.ArrayList;

public class Spinner_Activity extends AppCompatActivity {
    private Spinner spinner;
    private ArrayList<String> spinnerData;
    private ArrayAdapter<String> spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner_);
        spinner = findViewById(R.id.mySpinner);
        spinnerData = setSpinnerData();
        spinnerAdapter = setSpinnerAdapter();
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Spinner_Activity.this, "Selected:"+parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private ArrayList<String> setSpinnerData() {
        ArrayList<String> spinnerData = new ArrayList<>();
        spinnerData.add("Cupcake");
        spinnerData.add("Donut");
        spinnerData.add("Eclair");
        spinnerData.add("KitKat");
        spinnerData.add("Pie");
        return spinnerData;
    }

    private ArrayAdapter<String> setSpinnerAdapter() {
        return new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerData);
    }
}
