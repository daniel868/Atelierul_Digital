package com.example.atelieruldigitalfinalproject.UIFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atelieruldigitalfinalproject.DataPackage.Adapters.PaymentsAdapter;
import com.example.atelieruldigitalfinalproject.DataPackage.RoomDB.Entity.Payment;
import com.example.atelieruldigitalfinalproject.DataPackage.RoomDB.PaymentRepository;
import com.example.atelieruldigitalfinalproject.R;

import java.util.List;

public class MyPaymentsFragment extends Fragment {
    private RecyclerView paymentRecycleView;
    private PaymentRepository paymentRepository;
    private PaymentsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_payments, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        paymentRepository = new PaymentRepository(getContext());

        adapter = new PaymentsAdapter();
        paymentRecycleView = view.findViewById(R.id.payments_recycleView);
        paymentRecycleView.setAdapter(adapter);
        paymentRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));

        paymentRepository.getAllPayments().observe(getViewLifecycleOwner(), payments -> {
            adapter.setPaymentList(payments);
        });
    }
}
