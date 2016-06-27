package com.example.mobilesafe.activity;

import com.example.mobilesafe.R;
import com.example.mobilesafe.view.SettingItemView;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class SettingActivity extends Activity {

	private SettingItemView sivUpdate;
	private SharedPreferences sp;
	private Editor edit;
	private boolean autoUpdate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		
		sivUpdate=(SettingItemView) findViewById(R.id.siv_update);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		autoUpdate = sp.getBoolean("auto_update", true);
		edit = sp.edit();
		if(autoUpdate){
			//sivUpdate.setDesc("自动更新已开启");
			sivUpdate.setChecked(true);
		}else{
			//sivUpdate.setDesc("自动更新已关闭");
			sivUpdate.setChecked(false);
		}
		sivUpdate=(SettingItemView)findViewById(R.id.siv_update);
		//sivUpdate.setTitle("自动更新设置");
		//sivUpdate.setDesc("自动更新已开启");
		sivUpdate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//判断当前勾选状态
				if(sivUpdate.isChecked()){
					//设置不勾选
					sivUpdate.setChecked(false);
					//sivUpdate.setDesc("自动更新已关闭");
					//更新sp
					edit.putBoolean("auto_update", false).commit();
				}else{
					sivUpdate.setChecked(true);
					//sivUpdate.setDesc("自动更新已开启");
					edit.putBoolean("auto_update", true).commit();
				}
			}
		});
	}
}
