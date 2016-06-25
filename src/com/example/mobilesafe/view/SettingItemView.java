package com.example.mobilesafe.view;

import com.example.mobilesafe.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SettingItemView extends RelativeLayout{

	private TextView tvSettingTitle;
	private TextView tvSettingDesc;
	private CheckBox cbUpdate;

	public SettingItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
	}

	public SettingItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	public SettingItemView(Context context) {
		super(context);
		initView();
	}

	private void initView() {
		//让SettingItemView引用设置好的自定义的布局文件
		View.inflate(getContext(), R.layout.item_setting_list, this);
		tvSettingTitle = (TextView)findViewById(R.id.tv_setting_title);
		tvSettingDesc = (TextView)findViewById(R.id.tv_setting_desc);
		cbUpdate=(CheckBox)findViewById(R.id.cb_update);
	}
	
	public void setTitle(String title){
		tvSettingTitle.setText(title);
	}
	
	public void setDesc(String desc){
		tvSettingDesc.setText(desc);
	}
	
	public boolean isChecked(){
		return false;
	}
	
	public void setCheck(boolean checked){
		
		if(isChecked()){
			cbUpdate.setChecked(false);
		}else{
			cbUpdate.setChecked(true);
		}
	}
}
