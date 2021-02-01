package com.example.atelieruldigitalfinalproject.DataPackage.RoomDB.Entity;

import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.example.atelieruldigitalfinalproject.DataPackage.RoomDB.Converters.DateConverter;

import java.util.Date;

@Entity(tableName = "trip_table")
public class InputData {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "tripName")
    private String tripName;

    @ColumnInfo(name = "tripDestination")
    private String tripDestination;

    @ColumnInfo(name = "tripType")
    private String tripType;

    @ColumnInfo(name = "price")
    private float price;

    @ColumnInfo(name = "ratingBar")
    private float ratingBar;

    @ColumnInfo(name = "isFavourite")
    private boolean favourite = false;

    @TypeConverters(DateConverter.class)
    @ColumnInfo(name = "startDate")
    private Date startDate;

    @TypeConverters(DateConverter.class)
    @ColumnInfo(name = "finishDate")
    private Date finishDate;


    @ColumnInfo(name = "imageFilePath")
    private String imageFilePath;

    public InputData() {
    }

    public static InputData getDefaultInputData() {
        return new InputData("", "", "", 0, 0);
    }
    @Ignore
    public InputData(String tripName, String tripDestination, String tripType, float price, float ratingBar) {
        this.tripName = tripName;
        this.tripDestination = tripDestination;
        this.tripType = tripType;
        this.price = price;
        this.ratingBar = ratingBar;
    }
    @Ignore
    public InputData(String tripName, String tripDestination, String tripType, float price, float ratingBar, boolean favourite, Date startDate, Date finishDate, String imageFilePath) {
        this.tripName = tripName;
        this.tripDestination = tripDestination;
        this.tripType = tripType;
        this.price = price;
        this.ratingBar = ratingBar;
        this.favourite = favourite;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.imageFilePath = imageFilePath;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
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

    public String getImageFilePath() {
        return imageFilePath;
    }

    public void setImageFilePath(String imageFilePath) {
        this.imageFilePath = imageFilePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
