package com.example.atelieruldigitalfinalproject.DataPackage.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.atelieruldigitalfinalproject.DataPackage.RoomDB.Entity.InputData;

public class DetailsViewModel extends AndroidViewModel {
    private InputData inputData;

    public DetailsViewModel(@NonNull Application application) {
        super(application);
    }

    public InputData getInputData() {
        return inputData;
    }

    public void setInputData(InputData inputData) {
        this.inputData = inputData;
    }
}
