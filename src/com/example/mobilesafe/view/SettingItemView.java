package com.example.mobilesafe.view;

import com.example.mobilesafe.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
/**
 * 自定义组合控件
 * @author Administrator
 *
 */
public class SettingItemView extends RelativeLayout{

	private static final String NAMESPACE = "http://schemas.android.com/apk/res/com.example.mobilesafe";
	private TextView tvSettingTitle;
	private TextView tvSettingDesc;
	private CheckBox cbUpdate;
	private String title;
	private String descOn;
	private String descOff;

	public SettingItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
	}

	public SettingItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
		//根据属性名称获取属性值
		title = attrs.getAttributeValue(NAMESPACE, "title");
		descOn = attrs.getAttributeValue(NAMESPACE, "desc_on");
		descOff = attrs.getAttributeValue(NAMESPACE, "desc_off");
		setTitle(title);
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
	/**
	 * 返回勾选状态
	 * @return
	 */
	public boolean isChecked(){
		return cbUpdate.isChecked();
	}
	/**
	 * 设置勾选状态
	 * @param checked
	 */
	public void setChecked(boolean checked){
		cbUpdate.setChecked(checked);
		if(checked){
			setDesc(descOn);
		}else{
			setDesc(descOff);
		}
	}
}
