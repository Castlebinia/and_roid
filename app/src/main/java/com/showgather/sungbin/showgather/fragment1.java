package com.showgather.sungbin.showgather;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class fragment1 extends Fragment {
    final private static String DAUM_API_KEY = "d53fec04369ea0b054c1b221253f2cca";
    double latitude;    //내 위도(초기 gps기준)
    double longitude;   //내 경도(초기 gps기준)

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment1, container, false);

    }

}
