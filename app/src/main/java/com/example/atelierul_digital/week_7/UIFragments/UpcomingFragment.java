package com.example.atelierul_digital.week_7.UIFragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atelierul_digital.R;
import com.example.atelierul_digital.week_7.Example;
import com.example.atelierul_digital.week_7.MovieService;
import com.example.atelierul_digital.week_7.NetworkClass;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpcomingFragment extends Fragment {
    private static final String TAG = "UpcomingFragment";

    private RecyclerView recyclerView;
    private MovieAdapter adapter;

    private MovieService movieService;

    public UpcomingFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_upcoming, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.upcoming_recycleView);
        adapter = new MovieAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        movieService = NetworkClass.getInstance().create(MovieService.class);
        fetchData();
    }

    private void fetchData() {
        movieService.getUpcomingMovies(1).enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                setMovieData(response);
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    private void setMovieData(@NonNull Response<Example> response) {
        if (response != null) {
            adapter.setData(response.body().getResults());
        } else {
            Log.d(TAG, "setMovieData: Problem fetching data");
        }
    }
}
