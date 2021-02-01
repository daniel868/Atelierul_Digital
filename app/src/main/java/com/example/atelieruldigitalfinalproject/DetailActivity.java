package com.example.atelieruldigitalfinalproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.atelieruldigitalfinalproject.DataPackage.RoomDB.Entity.InputData;
import com.example.atelieruldigitalfinalproject.DataPackage.RoomDB.TripRepository;
import com.example.atelieruldigitalfinalproject.DataPackage.ViewModels.DetailsViewModel;
import com.example.atelieruldigitalfinalproject.DataPackage.WeatherAPI.DataAPI.WeatherData;
import com.example.atelieruldigitalfinalproject.DataPackage.WeatherAPI.Network.NetworkClass;
import com.example.atelieruldigitalfinalproject.DataPackage.WeatherAPI.Network.WeatherService;
import com.example.atelieruldigitalfinalproject.UIFragments.MainFragment;
import com.google.android.material.slider.Slider;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity implements Slider.OnChangeListener, View.OnClickListener {
    public static final String TOTAL_KEY = "total_key";
    public static final String TRIP_NAME = "trip_name_key";
    public static final int ACTION_ID = 3;

    private static final String TAG = "DetailActivity";
    private ImageView detailImageView;
    private TextView start_finish_textView, priceTxtView, peopleNumber, total, tripNameTxtView, tripDestinationTxtView, tripTypeTxtView, ratingTxtView;
    private RatingBar ratingBar;
    private TextView api_humidity, api_minimTemp, api_feelsLike, api_windSpeed;
    private Slider slider;

    private Button bookBtn;

    private DetailsViewModel viewModel;
    private WeatherService weatherService;
    private TripRepository repository;

    private float tripPrice, trip_price;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity_2);

        viewModel = new ViewModelProvider(this).get(DetailsViewModel.class);
        weatherService = NetworkClass.getInstance().create(WeatherService.class);
        repository = new TripRepository(this);
        initViews();


        if (viewModel.getInputData() != null) {
            setData(viewModel.getInputData());
            setWeatherData(viewModel.getInputData());

        } else {
            handleIntent();
        }
    }

    private void initViews() {
        detailImageView = findViewById(R.id.background_image);

        priceTxtView = findViewById(R.id.trip_price);

        tripNameTxtView = findViewById(R.id.destinationNameTextView);
        tripDestinationTxtView = findViewById(R.id.trip_destination_here);
        tripTypeTxtView = findViewById(R.id.trip_type);

        ratingBar = findViewById(R.id.detailRatingBar);
        start_finish_textView = findViewById(R.id.start_finish_date);
        ratingTxtView = findViewById(R.id.txtViewRating);

        api_humidity = findViewById(R.id.humidity_API);
        api_feelsLike = findViewById(R.id.feels_like_API);
        api_minimTemp = findViewById(R.id.minim_API);
        api_windSpeed = findViewById(R.id.wind_API);

        peopleNumber = findViewById(R.id.personsNumber);
        total = findViewById(R.id.total);
        slider = findViewById(R.id.slider);
        slider.addOnChangeListener(this);

        bookBtn = findViewById(R.id.book_btn);
        bookBtn.setOnClickListener(this);

    }


    private void handleIntent() {
        if (getIntent() != null) {
            int id = getIntent().getIntExtra(MainFragment.ID_POST, -1);
            if (id != -1) {
                repository.getTripById(id).observe(this, inputData -> {
                    setData(inputData);

                    setWeatherData(inputData);

                    viewModel.setInputData(inputData);
                });

            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private void setData(InputData inputData) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");


        priceTxtView.setText(String.format("$ %s", inputData.getPrice()));
        tripTypeTxtView.setText(inputData.getTripType());
        tripNameTxtView.setText(inputData.getTripName());
        tripDestinationTxtView.setText(String.format("%s", "Visit " + inputData.getTripDestination()));
        ratingBar.setRating(inputData.getRatingBar());
        ratingTxtView.setText(String.format("%s", inputData.getRatingBar()));
        start_finish_textView.setText(
                String.format("%s / %s", simpleDateFormat.format(inputData.getStartDate()), simpleDateFormat.format(inputData.getFinishDate())));

        detailImageView.setImageURI(Uri.parse(inputData.getImageFilePath()));

        total.setText("Total: $0");
        peopleNumber.setText("0 persons");
        tripPrice = inputData.getPrice();
    }

    private void setWeatherData(InputData inputData) {
        weatherService.getDataByCity(inputData.getTripDestination()).enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                handleResponse(response);
            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    @SuppressLint("DefaultLocale")
    private void handleResponse(@NonNull Response<WeatherData> response) {
        WeatherData weatherData = response.body();
        if (weatherData != null) {
            api_minimTemp.setText(String.format("%s Degree", weatherData.getMain().getTempMin()));
            api_windSpeed.setText(String.format("%s km/h", weatherData.getWind().getSpeed()));
            api_feelsLike.setText(String.format("%s Degree", weatherData.getMain().getFeelsLike()));
            api_humidity.setText(String.format("%s %s", weatherData.getMain().getHumidity(), "%"));
        } else {
            Log.d(TAG, "handleResponse: Failed");
        }
    }


    @Override
    public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
        peopleNumber.setText(String.format("%s persons", value));
        trip_price = value * tripPrice;
        total.setText(String.format("Total:$ %s", trip_price));
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(TOTAL_KEY, trip_price);
        intent.putExtra(TRIP_NAME, tripNameTxtView.getText().toString());
        startActivityForResult(intent, ACTION_ID);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        View view = findViewById(R.id.view_snack);
        if (requestCode == ACTION_ID && resultCode == RESULT_OK) {
            Snackbar.make(view, "Finish Payment", BaseTransientBottomBar.LENGTH_SHORT)
                    .setAction("OK", v -> {
                    }).show();
        } else {
            Snackbar.make(view, "Can't process the payment ", BaseTransientBottomBar.LENGTH_SHORT)
                    .setAction("OK", v -> {
                    }).show();
        }
    }
}
