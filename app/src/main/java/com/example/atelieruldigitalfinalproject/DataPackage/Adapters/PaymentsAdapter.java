package com.example.atelieruldigitalfinalproject.DataPackage.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atelieruldigitalfinalproject.DataPackage.RoomDB.Entity.Payment;
import com.example.atelieruldigitalfinalproject.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PaymentsAdapter extends RecyclerView.Adapter<PaymentsAdapter.PaymentsHolder> {
    private List<Payment> paymentList = new ArrayList<>();

    @NonNull
    @Override
    public PaymentsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_item_view, parent, false);
        return new PaymentsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentsHolder holder, int position) {
        holder.bindData(paymentList.get(position));
    }

    public void setPaymentList(List<Payment> paymentList) {
        this.paymentList = paymentList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return paymentList.size();
    }

    public class PaymentsHolder extends RecyclerView.ViewHolder {
        private TextView tripName, tripPrice, tripDate;

        public PaymentsHolder(@NonNull View itemView) {
            super(itemView);
            tripName = itemView.findViewById(R.id.payment_trip_name);
            tripPrice = itemView.findViewById(R.id.payment_total);
            tripDate = itemView.findViewById(R.id.payment_date);
        }

        public void bindData(Payment payment) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

            tripName.setText(payment.getTripName());
            tripPrice.setText("-$" + payment.getTripPrice());
            tripDate.setText(simpleDateFormat.format(payment.getDate()));
        }
    }
}
