package com.example.atelieruldigitalfinalproject.DataPackage;

import android.graphics.Bitmap;

public class InputData {
    private int id;
    private String tripName, tripDestination, tripType;
    private float price, ratingBar;
    private Bitmap imageBitmap;
    private int startDay, finishDay, startMonth, finishMonth, startYear, finishYear;
    private boolean favourite=false;

    public InputData() {
    }

    public static InputData getDefaultInputData() {
        return new InputData("", "", "", 0, 0, null, 0, 0, 0, 0, 0, 0);
    }

    public InputData(String tripName, String tripDestination, String tripType, float price, float ratingBar, Bitmap imageBitmap, int startDay, int finishDay, int startMonth, int finishMonth, int startYear, int finishYear) {
        this.tripName = tripName;
        this.tripDestination = tripDestination;
        this.tripType = tripType;
        this.price = price;
        this.ratingBar = ratingBar;
        this.imageBitmap = imageBitmap;
        this.startDay = startDay;
        this.finishDay = finishDay;
        this.startMonth = startMonth;
        this.finishMonth = finishMonth;
        this.startYear = startYear;
        this.finishYear = finishYear;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getTripDestination() {
        return tripDestination;
    }

    public void setTripDestination(String tripDestination) {
        this.tripDestination = tripDestination;
    }

    public String getTripType() {
        return tripType;
    }

    public void setTripType(String tripType) {
        this.tripType = tripType;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getRatingBar() {
        return ratingBar;
    }

    public void setRatingBar(float ratingBar) {
        this.ratingBar = ratingBar;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    public int getStartDay() {
        return startDay;
    }

    public void setStartDay(int startDay) {
        this.startDay = startDay;
    }

    public int getFinishDay() {
        return finishDay;
    }

    public void setFinishDay(int finishDay) {
        this.finishDay = finishDay;
    }

    public int getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(int startMonth) {
        this.startMonth = startMonth;
    }

    public int getFinishMonth() {
        return finishMonth;
    }

    public void setFinishMonth(int finishMonth) {
        this.finishMonth = finishMonth;
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public int getFinishYear() {
        return finishYear;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFinishYear(int finishYear) {
        this.finishYear = finishYear;
    }
}
