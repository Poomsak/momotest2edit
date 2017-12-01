package com.example.lenovo.momotest2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Lenovo on 24-11-2017.
 */

public class PageNumber extends Fragment {
    public PageNumber(){}
    public static PageNumber newInstance(){
        PageNumber pageNumber = new PageNumber();
        return pageNumber;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.layout_number,container,false);

        return view;
    }
}
