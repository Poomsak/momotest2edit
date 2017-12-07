package com.example.lenovo.momotest2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
    private String strStatus = "",strURLmo = "",strUsername = " ",strPassword = " ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        allCommand = new AllCommand();

        bg_Login = (TextView) findViewById(R.id.btn_login);
        edUsername = (EditText) findViewById(R.id.edUsername);
        edPassword = (EditText) findViewById(R.id.edPassword);

        edUsername.setKeyListener(null);
        edPassword.setKeyListener(null);

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
                    return allCommand.POST_OK_HTTP_SendData("My_URL", params_login);
                }

                @Override
                protected void onPostExecute(String s) {
                    allCommand.ShowLogCat("*** url ***", s);

                    try {
                        final JSONObject jObject;
                        jObject = new JSONObject(allCommand.CoverStringFromServer_One(s));
                        strURLmo = jObject.getString("url");
                        allCommand.SaveStringShare(login.this,allCommand.moURL,strURLmo);

                        new AsyncTask<String, Void, String>() {
                            @Override
                            protected void onPreExecute() {
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

                                        allCommand.SaveStringShare(login.this,allCommand.moCradit,jOLogin.getString("MemberCradit"));
                                        allCommand.SaveStringShare(login.this,allCommand.moMemberID,jOLogin.getString("MemberID"));
                                        allCommand.SaveStringShare(login.this,allCommand.moName,jOLogin.getString("Name"));
;
                                        String max1 = jOLogin.getString("MemberMax").toString().trim();
                                        String min1 = jOLogin.getString("MemberMin").toString().trim();
                                        if (max1.toString().trim().length() <= 0){
                                            max1 = "1";
                                        }
                                        if (min1.toString().trim().length() <= 0){
                                            min1 = "1";
                                        }

                                        allCommand.SaveStringShare(login.this,allCommand.moTangMax,max1);

                                        String max = jOLogin.getString("MemberMax").toString().trim();
                                        String min = jOLogin.getString("MemberMin").toString().trim();
                                        if (max.toString().trim().length() <= 0){
                                            max = "1";
                                        }
                                        if (min.toString().trim().length() <= 0){
                                            min = "1";
                                        }
                                        allCommand.SaveStringShare(login.this,allCommand.moTangMax,max);
                                        allCommand.SaveStringShare(login.this,allCommand.moTangMin,min);

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
