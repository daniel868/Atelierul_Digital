package com.example.atelierul_digital.week_6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.atelierul_digital.R;

public class HelloFragmentActivity extends AppCompatActivity {
    HelloFragmentDynamic helloFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_fragment_activty);

        Bundle bundle = new Bundle();
        bundle.putString("MESSAGE", "message_by_bundle");
        helloFragment = new HelloFragmentDynamic();
        helloFragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.helloFragmentContainer, helloFragment, "hello_fragment");
        transaction.commit();


    }

    public void doSmth(View view) {
        helloFragment.doSomething(view.getContext());
    }
}
