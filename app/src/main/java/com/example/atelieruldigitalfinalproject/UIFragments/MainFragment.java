package com.example.atelieruldigitalfinalproject.UIFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.atelieruldigitalfinalproject.AddEditActivity;
import com.example.atelieruldigitalfinalproject.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import javax.xml.transform.Result;

import static android.app.Activity.RESULT_OK;

public class MainFragment extends Fragment {

    private FloatingActionButton floatingActionButton;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        floatingActionButton = view.findViewById(R.id.add_edit_fab);

        floatingActionButton.setOnClickListener(view1 -> showAddEditActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    private void showAddEditActivity() {
        Intent intent = new Intent(getContext(), AddEditActivity.class);
        startActivityForResult(intent, 101);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK) {
            Toast.makeText(getContext(), "Positive feedback", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Negative feedback", Toast.LENGTH_SHORT).show();
        }
    }
}