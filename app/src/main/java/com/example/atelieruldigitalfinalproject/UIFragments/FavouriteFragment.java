package com.example.atelieruldigitalfinalproject.UIFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atelieruldigitalfinalproject.AddEditActivity;
import com.example.atelieruldigitalfinalproject.DataPackage.Adapters.TripItemAdapter;
import com.example.atelieruldigitalfinalproject.DataPackage.Listeners.OnDeleteButtonClick;
import com.example.atelieruldigitalfinalproject.DataPackage.RoomDB.Entity.InputData;
import com.example.atelieruldigitalfinalproject.DataPackage.Listeners.MyLongListener;
import com.example.atelieruldigitalfinalproject.DataPackage.Listeners.OnClickListener;
import com.example.atelieruldigitalfinalproject.DataPackage.Listeners.OnTogglePressListener;
import com.example.atelieruldigitalfinalproject.DataPackage.RoomDB.TripRepository;
import com.example.atelieruldigitalfinalproject.DetailActivity;
import com.example.atelieruldigitalfinalproject.R;

public class FavouriteFragment extends Fragment implements OnClickListener, MyLongListener, OnTogglePressListener, OnDeleteButtonClick {
    private RecyclerView favouriteRecycleView;
    private TripItemAdapter tripItemAdapter;
    private TripRepository repository;


    public FavouriteFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favourite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        repository = new TripRepository(getContext());
        favouriteRecycleView = view.findViewById(R.id.favouriteRecycleView);
        initRecycleView();
    }

    private void initRecycleView() {
        favouriteRecycleView.setAdapter(tripItemAdapter);
        favouriteRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        tripItemAdapter = new TripItemAdapter(this, this, this, this);
        favouriteRecycleView.setAdapter(tripItemAdapter);
        repository.getFavouriteTrips().observe(getViewLifecycleOwner(), inputData -> {
            tripItemAdapter.setDataList(inputData);
        });

    }


    @Override
    public void onLongClick(int id) {
        Intent intent = new Intent(getContext(), AddEditActivity.class);
        intent.putExtra(MainFragment.ID_POST, id);
        startActivityForResult(intent, MainFragment.EDIT_TRIP_ID);
    }

    @Override
    public void onClick(int id) {
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra(MainFragment.ID_POST, id);
        startActivity(intent);
    }

    @Override
    public void onTogglePress(InputData inputData, TripItemAdapter tripItemAdapter) {
        inputData.setFavourite(!inputData.isFavourite());
        repository.update(inputData);
    }

    @Override
    public void delete(InputData inputData) {
        repository.delete(inputData);
    }
}
