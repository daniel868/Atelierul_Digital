package com.example.atelieruldigitalfinalproject.DataPackage.RoomDB.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.atelieruldigitalfinalproject.DataPackage.RoomDB.Entity.InputData;

import java.util.List;

@Dao
public interface TripDao {

    @Insert
    void insertTrip(InputData inputData);

    @Insert
    void insertAll(InputData... inputData);

    @Delete
    void delete(InputData inputData);

    @Query("DELETE FROM trip_table")
    void deleteAll();

    @Update
    void update(InputData inputData);

    @Query("SELECT * FROM trip_table")
    LiveData<List<InputData>> getALLTrips();

    @Query("SELECT * FROM trip_table WHERE isFavourite")
    LiveData<List<InputData>> getFavouriteTrips();

    @Query("SELECT * FROM trip_table WHERE id=:id")
    LiveData<InputData> getTripById(int id);

    @Query("SELECt * FROM trip_table WHERE tripType=:type and id!=:id")
    LiveData<List<InputData>> geTripByType(String type, int id);

    @Query("SELECt * FROM trip_table WHERE tripType=:type")
    LiveData<List<InputData>> geTripByName(String type);
}
