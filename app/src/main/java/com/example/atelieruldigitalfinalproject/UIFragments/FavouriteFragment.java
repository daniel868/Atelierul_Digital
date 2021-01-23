package com.example.atelieruldigitalfinalproject.UIFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atelieruldigitalfinalproject.DataPackage.Adapters.FavouriteItemAdapter;
import com.example.atelieruldigitalfinalproject.R;
import com.example.atelieruldigitalfinalproject.Utils;

public class FavouriteFragment extends Fragment {
    private RecyclerView favouriteRecycleView;

    public FavouriteFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);
        favouriteRecycleView = view.findViewById(R.id.favouriteRecycleView);
        initRecycleView();

        return view;
    }

    private void initRecycleView() {
        favouriteRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        FavouriteItemAdapter adapter = new FavouriteItemAdapter();
        favouriteRecycleView.setAdapter(adapter);
        adapter.setInputData(Utils.getFavouriteItems(getContext()));
    }


}
