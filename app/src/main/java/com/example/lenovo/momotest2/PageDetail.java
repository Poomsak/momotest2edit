package com.example.lenovo.momotest2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Lenovo on 24-11-2017.
 */

public class PageDetail extends Fragment {
    public PageDetail(){}
    public static PageDetail newInstance(){
        PageDetail pageDetail = new PageDetail();
        return pageDetail;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.layout_number,container,false);

        return view;
    }
}