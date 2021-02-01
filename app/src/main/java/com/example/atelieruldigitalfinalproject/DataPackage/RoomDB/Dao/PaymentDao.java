package com.example.atelieruldigitalfinalproject.DataPackage.RoomDB.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.atelieruldigitalfinalproject.DataPackage.RoomDB.Entity.Payment;

import java.util.List;

@Dao
public interface PaymentDao {

    @Insert
    void insert(Payment payment);

    @Update
    void update(Payment payment);

    @Delete
    void delete(Payment payment);

    @Query("DELETE FROM payment_table ")
    void deleteAllPayments();

    @Query("SELECT * FROM payment_table")
    LiveData<List<Payment>> getAllTrips();


}
