package com.example.lenovo.momotest2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lenovo.momotest2.FormatHttpPostOkHttp.BasicNameValusPostOkHttp;
import com.example.lenovo.momotest2.FormatHttpPostOkHttp.FromHttpPostOkHttp;
import com.example.lenovo.momotest2.Manager.AllCommand;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONObject;

import java.util.ArrayList;

public class login extends AppCompatActivity {

    private TextView bg_Login;
    private EditText edUsername,edPassword;
    private AllCommand allCommand;
    private AVLoadingIndicatorView avloadLogin;
    private String strStatus = "",strURLBall = "",strUsername = " ",strPassword = " ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        allCommand = new AllCommand();

        bg_Login = (TextView) findViewById(R.id.bg_Login);
        edUsername = (EditText) findViewById(R.id.edUsername);
        edPassword = (EditText) findViewById(R.id.edPassword);


        avloadLogin = (AVLoadingIndicatorView) findViewById(R.id.avloadLogin);

        bg_Login.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edUsername.length() > 0 && edUsername.length() > 0){
                    onGetUrlBall();
                }else {
                    allCommand.ShowAertDialog_OK("กรอก" + getResources().getString(R.string.title_hint_user) + "และ" +
                            getResources().getString(R.string.title_hint_pass),login.this);
                }

            }
        });
    }
    @SuppressLint("StaticFieldLeak")
    private void onGetUrlBall() {
        if (edUsername.length() > 0 && edPassword.length() > 0) {
            new AsyncTask<String, Void, String>() {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    avloadLogin.setVisibility(View.VISIBLE);
                    bg_Login.setEnabled(false);
                }

                @Override
                protected String doInBackground(String... strings) {
                    ArrayList<FromHttpPostOkHttp> params_login = new ArrayList<FromHttpPostOkHttp>();
                    params_login.add(new BasicNameValusPostOkHttp().BasicNameValusPostOkHttp("server",
                            getUserFormat(2)));
                    return allCommand.POST_OK_HTTP_SendData("URL.php", params_login);
                }

                @Override
                protected void onPostExecute(String s) {
                    allCommand.ShowLogCat("*** url ***", s);

                    try {
                        final JSONObject jObject;
                        jObject = new JSONObject(allCommand.CoverStringFromServer_One(s));
                        strURLBall = jObject.getString("url");
                        allCommand.SaveStringShare(login.this,allCommand.kURLBall,strURLBall);

                        new AsyncTask<String, Void, String>() {
                            @Override
                            protected void onPreExecute() {
                                strUsername = getUserFormat(1);
                                strPassword = edPassword.getText().toString().trim();
                            }

                            @Override
                            protected String doInBackground(String... strings) {
                                String str_Url = strURLBall + "checkLogin.php";
                                ArrayList<FromHttpPostOkHttp> params = new ArrayList<FromHttpPostOkHttp>();
                                params.add(new BasicNameValusPostOkHttp().BasicNameValusPostOkHttp("l_user", strUsername));
                                params.add(new BasicNameValusPostOkHttp().BasicNameValusPostOkHttp("l_pass", strPassword));
                                return allCommand.POST_OK_HTTP_SendData(str_Url, params);
                            }

                            @Override
                            protected void onPostExecute(String s) {
                                allCommand.ShowLogCat("*** Login ***", s);
                                try {
                                    JSONObject jOLogin =	new JSONObject(s);
                                    strStatus = jOLogin.getString("Status");
                                    if (strStatus.toString().equals("2")) {

                                        /*
                                        allCommand.SaveStringShare(login.this,allCommand.kSaveUserName,edUsername.getText().toString());
                                        allCommand.SaveStringShare(login.this,allCommand.kSavePassword,edPassword.getText().toString());
                                        allCommand.SaveStringShare(login.this,allCommand.kcrid,jOLogin.getString("crid"));
                                        allCommand.SaveStringShare(login.this,allCommand.kmuser,jOLogin.getString("muser"));
                                        allCommand.SaveStringShare(login.this,allCommand.kmid,jOLogin.getString("mid"));
                                        allCommand.SaveStringShare(login.this,allCommand.kmcount,jOLogin.getString("mcount"));
                                        allCommand.SaveStringShare(login.this,allCommand.krob,((jOLogin.isNull("rob")) ? "" : jOLogin.getString("rob")));
                                        allCommand.SaveStringShare(login.this,allCommand.kmname,jOLogin.getString("mname"));
                                        allCommand.SaveStringShare(login.this,allCommand.kmdate,jOLogin.getString("mdate"));

                                        allCommand.SaveStringShare(login.this,allCommand.SHARE_BARCODE,jOLogin.getString("barcode"));
                                        String max1 = jOLogin.getString("yodmax1").toString().trim();
                                        String min1 = jOLogin.getString("yodmin1").toString().trim();
                                        if (max1.toString().trim().length() <= 0){
                                            max1 = "1";
                                        }
                                        if (min1.toString().trim().length() <= 0){
                                            min1 = "1";
                                        }

                                        allCommand.SaveStringShare(login.this,allCommand.SHARE_MEMBERMAX1,max1);
                                        allCommand.SaveStringShare(login.this,allCommand.SHARE_MEMBERMIN1,min1);

                                        String max = jOLogin.getString("yodmax").toString().trim();
                                        String min = jOLogin.getString("yodmin").toString().trim();
                                        if (max.toString().trim().length() <= 0){
                                            max = "1";
                                        }
                                        if (min.toString().trim().length() <= 0){
                                            min = "1";
                                        }
                                        allCommand.SaveStringShare(login.this,allCommand.SHARE_MEMBERMAX,max);
                                        allCommand.SaveStringShare(login.this,allCommand.SHARE_MEMBERMIN,min);
                                        allCommand.SaveStringShare(login.this,allCommand.SHARE_STEPMAXMIN,jOLogin.getString("stepmaxmin"));

                                        allCommand.SaveStringShare(login.this,allCommand.SHARE_TIME_REFRESH,
                                                String.valueOf(1000 * jOLogin.getInt("refresh")));
                                        allCommand.SaveStringShare(login.this,allCommand.SHARE_TIME_REFRESH_LIVE,
                                                String.valueOf(1000 * jOLogin.getInt("refresh_live")));*/

                                        Intent gomain = new Intent(login.this
                                                .getApplicationContext(),MainActivity.class);
                                        startActivity(gomain);
                                        login.this.finish();
                                    }else {
                                        allCommand.ShowAertDialog_OK("ไม่พบชื่อผู้ใช้นี้",login.this);
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

            }.execute();
        }
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
