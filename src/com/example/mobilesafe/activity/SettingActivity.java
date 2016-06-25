package com.example.mobilesafe.activity;

import com.example.mobilesafe.R;
import com.example.mobilesafe.view.SettingItemView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class SettingActivity extends Activity {

	private SettingItemView sivUpdate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		
		sivUpdate=(SettingItemView)findViewById(R.id.siv_update);
		sivUpdate.setTitle("自动更新设置");
		sivUpdate.setDesc( "自动更新已开启");
		sivUpdate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				if(){
//					
//				}else{
//					
//				}
			}
		});
	}
}
