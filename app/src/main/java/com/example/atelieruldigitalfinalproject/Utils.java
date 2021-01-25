package com.example.atelieruldigitalfinalproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.atelieruldigitalfinalproject.DataPackage.InputData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {


    public static boolean checkValidData(InputData inputData) {
        boolean isValid = true;
        try {
            if (inputData.getTripName().equals("")) {
                isValid = false;
                throw new Exception("TripInputName");
            }
            if (inputData.getTripType().equals("")) {
                isValid = false;
                throw new Exception("TripInputType");
            }
            if (inputData.getTripDestination().equals("")) {
                isValid = false;
                throw new Exception("InputDestinationName");
            }
            if (inputData.getImageBitmap() == null) {
                isValid = false;
                throw new Exception("BitmapImageNull");
            }
            if (inputData.getFinishDay() < 1 && inputData.getFinishMonth() < 1 && inputData.getFinishYear() < 1) {
                isValid = false;
                throw new Exception("Invalid Start Data Format");
            }
            if (inputData.getStartDay() < 1 && inputData.getStartMonth() < 1 && inputData.getStartYear() < 1) {
                isValid = false;
                throw new Exception("Invalid Finish Data Format");
            }
            if (inputData.getPrice() <= 0) {
                isValid = false;
                throw new Exception("Price Invalid Amount");
            }
            if (inputData.getRatingBar() <= 0) {
                isValid = false;
                throw new Exception("Rating Bar Invalid");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isValid;
    }

    public static List<InputData> testItems(Context context) {
        List<InputData> inputDataList = new ArrayList<>();
        Bitmap img1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.testimg1);
        Bitmap img2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.testimg2);
        Bitmap img3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.testimg3);

        InputData inputData0 = new InputData("TripItem1", "TripTitle1", context.getString(R.string.mountains_option), 80f, 3.5f, img3, 2, 3, 2, 2, 2020, 2020);
        inputData0.setId(0);
        inputData0.setFavourite(true);
        InputData inputData1 = new InputData("TripItem2", "TripTitle2", context.getString(R.string.sea_side_option), 300f, 3.5f, img1, 2, 3, 2, 2, 2020, 2020);
        inputData1.setId(1);
        inputData1.setFavourite(true);
        InputData inputData2 = new InputData("TripItem3", "TripTitle3", context.getString(R.string.sea_side_option), 350f, 3.5f, img2, 2, 3, 2, 4, 2020, 2020);
        inputData2.setId(2);
        inputData2.setFavourite(false);
        InputData inputData3 = new InputData("TripItem4", "TripTitle4", context.getString(R.string.city_break_option), 100f, 3.5f, img3, 2, 3, 3, 1, 2020, 2020);
        inputData3.setId(3);
        inputData3.setFavourite(true);

        inputDataList.add(inputData0);
        inputDataList.add(inputData1);
        inputDataList.add(inputData2);
        inputDataList.add(inputData3);
        return inputDataList;
    }

    public static List<InputData> getFavouriteItems(Context context) {
        List<InputData> inputDataList = testItems(context);
        List<InputData> favouriteList = new ArrayList<>();
        for (InputData inputData : inputDataList) {
            if (inputData.isFavourite()) {
                favouriteList.add(inputData);
            }
        }
        return favouriteList;
    }

    public static InputData getById(int id, Context context) {
        Bitmap img1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.testimg1);
        Bitmap img2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.testimg2);
        Bitmap img3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.testimg3);

        InputData inputData0 = new InputData("TripItem1", "TripTitle1", context.getString(R.string.mountains_option), 80, 3.5f, img3, 2, 3, 2, 2, 2020, 2020);
        inputData0.setId(0);
        InputData inputData1 = new InputData("TripItem2", "TripTitle2", context.getString(R.string.sea_side_option), 300, 3.5f, img1, 2, 3, 2, 2, 2020, 2020);
        inputData1.setId(1);
        InputData inputData2 = new InputData("TripItem3", "TripTitle3", context.getString(R.string.sea_side_option), 350, 3.5f, img2, 2, 3, 2, 4, 2020, 2020);
        inputData2.setId(2);
        InputData inputData3 = new InputData("TripItem4", "TripTitle4", context.getString(R.string.city_break_option), 100, 3.5f, img3, 2, 3, 3, 1, 2020, 2020);
        inputData3.setId(3);
        HashMap<Integer, InputData> map = new HashMap<>();
        map.put(0, inputData0);
        map.put(1, inputData1);
        map.put(2, inputData2);
        map.put(3, inputData3);

        return map.get(id);
    }


}
