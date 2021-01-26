package com.example.atelierul_digital.week_7;

import android.graphics.Movie;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.atelierul_digital.R;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkActivity extends AppCompatActivity {
    private static final String TAG = "NetworkActivity";
    private MovieService movieService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);

        movieService = NetworkClass.getInstance().create(MovieService.class);

    }

    public void topRatedMovies(View view) {
        movieService.getTopRatedMovies(1).enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                handleMovieResp(response);
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Log.w(TAG, "onFailure: ", t);
            }
        });
    }

    public void ButtonUpcomingMovies(View view) {
        movieService.getUpcomingMovies(1).enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                handleMovieResp(response);
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Log.w(TAG, "onFailure: ", t);
            }
        });
    }

    public void ButtonPLayingMovies(View view) {
        movieService.getNowPlayingMovies(1).enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                handleMovieResp(response);
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Log.w(TAG, "onFailure: ", t);
            }
        });
    }

    private void executeAll() {

    }

    public void executeAll(View view) {
        new ExecuteAll(movieService).execute(1);
    }

    private static class ExecuteAll extends AsyncTask<Integer, Void, Boolean> {
        private final MovieService movieService;

        public ExecuteAll(@NonNull MovieService movieService) {
            this.movieService = movieService;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(TAG, "onPreExecute: called");
        }

        @Override
        protected Boolean doInBackground(Integer... integers) {
            Integer page = integers[0];
            if (page == null) {
                page = 1;
            }
            try {
                if (isCancelled()) return false;
                handleMovieResp(movieService.getTopRatedMovies(page).execute());
                if (isCancelled()) return false;
                handleMovieResp(movieService.getNowPlayingMovies(page).execute());
                if (isCancelled()) return false;
                handleMovieResp(movieService.getUpcomingMovies(page).execute());
            } catch (IOException e) {
                Log.e(TAG, "doInBackground: ", e);
                return false;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            Log.d(TAG, "onPostExecute: called");
            super.onPostExecute(aBoolean);
            Log.d(TAG, "onPostExecute() called with: aBoolean = [" + aBoolean + "]");
        }
    }


    private static void handleMovieResp(@NonNull Response<Example> resultResponse) {
        Example movieResult = resultResponse.body();
        if (movieResult == null) {
            return;
        } else {
            for (Result rs : movieResult.getResults()) {
                Log.d(TAG, "handleMovieResp: " + rs);
            }
        }
    }


}
