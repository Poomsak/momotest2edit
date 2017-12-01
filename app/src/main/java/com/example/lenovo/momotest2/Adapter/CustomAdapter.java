package com.example.lenovo.momotest2.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lenovo.momotest2.MainActivity;
import com.example.lenovo.momotest2.Model.Modeldetail;
import com.example.lenovo.momotest2.R;

import java.util.List;

/**
 * Created by Lenovo on 16-11-2017.
 */

public class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Modeldetail> modeldetails;
    private Context context;

    public CustomAdapter(List<Modeldetail> modeldetails, Context context) {
        this.modeldetails = modeldetails;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_detail_lot,parent,false);
        return new ViewItemHoder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        Modeldetail modellist = modeldetails.get(position);

        ViewItemHoder viewitem = (ViewItemHoder) holder;
        viewitem.textnumber.setText(modellist.getNumber());
        viewitem.texttop.setText(modellist.getTop());
        viewitem.textbutton.setText(modellist.getButton());
        viewitem.texttoad.setText(modellist.getToad());

        if (modellist.getFocus() =="1"&&modellist.getNumber().toString().length()>2){

            viewitem.Liner_Top.setBackgroundColor(Color.BLUE);
            viewitem.Liner_Toad.setBackgroundColor(Color.BLUE);

        }else if (modellist.getFocus()=="1"&&modellist.getNumber().toString().length()<=2){

            viewitem.Liner_Top.setBackgroundColor(Color.BLUE);
            viewitem.Liner_Lower.setBackgroundColor(Color.BLUE);
        }
        if (modellist.getFocus()=="1"&&modellist.getTop().toString().length()>0){
            viewitem.Liner_Top.setBackgroundColor(Color.WHITE);
        }
        if (modellist.getFocus()=="1"&&modellist.getToad().toString().length()>0){
            viewitem.Liner_Toad.setBackgroundColor(Color.WHITE);
        }
        if (modellist.getFocus()=="1"&&modellist.getButton().toString().length()>0){
            viewitem.Liner_Lower.setBackgroundColor(Color.WHITE);
        }

        if (!modellist.isCheck_top()){
            viewitem.Liner_Top.setBackgroundColor(Color.WHITE);
        }
         if (!modellist.isCheck_button()&&modellist.getTop().toString().length()>0&&!modellist.isCheck_toad()){
            viewitem.Liner_Lower.setBackgroundColor(Color.WHITE);
        }
         if (!modellist.isCheck_toad()&&modellist.getTop().toString().length()>0&&!modellist.isCheck_button()){
            viewitem.Liner_Toad.setBackgroundColor(Color.WHITE);
        }


    }

    @Override
    public int getItemCount() {
        return modeldetails.size();
    }

    static class ViewItemHoder extends RecyclerView.ViewHolder {

        private TextView textnumber,texttop,textbutton,texttoad;
        private LinearLayout Liner_Top,Liner_Toad,Liner_Lower;
        public ViewItemHoder(View itemView) {
            super(itemView);
            textnumber = itemView.findViewById(R.id.textnumber);
            texttop = itemView.findViewById(R.id.texttop);
            textbutton = itemView.findViewById(R.id.textbutton);
            texttoad = itemView.findViewById(R.id.texttoad);

            Liner_Top = itemView.findViewById(R.id.Liner_Top);
            Liner_Toad = itemView.findViewById(R.id.Liner_Toad);
            Liner_Lower = itemView.findViewById(R.id.Liner_Lower);
        }
    }
}
