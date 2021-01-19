package com.example.atelieruldigitalfinalproject;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;


public class AddEditActivity extends AppCompatActivity {
    private static final String TAG = "AddEditActivity";
    public static final int GALLERY_REQUEST_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;

    private FloatingActionButton saveFab;
    private TextInputEditText tripNameEditText, tripDestinationEditText;
    private RadioGroup tripOptionRadioGroup;
    private SeekBar priceSeekBar;
    private ImageButton startDateBtn, finishDateBtn;
    private TextView startDateTv, finishDateTv;
    private RatingBar ratingBar;
    private ImageView locationImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_desing_test);
        initViews();

        saveFab.setOnClickListener(v -> saveData());
        locationImageView.setOnClickListener(v -> openCamera());
    }

    private void initViews() {
        saveFab = findViewById(R.id.insertDataFab);
        tripNameEditText = findViewById(R.id.trip_name_editText);
        tripDestinationEditText = findViewById(R.id.trip_destination_editText);
        tripOptionRadioGroup = findViewById(R.id.radio_group);
        priceSeekBar = findViewById(R.id.price_select);
        startDateBtn = findViewById(R.id.startCalendarImg);
        finishDateBtn = findViewById(R.id.finishCalendarImg);
        startDateTv = findViewById(R.id.start_dateTextView);
        finishDateTv = findViewById(R.id.finishDateTextView);
        ratingBar = findViewById(R.id.ratingStarBar);
        locationImageView = findViewById(R.id.select_location_imageView);
    }

    private void saveData() {
        Intent intent = new Intent();
        setResult(Activity.RESULT_OK, intent);
        finish();
    }


    private void openCamera() {
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
        } else {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
        }
    }


    private void openGallery() {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, GALLERY_REQUEST_CODE);
        } else {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST_CODE) {
            Log.d(TAG, "onActivityResult: Camera image selected");

            Bitmap photo = (Bitmap) data.getExtras().get("data");
            locationImageView.setImageBitmap(photo);
        }
        if (resultCode == RESULT_OK && requestCode == GALLERY_REQUEST_CODE) {
            Log.d(TAG, "onActivityResult: Gallery image selected");
            Uri selectedData = data.getData();
            if (selectedData != null) {
                ImageDecoder.Source source = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    source = ImageDecoder.createSource(this.getContentResolver(), selectedData);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    try {
                        locationImageView.setImageBitmap(ImageDecoder.decodeBitmap(source));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                Log.d(TAG, "onActivityResult: Null Pointer exception");
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == GALLERY_REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
        } else {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
        }
        if (requestCode == CAMERA_REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
        } else {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }
}
