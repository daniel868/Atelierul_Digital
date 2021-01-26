package com.example.atelierul_digital.week_7.UIFragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atelierul_digital.R;
import com.example.atelierul_digital.week_7.Result;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {
    private List<Result> data = new ArrayList<>();

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, int position) {
        holder.bindData(data.get(position));
    }

    public void setData(List<Result> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MovieHolder extends RecyclerView.ViewHolder {
        TextView titleMovie, releaseDate, voteAverage, voteCount;

        public MovieHolder(@NonNull View itemView) {
            super(itemView);
            titleMovie = itemView.findViewById(R.id.movieTitle);
            releaseDate = itemView.findViewById(R.id.releaseDate);
            voteAverage = itemView.findViewById(R.id.movieAverage);
            voteCount = itemView.findViewById(R.id.voteCount);
        }

        public void bindData(Result result) {
            titleMovie.setText(result.getTitle());
            releaseDate.setText(result.getReleaseDate());
            voteAverage.setText("Vote Average: " + result.getVoteAverage());
            voteCount.setText("Vote Count: " + result.getVoteCount());
        }
    }
}
