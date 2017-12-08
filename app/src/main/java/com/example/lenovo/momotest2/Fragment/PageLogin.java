package com.example.lenovo.momotest2.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.lenovo.momotest2.FormatHttpPostOkHttp.BasicNameValusPostOkHttp;
import com.example.lenovo.momotest2.FormatHttpPostOkHttp.FromHttpPostOkHttp;
import com.example.lenovo.momotest2.MainActivity;
import com.example.lenovo.momotest2.Manager.AllCommand;
import com.example.lenovo.momotest2.R;
import com.example.lenovo.momotest2.login;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Lenovo on 08-12-2017.
 */

public class PageLogin extends Fragment implements OnClickListener{

    private TextView bg_Login,bt_Left;
    private EditText edUsername,edPassword;
    private AllCommand allCommand;
    private AVLoadingIndicatorView avloadLogin,avi_loadLogo;
    private String strStatus = "",strURLmo = "",strUsername = " ",strPassword = " ";

    private TextView bt_zero, bt_nine, bt_eight,
            bt_seven, bt_six, bt_file, bt_four, bt_three, bt_two, bt_one;
    private TextView btn_edit;
    private TextInputLayout tilEdPassword,tilEdUsername;
    private ImageView im_logo_login;

    public PageLogin(){};

    public static PageLogin newInstance(){
        PageLogin pageLogin = new PageLogin();
        return  pageLogin;
    }

