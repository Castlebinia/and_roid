package com.showgather.sungbin.showgather;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


public class Review_fragment extends android.support.v4.app.Fragment {
    public Review_fragment(){

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RelativeLayout relativeLayout =(RelativeLayout)inflater.inflate(R.layout.res_review_fragment,container,false);
        return relativeLayout;
    }

}


