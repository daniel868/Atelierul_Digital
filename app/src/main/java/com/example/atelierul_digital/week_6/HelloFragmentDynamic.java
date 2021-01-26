package com.example.atelierul_digital.week_6;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.atelierul_digital.R;

public class HelloFragmentDynamic  extends Fragment {
    private static final String TAG = "HelloFragmentDynamic";
    private TheListener listener;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dynamic_fragment_layout,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
            Bundle bundle = getArguments();
            if (bundle!=null){
                Log.d(TAG, "onViewCreated: "+bundle.getString("MESSAGE"));
            }
            if (listener!=null){
                listener.doSomething("Done listener things ");
            }else {
                Log.d(TAG, "onViewCreated: Error");
            }
    }
    void doSomething(Context view){
        Toast.makeText(getContext(), "Toast from dynamic fragment", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (getActivity() instanceof TheListener){
            listener = (TheListener)getActivity();
        }
    }
}
