package com.example.atelieruldigitalfinalproject.DataPackage.RoomDB.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.example.atelieruldigitalfinalproject.DataPackage.RoomDB.Converters.DateConverter;

import java.util.Date;

@Entity(tableName = "payment_table")
public class Payment {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "payment_id")
    private int id;

    @ColumnInfo(name = "trip_name")
    private String tripName;

    @ColumnInfo(name = "trip_price")
    private float tripPrice;

    @TypeConverters(DateConverter.class)
    @ColumnInfo(name = "payment_date")
    private Date date;

    public Payment() {
    }

    @Ignore
    public Payment(String tripName, float tripPrice, Date date) {
        this.tripName = tripName;
        this.tripPrice = tripPrice;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public float getTripPrice() {
        return tripPrice;
    }

    public void setTripPrice(float tripPrice) {
        this.tripPrice = tripPrice;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
