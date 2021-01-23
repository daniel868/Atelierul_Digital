package com.example.atelieruldigitalfinalproject.DataPackage.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atelieruldigitalfinalproject.DataPackage.InputData;
import com.example.atelieruldigitalfinalproject.DataPackage.Listeners.MyLongListener;
import com.example.atelieruldigitalfinalproject.DataPackage.Listeners.OnClickListener;
import com.example.atelieruldigitalfinalproject.DataPackage.Listeners.OnTogglePressListener;
import com.example.atelieruldigitalfinalproject.R;

import java.util.ArrayList;
import java.util.List;

public class TripItemAdapter extends RecyclerView.Adapter<TripItemAdapter.TripItemHolder> {
    private static final String TAG = "TripItemAdapter";


    private List<InputData> dataList = new ArrayList<>();
    private MyLongListener listener;
    private OnTogglePressListener toggleListener;
    private OnClickListener clickListener;

    public TripItemAdapter() {
    }

    public TripItemAdapter(MyLongListener listener, OnTogglePressListener toggleListener, OnClickListener clickListener) {
        this.listener = listener;
        this.toggleListener = toggleListener;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public TripItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view_trip, parent, false);
        return new TripItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TripItemHolder holder, int position) {
        holder.bindData(dataList.get(position), listener, toggleListener, this,clickListener);
    }

    public void setDataList(List<InputData> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public class TripItemHolder extends RecyclerView.ViewHolder {
        private ImageView itemImageView;
        private RatingBar itemRatingBar;
        private TextView tripName, tripDestination, tripPrice;
        private ToggleButton favoriteButton;
        private ImageButton moreImageButton;

        public TripItemHolder(@NonNull View itemView) {
            super(itemView);
            itemImageView = itemView.findViewById(R.id.item_imageView);
            itemRatingBar = itemView.findViewById(R.id.item_rating_star);
            tripName = itemView.findViewById(R.id.item_trip_here);
            tripDestination = itemView.findViewById(R.id.item_destination_here);
            tripPrice = itemView.findViewById(R.id.itemTripPrice);
            favoriteButton = itemView.findViewById(R.id.favoriteImageButton);

        }

        public void bindData(InputData inputData, MyLongListener listener, OnTogglePressListener toggleListener,
                             TripItemAdapter tripItemAdapter, OnClickListener clickListener) {

            itemImageView.setImageBitmap(inputData.getImageBitmap());
            itemRatingBar.setRating(inputData.getRatingBar());
            tripName.setText(inputData.getTripName());
            tripDestination.setText(inputData.getTripDestination());
            tripPrice.setText("$" + inputData.getPrice());
            favoriteButton.setBackgroundResource(inputData.isFavourite() ? R.drawable.ic_baseline_favorite_24 : R.drawable.ic_baseline_black_24);

            itemView.setOnLongClickListener(v -> {
                listener.onLongClick(inputData.getId());
                return true;
            });

            favoriteButton.setOnClickListener(v -> {
                toggleListener.onTogglePress(inputData, tripItemAdapter);
            });

            itemView.setOnClickListener(v->{
                clickListener.onClick(inputData.getId());
            });
        }
    }
}
