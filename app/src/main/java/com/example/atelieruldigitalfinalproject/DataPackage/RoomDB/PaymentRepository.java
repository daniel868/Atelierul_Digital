package com.example.atelieruldigitalfinalproject.DataPackage.RoomDB;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.atelieruldigitalfinalproject.DataPackage.RoomDB.Entity.InputData;
import com.example.atelieruldigitalfinalproject.DataPackage.RoomDB.Entity.Payment;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PaymentRepository {
    private static final String TAG = "PaymentRepository";

    private Context context;
    private AppDatabase appDatabase;

    public PaymentRepository(Context context) {
        this.context = context;
        appDatabase = AppDatabase.getInstance(context);
    }

    public void insert(Payment payment) {
        {
            Completable.fromAction(() -> {
                appDatabase.paymentDao().insert(payment);
            }).observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {
                            Log.d(TAG, "onSubscribe: Called Insert");
                        }

                        @Override
                        public void onComplete() {
                            Log.d(TAG, "onComplete:Inserted " + payment.getTripName());
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            Log.e(TAG, "onError: ", e);
                        }
                    });
        }
    }

    public void delete(Payment payment) {
        Completable.fromAction(() -> {
            appDatabase.paymentDao().delete(payment);
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: Called Deleted");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete:Deleted " + payment.getTripName());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError: ", e);
                    }
                });
    }

    public void deleteAll() {
        Completable.fromAction(() -> {
            appDatabase.paymentDao().deleteAllPayments();
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

    public void update(Payment payment) {
        Completable.fromAction(() -> {
            appDatabase.paymentDao().update(payment);
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: Called Updated");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete:Updated " + payment.getId());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError: ", e);
                    }
                });
    }

    public LiveData<List<Payment>> getAllPayments() {
        return appDatabase.paymentDao().getAllTrips();
    }

}
