package com.example.atelieruldigitalfinalproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.atelieruldigitalfinalproject.DataPackage.RoomDB.Entity.InputData;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
            if (inputData.getImageFilePath() == null) {
                isValid = false;
                throw new Exception("BitmapImageNull");
            }
            if (inputData.getStartDate() == null) {
                isValid = false;
                throw new Exception("Start Date problem");
            }
            if (inputData.getFinishDate() == null) {
                isValid = false;
                throw new Exception("Finish Date problem");
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

        InputData inputData0 = new InputData("Rome,7 nights, all inclusive", "Rome", context.getString(R.string.sea_side_option), 80f, 3.5f);
        inputData0.setId(0);
        inputData0.setFavourite(true);
        inputData0.setStartDate(new Date());
        inputData0.setFinishDate(new Date());

        InputData inputData1 = new InputData("Santorini, 5 nights, 3 trips included", "Santorini", context.getString(R.string.mountains_option), 300f, 3.5f);
        inputData1.setId(1);
        inputData1.setFavourite(true);
        inputData1.setStartDate(new Date());
        inputData1.setFinishDate(new Date());

        InputData inputData2 = new InputData("Mamaia, 5 nights, 5 start Hotel", "Romania", context.getString(R.string.sea_side_option), 350f, 3.5f);
        inputData2.setId(2);
        inputData2.setFavourite(false);
        inputData2.setStartDate(new Date());
        inputData2.setFinishDate(new Date());

        InputData inputData3 = new InputData("Paris, 3 days city Break, 5 Start Hotel", "Paris", context.getString(R.string.city_break_option), 100f, 3.5f);
        inputData3.setId(3);
        inputData3.setFavourite(true);
        inputData3.setStartDate(new Date());
        inputData3.setFinishDate(new Date());

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

        InputData inputData0 = new InputData("Rome,7 nights, all inclusive", "Rome", context.getString(R.string.sea_side_option), 80f, 3.5f);
        inputData0.setId(0);
        InputData inputData1 = new InputData("Santorini, 5 nights, 3 trips included", "Santorini", context.getString(R.string.mountains_option), 300f, 3.5f);
        inputData1.setId(1);
        InputData inputData2 = new InputData("Mamaia, 5 nights, 5 start Hotel", "Romania", context.getString(R.string.sea_side_option), 350f, 3.5f);
        inputData2.setId(2);
        InputData inputData3 = new InputData("Paris, 3 days city Break, 5 Start Hotel", "Paris", context.getString(R.string.city_break_option), 100f, 3.5f);
        inputData3.setId(3);
        HashMap<Integer, InputData> map = new HashMap<>();
        map.put(0, inputData0);
        map.put(1, inputData1);
        map.put(2, inputData2);
        map.put(3, inputData3);

        return map.get(id);
    }


}
