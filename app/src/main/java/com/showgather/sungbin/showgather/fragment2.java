package com.showgather.sungbin.showgather;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class fragment2 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup view = (ViewGroup)inflater.inflate(R.layout.fragment2,container,false);
        TextView textView = (TextView)view.findViewById(R.id.subway);
        textView.setText("영수야 연식아");
        return view;
    }
}
