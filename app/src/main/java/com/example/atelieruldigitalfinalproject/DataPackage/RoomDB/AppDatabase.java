package com.example.atelieruldigitalfinalproject.DataPackage.RoomDB;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.atelieruldigitalfinalproject.DataPackage.RoomDB.Dao.PaymentDao;
import com.example.atelieruldigitalfinalproject.DataPackage.RoomDB.Dao.TripDao;
import com.example.atelieruldigitalfinalproject.DataPackage.RoomDB.Entity.InputData;
import com.example.atelieruldigitalfinalproject.DataPackage.RoomDB.Entity.Payment;

@Database(entities = {InputData.class, Payment.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "Trip_DATABASE";

    private static AppDatabase instance;

    public abstract TripDao tripDao();

    public abstract PaymentDao paymentDao();

    public static AppDatabase getInstance(@NonNull Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
