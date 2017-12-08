package com.example.lenovo.momotest2.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.momotest2.Adapter.CustomAdapterDetail;
import com.example.lenovo.momotest2.Adapter.CustomAdapterMain;
import com.example.lenovo.momotest2.FormatHttpPostOkHttp.BasicNameValusPostOkHttp;
import com.example.lenovo.momotest2.FormatHttpPostOkHttp.FromHttpPostOkHttp;
import com.example.lenovo.momotest2.Manager.AllCommand;
import com.example.lenovo.momotest2.Model.Modeldetail;
import com.example.lenovo.momotest2.Model.Modelitemlot;
import com.example.lenovo.momotest2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * Created by Lenovo on 06-12-2017.
 */

public class PageMain extends Fragment implements View.OnClickListener {

    private ArrayList<Modeldetail> list;
    private ArrayList<Modelitemlot> list_lot;

    private RecyclerView redetail;
    private RecyclerView re_savelot;

    private GridLayoutManager gridLayoutManager;
    private GridLayoutManager gridLayoutManager_savelot;
    private CustomAdapterMain adapter;
    private CustomAdapterDetail adapter_savelot;

    private LinearLayout laout_number,laout_savelot;

    private TextView bt_zero, bt_nine, bt_eight,
            bt_seven, bt_six, bt_file, bt_four, bt_three, bt_two, bt_one;
    private TextView btn_edit, btn_cancel, btn_enter, btn_Nexto, btn_print,bt_twozero;
    private EditText edit_number;
    private TextView text_tital;
    private TextView btn_close_lot;


    private boolean Check_number = true, Check_numberTop = true,
            Check_numberlower = true, Check_numberToad = true;
    private AllCommand allCommand;
    private TelephonyManager tManager;
    private String uuid;
    private String StateTang;

    public PageMain(){}
    public static PageMain newInstance(){
        PageMain pageMain = new PageMain();
        return pageMain;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.layout_number_main,container,false);
        itemView(view);
        return view;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        list = new ArrayList<>();
        list_lot = new ArrayList<>();
        allCommand = new AllCommand();

        tManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        uuid = tManager.getDeviceId();
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

