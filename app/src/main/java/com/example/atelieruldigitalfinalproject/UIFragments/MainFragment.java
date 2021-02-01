package com.example.atelieruldigitalfinalproject.UIFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.atelieruldigitalfinalproject.AddEditActivity;
import com.example.atelieruldigitalfinalproject.DataPackage.Listeners.OnDeleteButtonClick;
import com.example.atelieruldigitalfinalproject.DataPackage.RoomDB.Entity.InputData;
import com.example.atelieruldigitalfinalproject.DataPackage.Listeners.MyLongListener;
import com.example.atelieruldigitalfinalproject.DataPackage.Listeners.OnClickListener;
import com.example.atelieruldigitalfinalproject.DataPackage.Listeners.OnTogglePressListener;
import com.example.atelieruldigitalfinalproject.DataPackage.Adapters.TripItemAdapter;
import com.example.atelieruldigitalfinalproject.DataPackage.RoomDB.TripRepository;
import com.example.atelieruldigitalfinalproject.DetailActivity;
import com.example.atelieruldigitalfinalproject.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static android.app.Activity.RESULT_OK;

public class MainFragment extends Fragment implements MyLongListener, OnTogglePressListener, OnClickListener, OnDeleteButtonClick {
    private static final String TAG = "MainFragment";
    public static final int ADD_TRIP_ID = 101;
    public static final int EDIT_TRIP_ID = 102;
    public static final String ID_POST = "ID_POST";

    private FloatingActionButton floatingActionButton;
    private RecyclerView tripRecycleView;

    private TripRepository repository;


    public MainFragment() {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        floatingActionButton = view.findViewById(R.id.add_edit_fab);
        tripRecycleView = view.findViewById(R.id.recycleView);

        setRecycleViewData();

        floatingActionButton.setOnClickListener(view1 -> showAddEditActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    private void showAddEditActivity() {
        Intent intent = new Intent(getContext(), AddEditActivity.class);
        startActivityForResult(intent, ADD_TRIP_ID);
    }

    private void setRecycleViewData() {
        TripItemAdapter tripItemAdapter = new TripItemAdapter(this, this, this::onClick, this);
        tripRecycleView.setAdapter(tripItemAdapter);
        tripRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));

        repository = new TripRepository(getContext());
        repository.getAllTrips().observe(getViewLifecycleOwner(), tripItemAdapter::setDataList);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_TRIP_ID && resultCode == RESULT_OK) {
            Toast.makeText(getContext(), "Post Added successfully", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_TRIP_ID && resultCode == RESULT_OK) {
            Toast.makeText(getContext(), "Updated post successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Error updated or added trip", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onLongClick(int id) {

        Intent intent = new Intent(getContext(), AddEditActivity.class);
        intent.putExtra(ID_POST, id);
        startActivityForResult(intent, EDIT_TRIP_ID);

    }

    @Override
    public void onTogglePress(InputData inputData, TripItemAdapter tripItemAdapter) {
        inputData.setFavourite(!inputData.isFavourite());

        repository.update(inputData);
    }


    @Override
    public void onClick(int id) {
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra(ID_POST, id);
        startActivity(intent);
    }

    @Override
    public void delete(InputData inputData) {
        repository.delete(inputData);
    }
}