package com.example.atelieruldigitalfinalproject;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.atelieruldigitalfinalproject.DataPackage.InputData;
import com.example.atelieruldigitalfinalproject.DataPackage.ViewModels.DetailsViewModel;
import com.example.atelieruldigitalfinalproject.UIFragments.MainFragment;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = "DetailActivity";
    private ImageView detailImageView;
    private TextView startDateTxtView, finishDateTxtView, priceTxtView, weatherTxtView, tripNameTxtView, tripDestinationTxtView, tripTypeTxtView;
    private RatingBar ratingBar;

    private DetailsViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        viewModel = new ViewModelProvider(this).get(DetailsViewModel.class);

        initViews();
        if (viewModel.getInputData() != null) {
            setData(viewModel.getInputData());
        } else {
            handleIntent();
        }
    }

    private void initViews() {
        detailImageView = findViewById(R.id.detailImgView);
        startDateTxtView = findViewById(R.id.detailStartDateTxtView);
        finishDateTxtView = findViewById(R.id.detailFinishDateTxtView);
        priceTxtView = findViewById(R.id.detailsTripPrice);
        weatherTxtView = findViewById(R.id.detailWeatherAPI);
        tripNameTxtView = findViewById(R.id.detailTripNameTxtView);
        tripDestinationTxtView = findViewById(R.id.detailTripDestinationTxtView);
        tripTypeTxtView = findViewById(R.id.detailTripType);
        ratingBar = findViewById(R.id.detailRatingBar);
    }

    private void handleIntent() {
        if (getIntent() != null) {
            int id = getIntent().getIntExtra(MainFragment.ID_POST, -1);
            if (id != -1) {
                setData(Utils.getById(id, getApplicationContext()));
                viewModel.setInputData(Utils.getById(id, getApplicationContext()));
                Log.d(TAG, "handleIntent: Activity recreated for Id: " + id);
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
}