    private void itemView(View view){

        laout_number = view.findViewById(R.id.laout_number);
        laout_savelot = view.findViewById(R.id.laout_savelot);

        btn_close_lot = view.findViewById(R.id.btn_close_lot);

        edit_number = view.findViewById(R.id.edit_number);
        edit_number.setKeyListener(null);
        redetail = view.findViewById(R.id.recy_detail);
        re_savelot = view.findViewById(R.id.re_savelot);

        btn_enter = view.findViewById(R.id.btn_enter);
        btn_edit = view.findViewById(R.id.btn_edit);
        btn_cancel = view.findViewById(R.id.btn_cancel);
        //btn_Nexto = view.findViewById(R.id.btn_Nexto);
        btn_print = view.findViewById(R.id.btn_print);

        text_tital = view.findViewById(R.id.text_tital);
        text_tital.setText("เลข");
        bt_twozero = view.findViewById(R.id.bt_twozero);
        bt_zero = view.findViewById(R.id.bt_zero);
        bt_nine = view.findViewById(R.id.bt_nine);
        bt_eight = view.findViewById(R.id.bt_eight);
        bt_seven = view.findViewById(R.id.bt_seven);
        bt_six = view.findViewById(R.id.bt_six);
        bt_file = view.findViewById(R.id.bt_file);
        bt_four = view.findViewById(R.id.bt_four);
        bt_three = view.findViewById(R.id.bt_three);
        bt_two = view.findViewById(R.id.bt_two);
        bt_one = view.findViewById(R.id.bt_one);

        bt_twozero.setOnClickListener(this);
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
        btn_print.setOnClickListener(this);

        btn_close_lot.setOnClickListener(this);

        gridLayoutManager = new GridLayoutManager(getActivity(),1);
        adapter = new CustomAdapterMain(list,getActivity());

        gridLayoutManager_savelot = new GridLayoutManager(getActivity(),1);
        adapter_savelot = new CustomAdapterDetail(list_lot,getActivity());

        redetail.setAdapter(adapter);
        redetail.setLayoutManager(gridLayoutManager);
        redetail.setHasFixedSize(true);

        re_savelot.setAdapter(adapter_savelot);
        re_savelot.setLayoutManager(gridLayoutManager_savelot);
        re_savelot.setHasFixedSize(true);

        edit_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                int count = edit_number.length();
                if (count<=0){
                    btn_enter.setBackgroundResource(R.drawable.bg_number_nextto);
                    btn_enter.setText(R.string.text_next);
                }else {
                    btn_enter.setBackgroundResource(R.drawable.bg_number_enter);
                    btn_enter.setText(R.string.text_enter);
                }
                if (text_tital.getText().equals("เลข")){
                    btn_enter.setBackgroundResource(R.drawable.bg_number_enter);
                    btn_enter.setText(R.string.text_enter);
                }
            }
        });
        //setDataFist();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.btn_edit:
                int length = edit_number.getText().length();
                if (length > 0) {
                    edit_number.getText().delete(length - 1, length);
                }
                break;
            case R.id.btn_enter:
                if (btn_enter.getText().equals("ตกลง")){
                    if (edit_number.length()>0){
                        setData();
                        redetail.smoothScrollToPosition(list.size());
                    }
                }else if (btn_enter.getText().equals("ข้าม")){
                    setNexto();
                }

                break;
            case R.id.btn_cancel:
                Log.e("MainActivity", "Clear");
                Clear_Dataset();
                break;
            case R.id.btn_print:

                if(list.size()>0){
                    if (list.get(list.size()-1).getNumber().length()>0&&
                            list.get(list.size()-1).getTop().length()>0||list.get(list.size()-1).getButton().length()>0
                            ||list.get(list.size()-1).getToad().length()>0){
                        saveLot();
                    }

                }
                break;
            case R.id.btn_close_lot:

                laout_number.setVisibility(View.VISIBLE);
                laout_savelot.setVisibility(View.GONE);
                if (list_lot.size()>0){
                    list_lot.clear();
                    adapter_savelot.notifyDataSetChanged();
                }

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
            case R.id.bt_twozero:
                setNumber("00");
                break;
        }

    }

    private void Clear_Editext(){

        edit_number.setText(null);
        edit_number.requestFocus();
        edit_number.setFocusable(true);

    }
    private void setNumber(String number){
        edit_number.setText(edit_number.getText().toString()+number);
    }
    private void setData(){

        if (Check_number){

            Modeldetail modeldetail = new Modeldetail();
            modeldetail.setNumber(edit_number.getText().toString());
            modeldetail.setTop("");
            modeldetail.setButton("");
            modeldetail.setToad("");

            modeldetail.setFocus_number("0");
            modeldetail.setFocus_top("1");
            modeldetail.setFocus_button("0");
            modeldetail.setFocus_toad("0");

            modeldetail.setNo_focus_top("0");
            modeldetail.setNo_focus_button("0");
            modeldetail.setNo_focus_toad("0");

            if (edit_number.getText().length()>2){
                modeldetail.setNo_focus_button("1");

            }else if (edit_number.getText().length()<=2){
                modeldetail.setNo_focus_toad("1");
            }

            list.add(modeldetail);
            adapter.notifyDataSetChanged();

            text_tital.setText("บน");
            Check_number = false;
            Check_numberTop = true;
            Check_numberlower = true;
            Check_numberToad = true;

            if (edit_number.getText().length()>2){
                Check_numberlower = false;

            }else if (edit_number.getText().length()<=2){
                Check_numberToad = false;
            }

            Clear_Editext();

        }else if (Check_numberTop){
            String number = edit_number.getText().toString();
            if (!number.equals("0")){

                Modeldetail modeldetail = new Modeldetail();
                modeldetail.setNumber(list.get(list.size()-1).getNumber());
                modeldetail.setTop(edit_number.getText().toString());
                modeldetail.setButton("");
                modeldetail.setToad("");

                modeldetail.setFocus_number("0");
                modeldetail.setFocus_top("0");
                modeldetail.setFocus_button("0");
                modeldetail.setFocus_toad("0");

                modeldetail.setNo_focus_top(list.get(list.size()-1).getNo_focus_top());
                modeldetail.setNo_focus_button(list.get(list.size()-1).getNo_focus_button());
                modeldetail.setNo_focus_toad(list.get(list.size()-1).getNo_focus_toad());

                if (Check_numberlower){
                    text_tital.setText("ล่าง");
                    modeldetail.setFocus_button("1");
                }else {
                    text_tital.setText("โต้ด");
                    modeldetail.setFocus_toad("1");
                }

                list.set(list.size()-1,modeldetail);
                adapter.notifyDataSetChanged();

                Check_numberTop = false;
                Clear_Editext();

            }


        }else if (Check_numberlower){

            String number = edit_number.getText().toString();
            if (!number.equals("0")){

                Modeldetail modeldetail = new Modeldetail();
                modeldetail.setNumber(list.get(list.size()-1).getNumber());
                modeldetail.setTop(list.get(list.size()-1).getTop());
                modeldetail.setButton(edit_number.getText().toString());
                modeldetail.setToad("");

                modeldetail.setFocus_number("0");
                modeldetail.setFocus_top("0");
                modeldetail.setFocus_button("0");
                modeldetail.setFocus_toad("0");

                modeldetail.setNo_focus_top(list.get(list.size()-1).getNo_focus_top());
                modeldetail.setNo_focus_button(list.get(list.size()-1).getNo_focus_button());
                modeldetail.setNo_focus_toad(list.get(list.size()-1).getNo_focus_toad());

                if (!Check_numberToad){
                    Log.e("MainActivity", "END");
                    text_tital.setText("เลข");
                    Check_number = true;
                    modeldetail.setFocus_number("1");

                }else {

                    text_tital.setText("โต้ด");
                    modeldetail.setFocus_toad("1");
                }

                list.set(list.size()-1,modeldetail);
                adapter.notifyDataSetChanged();

            /*if (!Check_numberToad){
                //setDataFist();
            }*/

                Check_numberlower = false;
                Clear_Editext();

            }


        }else if (Check_numberToad){

            String number = edit_number.getText().toString();
            if (!number.equals("0")){
                Modeldetail modeldetail = new Modeldetail();
                modeldetail.setNumber(list.get(list.size()-1).getNumber());
                modeldetail.setTop(list.get(list.size()-1).getTop());
                modeldetail.setButton(list.get(list.size()-1).getButton());
                modeldetail.setToad(edit_number.getText().toString());

                modeldetail.setFocus_number("0");
                modeldetail.setFocus_top("0");
                modeldetail.setFocus_button("0");
                modeldetail.setFocus_toad("0");


                modeldetail.setNo_focus_top(list.get(list.size()-1).getNo_focus_top());
                modeldetail.setNo_focus_button(list.get(list.size()-1).getNo_focus_button());
                modeldetail.setNo_focus_toad(list.get(list.size()-1).getNo_focus_toad());

                if (!Check_numberlower){
                    Log.e("MainActivity", "END");
                    text_tital.setText("เลข");
                    Check_number = true;
                    modeldetail.setFocus_number("1");
                }

                list.set(list.size()-1,modeldetail);
                adapter.notifyDataSetChanged();

                Check_numberToad = false;
                Clear_Editext();

                //setDataFist();
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
        //setDataFist();

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

            modeldetail.setFocus_number("0");
            modeldetail.setFocus_top("0");
            modeldetail.setFocus_button("1");
            modeldetail.setFocus_toad("0");

            modeldetail.setNo_focus_top("1");
            modeldetail.setNo_focus_button(list.get(list.size()-1).getNo_focus_button());
            modeldetail.setNo_focus_toad(list.get(list.size()-1).getNo_focus_toad());

            if (!Check_numberToad&&list.get(list.size()-1).getTop().length()>0){
                modeldetail.setFocus_button("0");
                modeldetail.setNo_focus_top("0");
                modeldetail.setNo_focus_button("1");
            }


            list.set(list.size()-1,modeldetail);
            adapter.notifyDataSetChanged();

            if (!Check_numberToad&&list.get(list.size()-1).getTop().length()>0){
                Log.e("MainActivity", "END lower");
                text_tital.setText("เลข");
                Check_number = true;
                //setDataFist();
            }

        }else if (!Check_number&&Check_numberToad){

            Clear_Editext();
            Check_numberTop = false;
            text_tital.setText("โต้ด");

            Modeldetail modeldetail = new Modeldetail();
            modeldetail.setNumber(list.get(list.size()-1).getNumber());
            modeldetail.setTop(list.get(list.size()-1).getTop());
            modeldetail.setButton(list.get(list.size()-1).getButton());
            modeldetail.setToad(list.get(list.size()-1).getToad());

            modeldetail.setFocus_number("0");
            modeldetail.setFocus_top("0");
            modeldetail.setFocus_button("0");
            modeldetail.setFocus_toad("1");

            modeldetail.setNo_focus_top("1");
            modeldetail.setNo_focus_button(list.get(list.size()-1).getNo_focus_button());
            modeldetail.setNo_focus_toad(list.get(list.size()-1).getNo_focus_toad());

            if (!Check_numberlower&&list.get(list.size()-1).getTop().length()>0){

                modeldetail.setFocus_toad("0");
                modeldetail.setNo_focus_toad("1");
                modeldetail.setNo_focus_top("0");
            }


            list.set(list.size()-1,modeldetail);
            adapter.notifyDataSetChanged();

            if (!Check_numberlower&&list.get(list.size()-1).getTop().length()>0){
                Log.e("MainActivity", "END lower");
                text_tital.setText("เลข");
                Check_number = true;
                //setDataFist();
            }

        }


    }

    @SuppressLint("StaticFieldLeak")
    private void saveLot(){

        if (allCommand.isConnectingToInternet(getActivity())){

            final String urlServer = allCommand.GetStringShare(getActivity(),allCommand.moURL,"");
            final String moMid = allCommand.GetStringShare(getActivity(),allCommand.moMemberID,"");
            /*Log.e("MainActivity", "URL SET "+urlServer);*/
            new AsyncTask<String, Void, String>() {
                @Override
                protected String doInBackground(String... strings) {
                    ArrayList<FromHttpPostOkHttp> param = new ArrayList<>();
                    param.add(new BasicNameValusPostOkHttp().BasicNameValusPostOkHttp("mid",moMid));
                    param.add(new BasicNameValusPostOkHttp().BasicNameValusPostOkHttp("txt",setFomatTxtSavelot(list)));
                    param.add(new BasicNameValusPostOkHttp().BasicNameValusPostOkHttp("lat","0.0"));
                    param.add(new BasicNameValusPostOkHttp().BasicNameValusPostOkHttp("lon","0.0"));
                    param.add(new BasicNameValusPostOkHttp().BasicNameValusPostOkHttp("zone","1"));
                    param.add(new BasicNameValusPostOkHttp().BasicNameValusPostOkHttp("bf","a1"));
                    param.add(new BasicNameValusPostOkHttp().BasicNameValusPostOkHttp("lot_page","1"));
                    param.add(new BasicNameValusPostOkHttp().BasicNameValusPostOkHttp("uuid",uuid));
                    param.add(new BasicNameValusPostOkHttp().BasicNameValusPostOkHttp("pname",""));
                    param.add(new BasicNameValusPostOkHttp().BasicNameValusPostOkHttp("pid",""));
                    return allCommand.POST_OK_HTTP_SendData(urlServer+"save_lot.php",param);
                    ///return allCommand.GET_OK_HTTP_SendData("http://192.168.1.45/testMomo/save_lot.php");
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    allCommand.ShowLogCat("MainActivity ",s);

                    try {
                        JSONObject jsonTang = new JSONObject(s);
                        Toast.makeText(getContext(), jsonTang.getString("Msg"), Toast.LENGTH_SHORT).show();

                        if (jsonTang.getString("Status").equals("1")){

                            laout_number.setVisibility(View.GONE);
                            laout_savelot.setVisibility(View.VISIBLE);

                            JSONArray jArray = new JSONArray(jsonTang.getString("data"));
                            for (int i = 0;i<jArray.length();i++){
                                JSONObject jObject = jArray.getJSONObject(i);
                                Modelitemlot modelitemlot = new Modelitemlot();
                                modelitemlot.setNumberlot(jObject.getString("txt"));
                                modelitemlot.setStatuslot(jObject.getString("status"));
                                list_lot.add(modelitemlot);
                                adapter_savelot.notifyDataSetChanged();
                                Log.e("MainActivity", jObject.getString("txt"));

                            }
                        }

                        Clear_Dataset();
                        Clear_Editext();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }.execute();
        }

    }
    private String setFomatTxtSavelot(ArrayList<Modeldetail> listsave){

        String save_Lot = "";

        for (int i=0;i<listsave.size();i++){

            save_Lot += allCommand.DeleteFormatNumber(listsave.get(i).getNumber())+"-"+allCommand.DeleteFormatNumber(listsave.get(i).getTop())+"-"+
                    allCommand.DeleteFormatNumber(listsave.get(i).getToad())+"-"+allCommand.DeleteFormatNumber(listsave.get(i).getButton());

            if (i+1<listsave.size()){
                save_Lot += ",";
            }
        }

        return "("+save_Lot+")";
    }

    /*private void setDataFist(){

        Modeldetail modeldetail = new Modeldetail();
        modeldetail.setNumber("");
        modeldetail.setTop("");
        modeldetail.setButton("");
        modeldetail.setToad("");

        modeldetail.setFocus_number("1");
        modeldetail.setFocus_top("0");
        modeldetail.setFocus_button("0");
        modeldetail.setFocus_toad("0");

        modeldetail.setNo_focus_top("0");
        modeldetail.setNo_focus_button("0");
        modeldetail.setNo_focus_toad("0");

        list.add(modeldetail);
        adapter.notifyDataSetChanged();
        redetail.smoothScrollToPosition(list.size());

    }*/

}
