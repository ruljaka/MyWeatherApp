package com.ruslangrigoriev.weather.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ruslangrigoriev.weather.R;


public class BackFragment extends Fragment {
    public static final String BACK_TAG = "BackFragment";

    private ImageView backIV;
    private ConstraintLayout backCL;

    public BackFragment() {
        // Required empty public constructor
    }

    public static BackFragment newInstance() {
        return new BackFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_back, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        backIV = view.findViewById(R.id.back_image);
        backCL = view.findViewById(R.id.back_CL);

    }

    public void setDay(Context context){
        backCL.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_day_background));
        backIV.setImageResource(R.drawable.ic_day_image);
    }

    public void setNight(Context context){
        backCL.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_night_background));
        backIV.setImageResource(R.drawable.ic_nigh_timage);
    }
}