package com.example.lenovo.momotest2;

import android.graphics.drawable.AnimationDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lenovo.momotest2.Adapter.CustomAdapter;
import com.example.lenovo.momotest2.Model.Modeldetail;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ArrayList<Modeldetail> list;
    private RecyclerView redetail;
    private GridLayoutManager gridLayoutManager;
    private CustomAdapter adapter;

    private TextView bt_zero,bt_nine,bt_eight,
            bt_seven,bt_six,bt_file,bt_four,bt_three,bt_two,bt_one;
    private TextView btn_edit,btn_cancel,btn_enter,btn_Nexto;
    private EditText edit_number;
    private TextView text_tital;
    private boolean Check_number = true,Check_numberTop = true,
            Check_numberlower = true,Check_numberToad = true;
    private ArrayList<String> listNumber;
    private int Nexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_number);
        getSupportActionBar().hide();

        itemView();
        edit_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                /*if(charSequence.length() <= 2){
                    Log.e("MainActivity", "Number "+charSequence.length());
                }else if (charSequence.length() >=2){
                    Log.e("MainActivity", "Number "+charSequence.length());
                }*/

            }

            @Override
            public void afterTextChanged(Editable s) {

                edit_number.removeTextChangedListener(this);

                if (Check_number){
                    edit_number.setFilters(new InputFilter[] {new InputFilter.LengthFilter(3)});
                }else {
                    edit_number.setFilters(new InputFilter[] {new InputFilter.LengthFilter(13)});
                }

                try {
                    String originalString = s.toString();

                    Long longval;
                    if (originalString.contains(",")) {
                        originalString = originalString.replaceAll(",", "");
                    }
                    longval = Long.parseLong(originalString);

                    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                    formatter.applyPattern("#,###,###,###");
                    String formattedString = formatter.format(longval);

                    //setting text after format to EditText
                    edit_number.setText(formattedString);
                    edit_number.setSelection(edit_number.getText().length());

                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                edit_number.addTextChangedListener(this);

            }
        });

    }

    private void itemView(){
        list = new ArrayList<>();
        listNumber = new ArrayList<>();

        edit_number = findViewById(R.id.edit_number);
        redetail = findViewById(R.id.recy_detail);

        btn_enter = findViewById(R.id.btn_enter);
        btn_edit = findViewById(R.id.btn_edit);
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_Nexto = findViewById(R.id.btn_Nexto);
        btn_Nexto.setText("บันทึก");

        text_tital = findViewById(R.id.text_tital);
        text_tital.setText("เลข");
        bt_zero = findViewById(R.id.bt_zero);
        bt_nine = findViewById(R.id.bt_nine);
        bt_eight = findViewById(R.id.bt_eight);
        bt_seven = findViewById(R.id.bt_seven);
        bt_six = findViewById(R.id.bt_six);
        bt_file = findViewById(R.id.bt_file);
        bt_four = findViewById(R.id.bt_four);
        bt_three = findViewById(R.id.bt_three);
        bt_two = findViewById(R.id.bt_two);
        bt_one = findViewById(R.id.bt_one);

        bt_zero.setOnClickListener(this);
        bt_nine.setOnClickListener(this);
        bt_eight.setOnClickListener(this);
        bt_seven.setOnClickListener(this);
        bt_six.setOnClickListener(this);
        bt_file.setOnClickListener(this);
        bt_four.setOnClickListener(this);
        bt_three.setOnClickListener(this);
        bt_two.setOnClickListener(this);
        bt_one.setOnClickListener(this);

        btn_edit.setOnClickListener(this);
        btn_enter.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        btn_Nexto.setOnClickListener(this);

        gridLayoutManager = new GridLayoutManager(MainActivity.this,1);
        adapter = new CustomAdapter(list,MainActivity.this);

        redetail.setAdapter(adapter);
        redetail.setLayoutManager(gridLayoutManager);
        redetail.setHasFixedSize(true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.btn_edit:
                Clear_Editext();
                break;
            case R.id.btn_enter:
                if (edit_number.length()>0){
                    setData();
                    redetail.smoothScrollToPosition(list.size());
                }
                break;
            case R.id.btn_Nexto:
                setNexto();
                break;

            case R.id.btn_cancel:
                Log.e("MainActivity", "Clear");
                Clear_Dataset();
                break;
            case R.id.bt_one:
                setNumber("1");
                break;
            case R.id.bt_two:
                setNumber("2");
                break;
            case R.id.bt_three:
                setNumber("3");
                break;
            case R.id.bt_four:
                setNumber("4");
                break;
            case R.id.bt_file:
                setNumber("5");
                break;
            case R.id.bt_six:
                setNumber("6");
                break;
            case R.id.bt_seven:
                setNumber("7");
                break;
            case R.id.bt_eight:
                setNumber("8");
                break;
            case R.id.bt_nine:
                setNumber("9");
                break;
            case R.id.bt_zero:
                setNumber("0");
                break;
        }

    }

    private void Clear_Editext(){

        edit_number.setText(null);
        edit_number.requestFocus();
        edit_number.setFocusable(true);
    }
    private void setNumber(String number){
        edit_number.setText(edit_number.getText()+number);
    }
    private void setData(){


        if (Check_number){
            Modeldetail modeldetail = new Modeldetail();
            modeldetail.setNumber(edit_number.getText().toString());
            modeldetail.setTop("");
            modeldetail.setButton("");
            modeldetail.setToad("");

            modeldetail.setFocus("1");
            modeldetail.setCheck_top(true);

            modeldetail.setCheck_button(true);
            modeldetail.setCheck_toad(true);

            list.add(modeldetail);
            adapter.notifyDataSetChanged();

            Check_number = false;
            Check_numberTop = true;
            Check_numberlower = true;
            Check_numberToad = true;

            text_tital.setText("บน");
            btn_Nexto.setText("ข้าม");
            if (edit_number.getText().length()>2){
                Check_numberlower = false;

            }else if (edit_number.getText().length()<=2){
                Check_numberToad = false;
            }
            Clear_Editext();
            //Check_numberTop = true;

        }else if (Check_numberTop){

            Modeldetail modeldetail = new Modeldetail();
            modeldetail.setNumber(list.get(list.size()-1).getNumber());
            modeldetail.setTop(edit_number.getText().toString());
            modeldetail.setButton("");
            modeldetail.setToad("");

            modeldetail.setFocus(list.get(list.size()-1).getFocus());

            modeldetail.setCheck_button(list.get(list.size()-1).isCheck_button());
            modeldetail.setCheck_toad(list.get(list.size()-1).isCheck_toad());

            list.set(list.size()-1,modeldetail);
            adapter.notifyDataSetChanged();
            Clear_Editext();
            if (Check_numberlower){
                text_tital.setText("ล่าง");
            }else {
                text_tital.setText("โต้ด");
            }

            Check_numberTop = false;


        }else if (Check_numberlower){

            Modeldetail modeldetail = new Modeldetail();
            modeldetail.setNumber(list.get(list.size()-1).getNumber());
            modeldetail.setTop(list.get(list.size()-1).getTop());
            modeldetail.setButton(edit_number.getText().toString());
            modeldetail.setToad("");

            modeldetail.setFocus(list.get(list.size()-1).getFocus());

            modeldetail.setCheck_button(list.get(list.size()-1).isCheck_button());
            modeldetail.setCheck_toad(list.get(list.size()-1).isCheck_toad());

            list.set(list.size()-1,modeldetail);
            adapter.notifyDataSetChanged();
            Clear_Editext();

            Check_numberlower = false;
            if (!Check_numberToad){
                Log.e("MainActivity", "END");
                text_tital.setText("เลข");
                btn_Nexto.setText("บันทึก");
                Check_number = true;
            }else {
                text_tital.setText("โต้ด");
            }

        }else if (Check_numberToad){

            Modeldetail modeldetail = new Modeldetail();
            modeldetail.setNumber(list.get(list.size()-1).getNumber());
            modeldetail.setTop(list.get(list.size()-1).getTop());
            modeldetail.setButton(list.get(list.size()-1).getButton());
            modeldetail.setToad(edit_number.getText().toString());

            modeldetail.setFocus(list.get(list.size()-1).getFocus());

            modeldetail.setCheck_button(list.get(list.size()-1).isCheck_button());
            modeldetail.setCheck_toad(list.get(list.size()-1).isCheck_toad());

            list.set(list.size()-1,modeldetail);
            adapter.notifyDataSetChanged();
            Clear_Editext();
            Check_numberToad = false;
            if (!Check_numberlower){
                Log.e("MainActivity", "END");
                text_tital.setText("เลข");
                btn_Nexto.setText("บันทึก");
                Check_number = true;
            }
        }else {

            Log.e("MainActivity", "out");
        }

    }
    private void Clear_Dataset(){

        if (list.size()>0){
            list.clear();
            adapter.notifyDataSetChanged();
        }
        Clear_Editext();
        text_tital.setText("เลข");
        Check_number = true;

    }
    private void setNexto(){

        if (!Check_number&&Check_numberlower){

            Clear_Editext();
            Check_numberTop = false;
            text_tital.setText("ล่าง");

            Modeldetail modeldetail = new Modeldetail();
            modeldetail.setNumber(list.get(list.size()-1).getNumber());
            modeldetail.setTop(list.get(list.size()-1).getTop());
            modeldetail.setButton(list.get(list.size()-1).getButton());
            modeldetail.setToad(list.get(list.size()-1).getToad());
            modeldetail.setFocus(list.get(list.size()-1).getFocus());

            if (!Check_numberToad&&list.get(list.size()-1).getTop().length()>0){
                Log.e("MainActivity", "END lower");
                text_tital.setText("เลข");
                btn_Nexto.setText("บันทึก");
                Check_number = true;

            }
            modeldetail.setCheck_button(false);
            list.set(list.size()-1,modeldetail);
            adapter.notifyDataSetChanged();


        }else if (!Check_number&&Check_numberToad){

            Clear_Editext();
            Check_numberTop = false;
            text_tital.setText("โต้ด");

            Modeldetail modeldetail = new Modeldetail();
            modeldetail.setNumber(list.get(list.size()-1).getNumber());
            modeldetail.setTop(list.get(list.size()-1).getTop());
            modeldetail.setButton(list.get(list.size()-1).getButton());
            modeldetail.setToad(list.get(list.size()-1).getToad());
            modeldetail.setFocus(list.get(list.size()-1).getFocus());

            if (!Check_numberlower&&list.get(list.size()-1).getTop().length()>0){
                Log.e("MainActivity", "END Toad");
                text_tital.setText("เลข");
                btn_Nexto.setText("บันทึก");
                Check_number = true;

            }
            modeldetail.setCheck_toad(false);
            list.set(list.size()-1,modeldetail);
            adapter.notifyDataSetChanged();
        }
    }

}