    @Override
    public void onResume() {
        super.onResume();
        setImageLogo();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        allCommand = new AllCommand();
        getURL();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_login,container,false);
        itemView(view);
        return view;
    }

    private void itemView(View view){
        tilEdPassword = view.findViewById(R.id.tilEdPassword);
        tilEdUsername = view.findViewById(R.id.tilEdUsername);

        bg_Login = (TextView) view.findViewById(R.id.btn_login);
        bt_Left = view.findViewById(R.id.bt_Left);
        edUsername = (EditText) view.findViewById(R.id.edUsername);
        edPassword = (EditText) view.findViewById(R.id.edPassword);

        im_logo_login = view.findViewById(R.id.im_logo_login);

        edUsername.setText("aaaa01@zx");
        edPassword.setText("1111");

        edUsername.setKeyListener(null);
        edPassword.setKeyListener(null);

        avloadLogin = (AVLoadingIndicatorView) view.findViewById(R.id.avloadLogin);
        avi_loadLogo = view.findViewById(R.id.avi_loadLogo);

        bg_Login.setOnClickListener(this);
        bt_Left.setOnClickListener(this);

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
        btn_edit = view.findViewById(R.id.btn_edit);

        btn_edit.setOnClickListener(this);

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

        tilEdUsername.setBackgroundResource(R.drawable.bg_ed_select);
    }
    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.btn_login:

                if (edUsername.length() > 0 && edUsername.length() > 0){
                    onGetUrlBall();
                }else {
                    allCommand.ShowAertDialog_OK("กรอก" + getResources().getString(R.string.title_hint_user) + "และ" +
                            getResources().getString(R.string.title_hint_pass),getContext());
                }

                break;
            case R.id.bt_Left:

                setbg_edtext();

                break;

            case R.id.btn_edit:

                int lengthuser = edUsername.getText().length();
                int lengthpass = edPassword.getText().length();

                if (getActivity().getCurrentFocus().getId() == edUsername.getId()){
                    edUsername.getText().delete(lengthuser - 1, lengthuser);

                }else if (getActivity().getCurrentFocus().getId() == edPassword.getId()){
                    edPassword.getText().delete(lengthpass - 1, lengthpass);
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
        }

    }

    @SuppressLint("StaticFieldLeak")
    private void onGetUrlBall() {
        if (edUsername.length() > 0 && edPassword.length() > 0) {

            try {
                new AsyncTask<String, Void, String>() {
                    @Override
                    protected void onPreExecute() {
                        avloadLogin.setVisibility(View.VISIBLE);
                        bg_Login.setEnabled(false);
                        strUsername = getUserFormat(1);
                        strPassword = edPassword.getText().toString().trim();
                    }

                    @Override
                    protected String doInBackground(String... strings) {
                        String str_Url = strURLmo + "checkLogin.php";
                        ArrayList<FromHttpPostOkHttp> params = new ArrayList<FromHttpPostOkHttp>();
                        params.add(new BasicNameValusPostOkHttp().BasicNameValusPostOkHttp("sUsername ", strUsername));
                        params.add(new BasicNameValusPostOkHttp().BasicNameValusPostOkHttp("sPassword ", strPassword));
                        return allCommand.POST_OK_HTTP_SendData(str_Url, params);
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        allCommand.ShowLogCat("*** Login ***", s);
                        try {
                            JSONObject jOLogin =	new JSONObject(s);
                            strStatus = jOLogin.getString("Status");
                            if (strStatus.toString().equals("1")) {

                                allCommand.SaveStringShare(getContext(),allCommand.moCradit,jOLogin.getString("MemberCradit"));
                                allCommand.SaveStringShare(getContext(),allCommand.moMemberID,jOLogin.getString("MemberID"));
                                allCommand.SaveStringShare(getContext(),allCommand.moName,jOLogin.getString("Name"));
                                ;
                                String max1 = jOLogin.getString("MemberMax").toString().trim();
                                String min1 = jOLogin.getString("MemberMin").toString().trim();
                                if (max1.toString().trim().length() <= 0){
                                    max1 = "1";
                                }
                                if (min1.toString().trim().length() <= 0){
                                    min1 = "1";
                                }

                                allCommand.SaveStringShare(getContext(),allCommand.moTangMax,max1);

                                String max = jOLogin.getString("MemberMax").toString().trim();
                                String min = jOLogin.getString("MemberMin").toString().trim();
                                if (max.toString().trim().length() <= 0){
                                    max = "1";
                                }
                                if (min.toString().trim().length() <= 0){
                                    min = "1";
                                }
                                allCommand.SaveStringShare(getContext(),allCommand.moTangMax,max);
                                allCommand.SaveStringShare(getContext(),allCommand.moTangMin,min);

                                Intent gomain = new Intent(getContext()
                                        .getApplicationContext(),MainActivity.class);
                                startActivity(gomain);
                                getActivity().finish();
                            }else {
                                allCommand.ShowAertDialog_OK("ไม่พบชื่อผู้ใช้นี้",getContext());
                            }
                        }catch (Exception e){
                            allCommand.ShowLogCat("*** Err ***", "Err SetDataLogin " + e.getMessage());
                        }
                        avloadLogin.setVisibility(View.INVISIBLE);
                        bg_Login.setEnabled(true);
                    }

                }.execute();

            } catch (Exception e) {
                avloadLogin.setVisibility(View.INVISIBLE);
                bg_Login.setEnabled(true);
                allCommand.ShowLogCat("*** Err ***", "Err SetDataGetUrl " + e.getMessage());
            }

        }
    }

    private void setNumber(String number){
        if (getActivity().getCurrentFocus().getId() == edUsername.getId()){
            edUsername.setText(edUsername.getText()+number);
        }else if (getActivity().getCurrentFocus().getId() == edPassword.getId()){
            edPassword.setText(edPassword.getText()+number);
        }
    }
    private void setbg_edtext(){
        if (getActivity().getCurrentFocus().getId() == edUsername.getId()){
            edPassword.requestFocus();
            tilEdPassword.setBackgroundResource(R.drawable.bg_ed_select);
            tilEdUsername.setBackgroundResource(R.drawable.bg_ed_pass);
        }else if (getActivity().getCurrentFocus().getId() == edPassword.getId()){
            edUsername.requestFocus();
            tilEdUsername.setBackgroundResource(R.drawable.bg_ed_select);
            tilEdPassword.setBackgroundResource(R.drawable.bg_ed_pass);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private void getURL(){

        if (allCommand.isConnectingToInternet(getContext())){

            new AsyncTask<String, Void, String>() {

                @Override
                protected String doInBackground(String... strings) {
                    ArrayList<FromHttpPostOkHttp> params_login = new ArrayList<FromHttpPostOkHttp>();
                    params_login.add(new BasicNameValusPostOkHttp().BasicNameValusPostOkHttp("server",
                            getUserFormat(2)));
                    return allCommand.POST_OK_HTTP_SendData("My_URL", params_login);
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    allCommand.ShowLogCat("*** url ***", s);
                    final JSONObject jObject;
                    try {
                        jObject = new JSONObject(allCommand.CoverStringFromServer_One(s));
                        strURLmo = jObject.getString("url");
                        allCommand.SaveStringShare(getContext(),allCommand.moURL,strURLmo);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }.execute();
        }

    }
    @SuppressLint("StaticFieldLeak")
    private void setImageLogo(){
        avi_loadLogo.setVisibility(View.VISIBLE);
        Glide.with(getContext())
                .load(strURLmo+"img/logo99.png")
                .listener(new RequestListener<String, GlideDrawable>() {

                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        avi_loadLogo.setVisibility(View.INVISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        avi_loadLogo.setVisibility(View.INVISIBLE);
                        return false;
                    }
                })
                .into(im_logo_login);

    }
    private String getUserFormat(int status){
        String userName = edUsername.getText().toString().trim();
        final String[] arrUserName = userName.split("\\@");
        if (status == 2){//server
            if (arrUserName.length == 2){
                return arrUserName[1];
            }else {
                return "";
            }
        }else if (status == 1){//user
            if (arrUserName.length == 2){
                return arrUserName[0];
            }else {
                return userName;
            }
        }else {//null
            return userName;
        }
    }
}
