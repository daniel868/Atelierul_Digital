package com.example.atelieruldigitalfinalproject;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atelieruldigitalfinalproject.DataPackage.Adapters.InfoAdapter;
import com.example.atelieruldigitalfinalproject.DataPackage.InputData;
import com.example.atelieruldigitalfinalproject.DataPackage.ViewModels.DetailsViewModel;
import com.example.atelieruldigitalfinalproject.DataPackage.WeatherAPI.DataAPI.Weather;
import com.example.atelieruldigitalfinalproject.DataPackage.WeatherAPI.DataAPI.WeatherData;
import com.example.atelieruldigitalfinalproject.DataPackage.WeatherAPI.Network.NetworkClass;
import com.example.atelieruldigitalfinalproject.DataPackage.WeatherAPI.Network.WeatherService;
import com.example.atelieruldigitalfinalproject.UIFragments.MainFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = "DetailActivity";
    private ImageView detailImageView;
    private TextView startDateTxtView, finishDateTxtView, priceTxtView, weatherTxtView, tripNameTxtView, tripDestinationTxtView, tripTypeTxtView;
    private RatingBar ratingBar;
    private RecyclerView infoRecycleView;
    private LinearLayoutManager linearLayoutManager;
    private InfoAdapter adapter;

    private DetailsViewModel viewModel;
    private WeatherService weatherService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_test);

        viewModel = new ViewModelProvider(this).get(DetailsViewModel.class);
        weatherService = NetworkClass.getInstance().create(WeatherService.class);
        initViews();
        setWeatherData();

        if (viewModel.getInputData() != null) {
            setData(viewModel.getInputData());
            setRecycleViewData(viewModel.getInputData());
        } else {
            handleIntent();
        }
    }

    private void initViews() {
        detailImageView = findViewById(R.id.infoImageView);
        startDateTxtView = findViewById(R.id.infoStartDateTxtView);
        finishDateTxtView = findViewById(R.id.infoFinishDateTxtView);
        priceTxtView = findViewById(R.id.infoPriceTxtView);
        weatherTxtView = findViewById(R.id.infoMaxTemperature);
        tripNameTxtView = findViewById(R.id.infoTripNameTxtView);
        tripDestinationTxtView = findViewById(R.id.infoTripDestinationTxtView);
        tripTypeTxtView = findViewById(R.id.infoTripTypeTxtView);
        ratingBar = findViewById(R.id.infoRatinBar);
        infoRecycleView = findViewById(R.id.suggestedTrips);
        adapter = new InfoAdapter();

        linearLayoutManager = new LinearLayoutManager(DetailActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        infoRecycleView.setLayoutManager(linearLayoutManager);

    }

    private void setRecycleViewData(InputData currentInputData) {
        List<InputData> suggestedList = new ArrayList<>();
        for (InputData defaultInputData : Utils.testItems(DetailActivity.this)) {
            if (defaultInputData.getTripType().equals(currentInputData.getTripType()) &
                    !defaultInputData.getTripName().equals(currentInputData.getTripName())) {
                suggestedList.add(defaultInputData);
            }
        }
        Log.d(TAG, "setRecycleViewData: Size " + suggestedList.size());
        adapter.setInputData(suggestedList);
        infoRecycleView.setAdapter(adapter);
    }

    private void handleIntent() {
        if (getIntent() != null) {
            int id = getIntent().getIntExtra(MainFragment.ID_POST, -1);
            if (id != -1) {
                setData(Utils.getById(id, getApplicationContext()));
                setRecycleViewData(Utils.getById(id, getApplicationContext()));
                viewModel.setInputData(Utils.getById(id, getApplicationContext()));
            }
        }
    }

    private void setData(InputData inputData) {
        detailImageView.setImageBitmap(inputData.getImageBitmap());
        startDateTxtView.setText(inputData.getStartDay() + "/" + inputData.getStartMonth() + "/" + inputData.getStartYear());
        finishDateTxtView.setText(inputData.getFinishDay() + "/" + inputData.getFinishMonth() + "/" + inputData.getFinishYear());
        priceTxtView.setText("$ " + inputData.getPrice());
        tripTypeTxtView.setText(inputData.getTripType());
        tripNameTxtView.setText(inputData.getTripName());
        tripDestinationTxtView.setText(inputData.getTripDestination());
        ratingBar.setRating(inputData.getRatingBar());
    }

    private void setWeatherData() {
        weatherService.getDataByCity("Arad,RO").enqueue(new Callback<WeatherData>() {
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

    private void handleResponse(@NonNull Response<WeatherData> response) {
        WeatherData weatherData = response.body();
        if (weatherData != null) {
            weatherTxtView.setText("MaxTemperatures " + weatherData.getMain().getTempMax());
        } else {
            Log.d(TAG, "handleResponse: Failed");
        }
    }
}
