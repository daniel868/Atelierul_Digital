package com.example.atelieruldigitalfinalproject;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProvider;

import com.example.atelieruldigitalfinalproject.DataPackage.RoomDB.TripRepository;
import com.example.atelieruldigitalfinalproject.DataPackage.ViewModels.AddEditViewModel;
import com.example.atelieruldigitalfinalproject.DataPackage.RoomDB.Entity.InputData;
import com.example.atelieruldigitalfinalproject.UIFragments.MainFragment;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.slider.Slider;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class AddEditActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, RatingBar.OnRatingBarChangeListener, Slider.OnChangeListener {
    private static final String TAG = "AddEditActivity";
    public static final int GALLERY_REQUEST_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    public static final int DEFAULT_VALUE = -1;

    //TODO: Views
    private FloatingActionButton saveFab;
    private TextInputLayout tripNameLayout, tripDestinationLayout, startDateLayout, finishDateLayout;
    private TextInputEditText tripNameEditText, tripDestinationEditText, startDateTv, finishDateTv;
    private RadioGroup tripOptionRadioGroup;
    private Slider priceSlider;
    private ImageButton startDateBtn, finishDateBtn;
    //    private TextView , ;
    private RatingBar ratingBar;
    private ImageView locationImageView;
    private String[] dialogBoxOption;
    private Calendar calendar;
    private DatePickerDialog datePickerDialog;

    //TODO: Handle data
    private InputData inputData;
    private AddEditViewModel addEditViewModel;

    private String currentPhotoPath;
    private Uri photoUri;

    private TripRepository repository;

    int updatedId = DEFAULT_VALUE;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_v2);
        repository = new TripRepository(this);

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
            updatedId = getIntent().getIntExtra(MainFragment.ID_POST, -1);
            if (updatedId != -1) {
                Toast.makeText(AddEditActivity.this, "ID sented to edit" + updatedId, Toast.LENGTH_SHORT).show();
                repository.getTripById(updatedId).observe(this, inputData -> handleEditInputData(inputData));

            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private void handleEditInputData(InputData inputData) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

        tripDestinationEditText.setText(inputData.getTripDestination());
        tripNameEditText.setText(inputData.getTripName());

        startDateTv.setText(simpleDateFormat.format(inputData.getStartDate()));
        finishDateTv.setText(simpleDateFormat.format(inputData.getFinishDate()));
        ratingBar.setRating(inputData.getRatingBar());
        priceSlider.setValue(inputData.getPrice());

        locationImageView.setImageURI(Uri.parse(inputData.getImageFilePath()));

        currentPhotoPath = inputData.getImageFilePath();

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

        tripNameLayout = findViewById(R.id.layoutTripName);
        tripDestinationLayout = findViewById(R.id.layoutTripDestination);
        startDateLayout = findViewById(R.id.inputStartDateField);
        finishDateLayout = findViewById(R.id.inputFinishDateField);

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
        inputData = new InputData();

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

    private void checkInputTextBoxError() {
        if (tripNameEditText.getText().toString().equals("")) {
            tripNameLayout.setError(getString(R.string.trip_name_error));
        } else {
            clearError(tripNameLayout);
        }
        if (tripDestinationEditText.getText().toString().equals("")) {
            tripDestinationLayout.setError(getString(R.string.trip_destination_error));
        } else {
            clearError(tripDestinationLayout);
        }
        if (startDateTv.getText().toString().equals("")) {
            startDateLayout.setError(getString(R.string.trip_date_error));
        } else {
            clearError(startDateLayout);
        }
        if (finishDateTv.getText().toString().equals("")) {
            finishDateLayout.setError(getString(R.string.trip_finish_date_error));
        } else {
            clearError(finishDateLayout);
        }
    }

    private void clearError(TextInputLayout textInputLayout) {
        if (textInputLayout != null) {
            textInputLayout.setError(null);
        }
    }

    private void saveData() {
        Intent intent = new Intent();
        setEditTextData();
        checkInputTextBoxError();
        if (Utils.checkValidData(inputData) && updatedId == DEFAULT_VALUE) {
            repository.insert(inputData);
            galleryAddPicture(new File(inputData.getImageFilePath()));
            setResult(Activity.RESULT_OK, intent);
            finish();
        } else if (Utils.checkValidData(inputData) && updatedId != DEFAULT_VALUE) {
            if (!inputData.getImageFilePath().equals(currentPhotoPath)) {
                galleryAddPicture(new File(inputData.getImageFilePath()));
            }
            repository.update(inputData);
            setResult(Activity.RESULT_OK, intent);
            finish();
        } else {
            View view = findViewById(R.id.snackBarView);
            Snackbar.make(view, "Invalid Input Data", BaseTransientBottomBar.LENGTH_SHORT)
                    .setAction("OK", v -> {
                    }).show();
        }
    }

    private void openCamera() {
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
        } else {
            takePictureIntent();
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

    @SuppressLint("SimpleDateFormat")
    private void openCalendar(TextInputEditText txtView) {
        datePickerDialog = new DatePickerDialog(AddEditActivity.this, (view, year, month, dayOfMonth) -> {
            calendar.set(year, month, dayOfMonth);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

            txtView.setText(simpleDateFormat.format(calendar.getTime()));

            if (txtView.getId() == R.id.start_dateTextView) {
                inputData.setStartDate(calendar.getTime());
                addEditViewModel.setStartDateTextView(dayOfMonth + "/" + (month + 1) + "/" + year);
            }

            if (txtView.getId() == R.id.finishDateTextView) {
                inputData.setFinishDate(calendar.getTime());
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
            setPic(currentPhotoPath);
            inputData.setImageFilePath(currentPhotoPath);
        }
        if (resultCode == RESULT_OK && requestCode == GALLERY_REQUEST_CODE) {
            Log.d(TAG, "onActivityResult: Gallery image selected");
            Uri selectedData = data.getData();
            currentPhotoPath = getRealPathFromURI(selectedData);
            setPic(currentPhotoPath);
            inputData.setImageFilePath(currentPhotoPath);
//            if (selectedData != null) {
//                ImageDecoder.Source source = null;
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//                    source = ImageDecoder.createSource(this.getContentResolver(), selectedData);
//                }
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//                    try {
//                        locationImageView.setImageBitmap(ImageDecoder.decodeBitmap(source));
//
//
//                        addEditViewModel.setImageBitmap(ImageDecoder.decodeBitmap(source));
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            } else {
//                Log.d(TAG, "onActivityResult: Null Pointer exception");
//            }
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

    private File createFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void takePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (photoFile != null) {
                photoUri = FileProvider.getUriForFile(this, "com.example.atelieruldigitalfinalproject.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            }
        }
    }

    private void galleryAddPicture(final File file) {
        try {
            MediaStore.Images.Media.insertImage(getContentResolver(), file.getAbsolutePath(), file.getName(), null);
            getApplicationContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setPic(String currentPhotoPath) {

        int targetW = locationImageView.getWidth();
        int targetH = locationImageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.max(1, Math.min(photoW / targetW, photoH / targetH));

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        locationImageView.setImageBitmap(bitmap);
    }

    private String getRealPathFromURI(Uri contentUri) {
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = managedQuery(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            System.out.println("column_index of selected image is:" + column_index);
            cursor.moveToFirst();
            System.out.println("selected image path is:" + cursor.getString(column_index));
            return cursor.getString(column_index);
        } catch (Exception e) {
            return contentUri.getPath();
        }
    }
}
