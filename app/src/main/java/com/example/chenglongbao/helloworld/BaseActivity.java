package com.example.chenglongbao.helloworld;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.apkfuns.xprogressdialog.XProgressDialog;
import com.example.chenglongbao.helloworld.activity.ProgressBarActivity;

import java.util.Locale;

public class BaseActivity extends AppCompatActivity {
	public String countryCode;

	public XProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Locale l = Locale.getDefault();
		countryCode = l.getCountry();
		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
//		NYApplication.getTracker().setScreenName("BaseActivity");
	}
	
	@Override
	protected void onStop() {
		super.onStop();
	}

	/**
	 * 登录中...dialog
	 * @param messageId
	 */
//	public void progressShow(int messageId) {
//		// 数据同步
//		dialog = new ProgressDialog(this);
//		dialog.setMessage(getResources().getString(messageId));
//		dialog.setCancelable(true);
//		dialog.setCanceledOnTouchOutside(false);
//		dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
//
//			@Override
//			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
//				if (keyCode == KeyEvent.KEYCODE_BACK) {
//				}
//				return true;
//			}
//		});
//		dialog.show();
//	}

//	public ProgressDialog progressShow(int messageId) {
//		// 数据同步
//		dialog = new ProgressDialog(this);
//		dialog.setMessage(getResources().getString(messageId));
//		dialog.setCancelable(true);
//		dialog.setCanceledOnTouchOutside(false);
//		dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
//
//			@Override
//			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
//				if (keyCode == KeyEvent.KEYCODE_BACK) {
//				}
//				return false;
//			}
//		});
//		dialog.show();
//		return dialog;
//	}

	public XProgressDialog progressShow(Context context, int messageId) {
		// 数据同步
		dialog = new XProgressDialog(context, messageId);
		dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
				}
				return false;
			}
		});
		dialog.show();
		return dialog;
	}
}
