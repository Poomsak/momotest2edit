package com.example.lenovo.momotest2.Manager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TextView;

import com.example.lenovo.momotest2.BuildConfig;
import com.example.lenovo.momotest2.FormatHttpPostOkHttp.FromHttpPostOkHttp;
import com.example.lenovo.momotest2.R;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.util.ArrayList;


public class AllCommand {

	public static String SHARE_NAME = "SAVE_LOGIN";
	public static String kcrid = "kcrid";
	public static String kURLBall = "kURLBall";
	public static String kmid = "kmid";
	public static String kmcount = "kmcount";
	public static String krob = "krob";
	public static String kmname = "kmname";
	public static String kmdate ="kmdate";
	public static String kmuser = "kmuser";

	public static String kSaveUserName = "ksave_username";
	public static String kSavePassword = "ksave_password";

	public static String SHARE_IDDIVICE= "save_id_divice";
	public static String SHARE_NAMEDIVICE= "save_name_divice";

	public static String SHARE_MEMBERMAX1 = "membermax_teng";
	public static String SHARE_MEMBERMIN1 = "membermin_teng";
	public static String SHARE_MEMBERMAX = "membermax";
	public static String SHARE_MEMBERMIN = "membermin";
	public static String SHARE_STEPMAXMIN = "stepmaxmin";

	public static String SHARE_BARCODE = "barcode";
	public static String SHARE_TIME_REFRESH = "timerefresh";
	public static String SHARE_TIME_REFRESH_LIVE = "timerefreshlive";
	public static String SHARE_CHECK_LOAD = "check_load";
	public static String SHARE_CHECK_LOAD_LIVE = "check_load_live";
	public static String SHARE_SETTING_PAPER = "setting_paper";

	public boolean isConnectingToInternet(Context _context) {
		ConnectivityManager connectivity = (ConnectivityManager) _context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null)
				for (int i = 0; i < info.length; i++)
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
		}
		return false;
	}
	public String GET_OK_HTTP_SendData(String url) {
		ShowLogCat("GET_OK_HTTP_SendData",url);
		try{
			OkHttpClient client = new OkHttpClient();
			Request request = new Request.Builder().url(url).build();
			Response response = client.newCall(request).execute();
			return response.body().string();
		}catch (Exception e){
			ShowLogCat("Err","GET_OK_HTTP_SendData " + e.getMessage());
			return "";
		}
	}
	public String POST_OK_HTTP_SendData(String url, ArrayList<FromHttpPostOkHttp> params) {
		ShowLogCat("POST_OK_HTTP_SendData",url);
		try{
			OkHttpClient client = new OkHttpClient();
			MultipartBuilder multipartBuilder = new MultipartBuilder().type(MultipartBuilder.FORM);
			for (int i = 0;i<params.size();i++){
				Log.e("Check Data",params.get(i).getKEY_POST().toString()+ " : " +params.get(i).getVALUS_POST().toString());
				multipartBuilder.addFormDataPart(params.get(i).getKEY_POST().toString(),params.get(i).getVALUS_POST().toString());
			}
			RequestBody requestBody = multipartBuilder.build();
			Request request = new Request.Builder()
					.url(url)
					.post(requestBody)
					.build();

			Response response = client.newCall(request).execute();
			if (response.isSuccessful()){
				return response.body().string().toString();
			}
		}catch (Exception e){
			Log.e("*** Err ***","Err POST_OK_HTTP_SendData " + e.getMessage());
			return "";
		}
		return "";
	}

	public String GetStringShare(Context _context, String strKey, String strDe) {
		SharedPreferences shLang;
		shLang = _context
				.getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);
		if (shLang != null) {
			String strShare = shLang.getString(strKey, strDe);
			return strShare;
		}
		return "";
	}
	public void SaveStringShare(Context _context, String strKey, String strDe){
		SharedPreferences shLang;
		SharedPreferences.Editor edShLang;
		shLang = _context.getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);
		edShLang = shLang.edit();
		edShLang.remove(strKey);
		edShLang.commit();
		edShLang.putString(strKey, strDe);
		edShLang.commit();
	}

	public void RemvoeStringShare(Context _context, String strKey, String strDe){
		SharedPreferences shLang;
		SharedPreferences.Editor edShLang;
		shLang = _context.getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);
		edShLang = shLang.edit();
		edShLang.remove(strKey);
		edShLang.commit();
	}
	public void ShowLogCat(String tag, String msg){
		if (BuildConfig.DEBUG){
			Log.e("***AllCommand***",tag + " : " + msg);
		}
	}
	public String CoverStringFromServer_One(String strData){
		try {
			return  strData.substring(strData.indexOf("{"), strData.lastIndexOf("}") + 1);
		} catch (Exception e) {
			return "";
		}
	}
	public void ShowAertDialog_OK(String strMsg, final Context _context){
		int sizeMsgDialog = Integer.parseInt(_context.getResources().getString(R.string.size_dialog_ok));
		AlertDialog.Builder builder = new AlertDialog.Builder(_context);
		TextView myMsg = new TextView(_context);
		myMsg.setText("\n" + strMsg + "\n");
		myMsg.setTextSize(sizeMsgDialog);
		myMsg.setGravity(Gravity.CENTER);
		myMsg.setTypeface(null, Typeface.BOLD);
		builder.setView(myMsg);
		builder.setCancelable(true);
		builder.setPositiveButton(R.string.title_ok,null);
		final AlertDialog alertdialog = builder.create();
		alertdialog.setOnShowListener(new DialogInterface.OnShowListener() {
			@Override
			public void onShow(DialogInterface arg0) {
				int iPadBT = Integer.parseInt(_context.getResources().getString(R.string.size_padding_tb_dialog));
				int iSizeButton = Integer.parseInt(_context.getResources().getString(R.string.size_dialog_button));
				Button btnOk = alertdialog.getButton(Dialog.BUTTON_POSITIVE);
				btnOk.setTextSize(iSizeButton);
				btnOk.setPadding(1, iPadBT, 1, iPadBT);
				btnOk.setTypeface(null, Typeface.BOLD);
			}
		});
		alertdialog.show();
	}

}
