package com.example.atelieruldigitalfinalproject;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.atelieruldigitalfinalproject.DataPackage.ViewModels.AddEditViewModel;
import com.example.atelieruldigitalfinalproject.DataPackage.InputData;
import com.example.atelieruldigitalfinalproject.UIFragments.MainFragment;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.slider.Slider;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.Calendar;


public class AddEditActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, RatingBar.OnRatingBarChangeListener, Slider.OnChangeListener {
    private static final String TAG = "AddEditActivity";
    public static final int GALLERY_REQUEST_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;

    //TODO: Views
    private FloatingActionButton saveFab;
    private TextInputEditText tripNameEditText, tripDestinationEditText;
    private RadioGroup tripOptionRadioGroup;
    private Slider priceSlider;
    private ImageButton startDateBtn, finishDateBtn;
    private TextView startDateTv, finishDateTv;
    private RatingBar ratingBar;
    private ImageView locationImageView;
    private String[] dialogBoxOption;
    private Calendar calendar;
    private DatePickerDialog datePickerDialog;

    //TODO: Handle data
    private InputData inputData;
    private AddEditViewModel addEditViewModel;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_desing_test);
        initViews();
        initData();
        handleEditIntent();

        saveFab.setOnClickListener(v -> saveData());
        locationImageView.setOnClickListener(v -> openDialogBox());
        startDateBtn.setOnClickListener(v -> openCalendar(startDateTv));
        finishDateBtn.setOnClickListener(v -> openCalendar(finishDateTv));
        tripOptionRadioGroup.setOnCheckedChangeListener(this::onCheckedChanged);
        ratingBar.setOnRatingBarChangeListener(this::onRatingChanged);
        priceSlider.addOnChangeListener(this::onValueChange);


    }

    private void handleEditIntent() {
        if (getIntent() != null) {
            int id = getIntent().getIntExtra(MainFragment.ID_POST, -1);
            if (id != -1) {
                Toast.makeText(AddEditActivity.this, "ID sented to edit" + id, Toast.LENGTH_SHORT).show();
                handleEditInputData(Utils.getById(id, AddEditActivity.this));
            }
        }
    }

    private void handleEditInputData(InputData inputData) {
        tripDestinationEditText.setText(inputData.getTripDestination());
        tripNameEditText.setText(inputData.getTripName());
        locationImageView.setImageBitmap(inputData.getImageBitmap());
        startDateTv.setText(new StringBuilder().append(inputData.getStartDay()).append("/").append(inputData.getStartMonth()).append("/").append(inputData.getStartYear()).toString());
        finishDateTv.setText(new StringBuilder().append(inputData.getFinishDay()).append("/").append(inputData.getFinishMonth()).append("/").append(inputData.getFinishYear()).toString());
        ratingBar.setRating(inputData.getRatingBar());
        priceSlider.setValue(inputData.getPrice());

        if (inputData.getTripType().equals(getString(R.string.mountains_option))) {
            tripOptionRadioGroup.check(R.id.mountains);
        }

        if (inputData.getTripType().equals(getString(R.string.city_break_option))) {
            tripOptionRadioGroup.check(R.id.cityBreak);
        }
        if (inputData.getTripType().equals(getString(R.string.sea_side_option))) {
            tripOptionRadioGroup.check(R.id.seaSide);
        }

        this.inputData = inputData;

    }

    private void initViews() {
        saveFab = findViewById(R.id.insertDataFab);
        tripNameEditText = findViewById(R.id.trip_name_editText);
        tripDestinationEditText = findViewById(R.id.trip_destination_editText);
        tripOptionRadioGroup = findViewById(R.id.radio_group);
        priceSlider = findViewById(R.id.priceSlider);
        startDateBtn = findViewById(R.id.startCalendarImg);
        finishDateBtn = findViewById(R.id.finishCalendarImg);
        startDateTv = findViewById(R.id.start_dateTextView);
        finishDateTv = findViewById(R.id.finishDateTextView);
        ratingBar = findViewById(R.id.ratingStarBar);
        locationImageView = findViewById(R.id.select_location_imageView);

        dialogBoxOption = new String[]{"Camera", "Gallery"};

        calendar = Calendar.getInstance();

        //TODO: darkMode Theme check
        int nightModeFlag = getApplicationContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (nightModeFlag == Configuration.UI_MODE_NIGHT_YES) {
            tripNameEditText.setBackgroundResource(R.color.black);
            tripDestinationEditText.setBackgroundResource(R.color.black);
        }
    }

    private void initData() {
        addEditViewModel = new ViewModelProvider(AddEditActivity.this).get(AddEditViewModel.class);
        inputData = InputData.getDefaultInputData();

        if (addEditViewModel.getImageBitmap() != null) {
            locationImageView.setImageBitmap(addEditViewModel.getImageBitmap());
        }
        if (addEditViewModel.getFinishDataTextView() != null) {
            finishDateTv.setText(addEditViewModel.getFinishDataTextView());
        }
        if (addEditViewModel.getStartDateTextView() != null) {
            startDateTv.setText(addEditViewModel.getStartDateTextView());
        }

    }

    private void setEditTextData() {
        inputData.setTripName(tripNameEditText.getText().toString());
        inputData.setTripDestination(tripDestinationEditText.getText().toString());
    }

    private void saveData() {
        Intent intent = new Intent();
        setEditTextData();
        if (Utils.checkValidData(inputData)) {
            setResult(Activity.RESULT_OK, intent);
            finish();
        } else {
            Toast.makeText(AddEditActivity.this, "Invalid Data", Toast.LENGTH_SHORT).show();
        }
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

    private void openDialogBox() {
        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(AddEditActivity.this)
                .setTitle("Pick an option")
                .setItems(dialogBoxOption, (dialogInterface, i) -> {
                    if (i == 0) {
                        openCamera();
                    } else if (i == 1) {
                        openGallery();
                    }
                });
        materialAlertDialogBuilder.show();
    }

    private void openCalendar(TextView txtView) {
        datePickerDialog = new DatePickerDialog(AddEditActivity.this, (view, year, month, dayOfMonth) -> {
            txtView.setText(dayOfMonth + "/" + (month + 1) + "/" + year);

            if (txtView.getId() == R.id.start_dateTextView) {
                inputData.setStartDay(dayOfMonth);
                inputData.setStartMonth(month + 1);
                inputData.setStartYear(year);

                addEditViewModel.setStartDateTextView(dayOfMonth + "/" + (month + 1) + "/" + year);
            }

            if (txtView.getId() == R.id.finishDateTextView) {
                inputData.setFinishDay(dayOfMonth);
                inputData.setFinishMonth(month + 1);
                inputData.setFinishMonth(year);

                addEditViewModel.setFinishDataTextView(dayOfMonth + "/" + (month + 1) + "/" + year);
            }

        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST_CODE) {
            Log.d(TAG, "onActivityResult: Camera image selected");

            Bitmap photo = (Bitmap) data.getExtras().get("data");
            locationImageView.setImageBitmap(photo);
            if (photo != null) {
                inputData.setImageBitmap(photo);
                addEditViewModel.setImageBitmap(photo);
            }
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
                        inputData.setImageBitmap(ImageDecoder.decodeBitmap(source));

                        addEditViewModel.setImageBitmap(ImageDecoder.decodeBitmap(source));
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

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.seaSide) {
            inputData.setTripType(getString(R.string.sea_side_option));
        } else if (checkedId == R.id.mountains) {
            inputData.setTripType(getString(R.string.mountains_option));
        } else if (checkedId == R.id.cityBreak) {
            inputData.setTripType(getString(R.string.city_break_option));
        }
        Log.d(TAG, "onCheckedChanged: " + inputData.getTripType());
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        inputData.setRatingBar(rating);
        Log.d(TAG, "onRatingChanged: " + rating);
    }


    @Override
    public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
        inputData.setPrice(value);
        Log.d(TAG, "onValueChange: " + value);
    }
}
