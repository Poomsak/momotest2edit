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

public class PageMenu extends Fragment implements View.OnClickListener{

    private TextView textMenu1,textMenu2,textMenu3;
    public PageMenu(){}
    public static PageMenu newInstance(){
        PageMenu pageMenu = new PageMenu();
        return pageMenu;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.layout_menu,container,false);
        textMenu3 = (TextView) view.findViewById(R.id.textMenu3);
        textMenu2 = (TextView) view.findViewById(R.id.textMenu2);
        textMenu1 = (TextView) view.findViewById(R.id.textMenu1);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.textMenu1:

                break;
            case R.id.textMenu2:

                break;
            case R.id.textMenu3:

                break;
        }
    }
}
