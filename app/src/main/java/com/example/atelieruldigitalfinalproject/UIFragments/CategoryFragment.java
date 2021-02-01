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

import com.example.atelieruldigitalfinalproject.DataPackage.Adapters.CategoryAdapter;
import com.example.atelieruldigitalfinalproject.DataPackage.CategoryActivity;
import com.example.atelieruldigitalfinalproject.DataPackage.Listeners.OnClickListener;
import com.example.atelieruldigitalfinalproject.R;

public class CategoryFragment extends Fragment implements OnClickListener {
    public static final String KEY = "TRIP_KEY";
    private RecyclerView categoryRecycleView;
    private CategoryAdapter adapter;

    public CategoryFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        categoryRecycleView = view.findViewById(R.id.trip_categoryRV);
        adapter = new CategoryAdapter(getContext(), this::onClick);
        categoryRecycleView.setAdapter(adapter);
        categoryRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    @Override
    public void onClick(int id) {
        Intent intent = new Intent(getContext(), CategoryActivity.class);
        if (id == 0) {
            intent.putExtra(KEY, getString(R.string.mountains_option));
        } else if (id == 1) {
            intent.putExtra(KEY, getString(R.string.sea_side_option));
        } else if (id == 2) {
            intent.putExtra(KEY, getString(R.string.city_break_option));
        }
        startActivity(intent);
    }
}
