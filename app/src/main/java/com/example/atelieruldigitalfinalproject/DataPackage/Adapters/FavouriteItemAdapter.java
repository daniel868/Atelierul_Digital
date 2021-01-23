package com.example.atelieruldigitalfinalproject.DataPackage.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atelieruldigitalfinalproject.DataPackage.InputData;
import com.example.atelieruldigitalfinalproject.R;

import java.util.ArrayList;
import java.util.List;

public class FavouriteItemAdapter extends RecyclerView.Adapter<FavouriteItemAdapter.FavouriteItemHolder> {
    private List<InputData> inputData = new ArrayList<>();

    @NonNull
    @Override
    public FavouriteItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view_trip,parent,false);
        return new FavouriteItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteItemHolder holder, int position) {
        holder.bindData(inputData.get(position));
    }

    @Override
    public int getItemCount() {
        return inputData.size();
    }

    public void setInputData(List<InputData> inputData) {
        for (InputData currentInputData : inputData) {
            if (currentInputData.isFavourite()){
                this.inputData.add(currentInputData);
            }
        }
        notifyDataSetChanged();
    }

    public class FavouriteItemHolder extends RecyclerView.ViewHolder {
        private ImageView itemImageView;
        private RatingBar itemRatingBar;
        private TextView tripName, tripDestination, tripPrice;
        private ToggleButton favoriteButton;
        public FavouriteItemHolder(@NonNull View itemView) {
            super(itemView);
            itemImageView = itemView.findViewById(R.id.item_imageView);
            itemRatingBar = itemView.findViewById(R.id.item_rating_star);
            tripName = itemView.findViewById(R.id.item_trip_here);
            tripDestination = itemView.findViewById(R.id.item_destination_here);
            tripPrice = itemView.findViewById(R.id.itemTripPrice);
            favoriteButton = itemView.findViewById(R.id.favoriteImageButton);
        }
        public void bindData(InputData inputData){
            itemImageView.setImageBitmap(inputData.getImageBitmap());
            itemRatingBar.setRating(inputData.getRatingBar());
            tripName.setText(inputData.getTripName());
            tripDestination.setText(inputData.getTripDestination());
            tripPrice.setText("$" + inputData.getPrice());
        }
    }
}
