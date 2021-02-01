package com.example.atelieruldigitalfinalproject.DataPackage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atelieruldigitalfinalproject.DataPackage.Adapters.CategoryAdapter;
import com.example.atelieruldigitalfinalproject.DataPackage.Adapters.TripItemAdapter;
import com.example.atelieruldigitalfinalproject.DataPackage.Adapters.TypeAdapter;
import com.example.atelieruldigitalfinalproject.DataPackage.RoomDB.Entity.InputData;
import com.example.atelieruldigitalfinalproject.DataPackage.RoomDB.Repository;
import com.example.atelieruldigitalfinalproject.R;
import com.example.atelieruldigitalfinalproject.UIFragments.CategoryFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.jakewharton.rxbinding4.widget.RxTextView;
import com.jakewharton.rxbinding4.widget.TextViewTextChangeEvent;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DefaultObserver;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CategoryActivity extends AppCompatActivity {
    private static final String TAG = "CategoryActivity";
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private String tripType;
    private Repository repository;


    private RecyclerView recyclerView;
    private TypeAdapter adapter;
    private TextInputEditText searchBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        recyclerView = findViewById(R.id.activity_categoryRV);
        adapter = new TypeAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchBar = findViewById(R.id.search_bar);

        repository = new Repository(this);
        handleIntent();

        Disposable disposable = RxTextView.textChangeEvents(searchBar).skipInitialValue()
                .debounce(300, TimeUnit.MICROSECONDS)
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<TextViewTextChangeEvent>() {
                    @Override
                    public void onNext(@NonNull TextViewTextChangeEvent textViewTextChangeEvent) {
                        adapter.getFilter().filter(textViewTextChangeEvent.getText());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError: ", e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        compositeDisposable.add(disposable);
    }

    private void handleIntent() {
        Intent intent = getIntent();
        if (intent != null) {

            tripType = intent.getExtras().getString(CategoryFragment.KEY);
            repository.getTripByName(tripType).observe(this, inputData -> {
                adapter.setInputDataEntities(inputData);
            });
        }
    }

    private void implementSearchBar() {
        RxTextView.textChangeEvents(searchBar).skipInitialValue()
                .debounce(300, TimeUnit.MICROSECONDS)
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DefaultObserver<TextViewTextChangeEvent>() {
                    @Override
                    public void onNext(@NonNull TextViewTextChangeEvent textViewTextChangeEvent) {
                        adapter.getFilter().filter(textViewTextChangeEvent.getText());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError: ", e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}
