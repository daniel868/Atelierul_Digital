package com.example.atelieruldigitalfinalproject.DataPackage.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atelieruldigitalfinalproject.DataPackage.InputData;
import com.example.atelieruldigitalfinalproject.R;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.InfoAdapterHolder> {
    private List<InputData> inputDataList = new ArrayList<>();

    @NonNull
    @Override
    public InfoAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutInflater = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_info_recycle_view, parent, false);
        return new InfoAdapterHolder(layoutInflater);
    }

    @Override
    public void onBindViewHolder(@NonNull InfoAdapterHolder holder, int position) {
        holder.bindData(inputDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return inputDataList.size();
    }

    public void setInputData(List<InputData> inputDataList) {
        this.inputDataList = inputDataList;
        notifyDataSetChanged();
    }

    public class InfoAdapterHolder extends RecyclerView.ViewHolder {
        private ImageView cardImageView;
        private TextView tripTitle, tripDestination;

        public InfoAdapterHolder(@NonNull View itemView) {
            super(itemView);
            cardImageView = itemView.findViewById(R.id.cardItemImageView);
            tripTitle = itemView.findViewById(R.id.cardItemTripTitleTxtView);
            tripDestination = itemView.findViewById(R.id.cardItemTripDestinationTxtView);
        }

        public void bindData(InputData inputData) {
            cardImageView.setImageBitmap(inputData.getImageBitmap());
            tripTitle.setText(inputData.getTripName());
            tripDestination.setText(inputData.getTripDestination());
        }
    }

}
