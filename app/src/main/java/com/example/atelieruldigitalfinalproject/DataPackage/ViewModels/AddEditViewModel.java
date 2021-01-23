package com.example.atelieruldigitalfinalproject.DataPackage.ViewModels;

import android.app.Application;
import android.graphics.Bitmap;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class AddEditViewModel extends AndroidViewModel {
    private Bitmap imageBitmap;
    private String startDateTextView, finishDataTextView;


    public AddEditViewModel(@NonNull Application application) {
        super(application);
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    public String getStartDateTextView() {
        return startDateTextView;
    }

    public void setStartDateTextView(String startDateTextView) {
        this.startDateTextView = startDateTextView;
    }

    public String getFinishDataTextView() {
        return finishDataTextView;
    }

    public void setFinishDataTextView(String finishDataTextView) {
        this.finishDataTextView = finishDataTextView;
    }
}
