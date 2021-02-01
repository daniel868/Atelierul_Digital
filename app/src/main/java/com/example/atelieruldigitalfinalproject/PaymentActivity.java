package com.example.atelieruldigitalfinalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.atelieruldigitalfinalproject.DataPackage.RoomDB.Entity.Payment;
import com.example.atelieruldigitalfinalproject.DataPackage.RoomDB.PaymentRepository;
import com.example.atelieruldigitalfinalproject.DataPackage.RoomDB.TripRepository;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Date;

public class PaymentActivity extends AppCompatActivity {
    private Spinner monthSpinner, yearSpinner;
    private TextInputEditText creditCarNumber, creditCarHolderName, credCardCVV;
    private TextInputLayout creditNumberLayout, creditHolderLayout;

    private TextView totalPay;
    private Button payBtn;

    private float sum_total;
    private String trip_name;

    private ArrayAdapter<String> monthAdapter;
    private ArrayAdapter<Integer> yearAdapter;

    private PaymentRepository repository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        repository = new PaymentRepository(this);

        initViews();
        initSpinners();
        handleIntent();

        payBtn.setOnClickListener(v -> saveData());
    }

    private void saveData() {
        if (checkError()) {
            Payment payment = new Payment();
            payment.setTripName(trip_name);
            payment.setDate(new Date());
            payment.setTripPrice(sum_total);

            repository.insert(payment);
            setResult(Activity.RESULT_OK);
            finish();
        }
    }

    private boolean checkError() {
        boolean isCheck = true;
        if (creditCarNumber.getText().toString().equals("")) {
            isCheck = false;
            creditNumberLayout.setError("Credit Card Number missing");
        } else {
            creditNumberLayout.setError(null);
        }
        if (creditCarHolderName.getText().toString().equals("")) {
            isCheck = false;
            creditHolderLayout.setError("Name is missing");
        } else {
            creditHolderLayout.setError(null);
        }
        if (credCardCVV.getText().toString().equals("")) {
            isCheck = false;
            Toast.makeText(this, "Invalid CVV input", Toast.LENGTH_SHORT).show();
        }
        if (sum_total == 0.0) {
            isCheck = false;
            Toast.makeText(this, "Invalid sum", Toast.LENGTH_SHORT).show();
        }
        if (trip_name == null) {
            isCheck = false;
            Toast.makeText(this, "Problem getting trip details", Toast.LENGTH_SHORT).show();
        }

        return isCheck;
    }

    private void initViews() {
        monthSpinner = findViewById(R.id.month_spinner);
        yearSpinner = findViewById(R.id.year_spinner);

        creditCarNumber = findViewById(R.id.credCardNumberEditText);
        creditCarHolderName = findViewById(R.id.holdersNameEditText);
        credCardCVV = findViewById(R.id.cvv);

        creditNumberLayout = findViewById(R.id.credCardInputField);
        creditHolderLayout = findViewById(R.id.carHoldersName);

        totalPay = findViewById(R.id.total_pay_txtView);
        payBtn = findViewById(R.id.finish_pay_btn);
    }

    private void initSpinners() {
        monthAdapter = setMonthSpinnerAdapter();
        yearAdapter = setYearSpinnerAdapter();

        monthSpinner.setAdapter(monthAdapter);
        yearSpinner.setAdapter(yearAdapter);
    }

    private ArrayList<String> setMonthSpinnerData() {
        ArrayList<String> data = new ArrayList<>();
        for (int i = 1; i < 13; i++) {
            data.add(i < 10 ? "0" + i : "" + i);
        }
        return data;
    }

    private ArrayList<Integer> setYearSpinnerData() {
        ArrayList<Integer> data = new ArrayList<>();
        for (int i = 2021; i < 2030; i++) {
            data.add(i);
        }
        return data;
    }

    private ArrayAdapter<String> setMonthSpinnerAdapter() {
        return new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, setMonthSpinnerData());
    }

    private ArrayAdapter<Integer> setYearSpinnerAdapter() {
        return new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, setYearSpinnerData());
    }

    private void handleIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            sum_total = intent.getExtras().getFloat(DetailActivity.TOTAL_KEY);
            trip_name = intent.getExtras().getString(DetailActivity.TRIP_NAME);
            totalPay.setText(String.format("Total to pay: %s", sum_total));
        }
    }
}
