package com.example.atelieruldigitalfinalproject.DataPackage.RoomDB;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.atelieruldigitalfinalproject.DataPackage.RoomDB.Entity.InputData;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TripRepository {
    private static final String TAG = "Repository";

    private Context context;
    private AppDatabase appDatabase;

    public TripRepository(Context context) {
        this.context = context;
        appDatabase = AppDatabase.getInstance(context);
    }

    public void insert(InputData inputData) {
        Completable.fromAction(() -> {
            appDatabase.tripDao().insertTrip(inputData);
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: Called Insert");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete:Inserted " + inputData.getTripName());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError: ", e);
                    }
                });
    }

    public void insertAll(InputData... inputData) {
        Completable.fromAction(() -> {
            appDatabase.tripDao().insertAll(inputData);
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: Called Insert All");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete:Inserted All");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError: ", e);
                    }
                });
    }

    public void delete(InputData inputData) {
        Completable.fromAction(() -> {
            appDatabase.tripDao().delete(inputData);
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: Called Deleted");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete:Deleted " + inputData.getTripName());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError: ", e);
                    }
                });
    }

    public void deleteAll() {
        Completable.fromAction(() -> {
            appDatabase.tripDao().deleteAll();
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: Called Deleted All");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete:Deleted All");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError: ", e);
                    }
                });
    }

    public void update(InputData inputData) {
        Completable.fromAction(() -> {
            appDatabase.tripDao().update(inputData);
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: Called Updated");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete:Updated " + inputData.getId());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError: ", e);
                    }
                });
    }

    public LiveData<List<InputData>> getAllTrips() {
        return appDatabase.tripDao().getALLTrips();
    }

    public LiveData<List<InputData>> getFavouriteTrips() {
        return appDatabase.tripDao().getFavouriteTrips();
    }

    public LiveData<InputData> getTripById(int id) {
        return appDatabase.tripDao().getTripById(id);
    }

    public LiveData<List<InputData>> getTripByType(String type, int id) {
        return appDatabase.tripDao().geTripByType(type,id);
    }

    public LiveData<List<InputData>> getTripByName(String type) {
        return appDatabase.tripDao().geTripByName(type);
    }


}
