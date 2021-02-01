package com.example.atelieruldigitalfinalproject.DataPackage.Adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atelieruldigitalfinalproject.DataPackage.RoomDB.Entity.InputData;
import com.example.atelieruldigitalfinalproject.R;

import java.util.ArrayList;
import java.util.List;

public class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.TypeHolder> implements Filterable {
    private List<InputData> inputDataEntities = new ArrayList<>();
    private List<InputData> filteredEntities = new ArrayList<>();


    @NonNull
    @Override

    public TypeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view_trip, parent, false);
        return new TypeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TypeHolder holder, int position) {
        holder.bindData(filteredEntities.get(position));
    }

    public void setInputDataEntities(List<InputData> inputDataEntities) {
        this.inputDataEntities = inputDataEntities;
        this.filteredEntities = inputDataEntities;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return filteredEntities.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charStrings = constraint.toString();
                if (charStrings.isEmpty()) {
                    filteredEntities = inputDataEntities;
                } else {
                    List<InputData> tempFilter = new ArrayList<>();
                    for (InputData currentInputData : inputDataEntities) {
                        if (currentInputData.getTripName().toLowerCase().contains(charStrings.toLowerCase())) {
                            tempFilter.add(currentInputData);
                        }
                    }
                    filteredEntities = tempFilter;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredEntities;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredEntities = (List<InputData>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class TypeHolder extends RecyclerView.ViewHolder {
        private ImageView itemImageView;
        private RatingBar itemRatingBar;
        private TextView tripName, tripDestination, tripPrice;
        private ToggleButton favoriteButton;
        private ImageButton moreImageButton;


        public TypeHolder(@NonNull View itemView) {
            super(itemView);
            itemImageView = itemView.findViewById(R.id.item_imageView);
            itemRatingBar = itemView.findViewById(R.id.item_rating_star);
            tripName = itemView.findViewById(R.id.item_trip_here);
            tripDestination = itemView.findViewById(R.id.item_destination_here);
            tripPrice = itemView.findViewById(R.id.itemTripPrice);
            favoriteButton = itemView.findViewById(R.id.favoriteImageButton);
            moreImageButton = itemView.findViewById(R.id.deleteImageButton);
        }

        public void bindData(InputData inputData) {
            itemImageView.setImageURI(Uri.parse(inputData.getImageFilePath()));
            itemRatingBar.setRating(inputData.getRatingBar());
            tripName.setText(inputData.getTripName());
            tripDestination.setText(inputData.getTripDestination());
            tripPrice.setText("$" + inputData.getPrice());
            favoriteButton.setBackgroundResource(inputData.isFavourite() ? R.drawable.ic_baseline_favorite_24 : R.drawable.ic_baseline_black_24);
        }
    }
}
