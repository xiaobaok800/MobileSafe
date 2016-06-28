package com.example.mobilesafe.activity;

import com.example.mobilesafe.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends Activity {

	private GridView gvHome;
	private String[] mItems = new String[] { "手机防盗", "通讯卫士", "软件管理", "进程管理",
			"流量统计", "手机杀毒", "缓存清理", "高级工具", "设置中心" };

	private int[] mPics = new int[] { R.drawable.home_safe,
			R.drawable.home_callmsgsafe, R.drawable.home_apps,
			R.drawable.home_taskmanager, R.drawable.home_netmanager,
			R.drawable.home_trojan, R.drawable.home_sysoptimize,
			R.drawable.home_tools, R.drawable.home_settings };
	private SharedPreferences sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		sp = getSharedPreferences("config", MODE_PRIVATE);
		
		gvHome=(GridView)findViewById(R.id.gv_home);
		gvHome.setAdapter(new HomeAdapter());
		gvHome.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, 
					int position, long id) {
				switch (position) {
				case 0:
					showPassWordDialog();
					break;
				//设置中心
				case 8:
					Intent intent=new Intent(HomeActivity.this,SettingActivity.class);
					startActivity(intent);
					break;
				}
			}
		});
	}
	
	protected void showPassWordDialog() {
		String savePassWord = sp.getString("passWord", null);
		if(!TextUtils.isEmpty(savePassWord)){
			showPassWordInputDialog();
		}else{
			showPassWordSetDialog();
		}
	}

	private void showPassWordInputDialog() {
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		final AlertDialog dialog=builder.create();
		View view=View.inflate(this, R.layout.dialog_input_password, null);
		dialog.setView(view, 0,0, 0, 0);//设置边距为0，保证兼容性
		
		Button btOk=(Button)view.findViewById(R.id.bt_ok);
		Button btCancel=(Button)view.findViewById(R.id.bt_cancel);
		final EditText etPsd=(EditText)view.findViewById(R.id.et_psd);
		
		btOk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String passWord=etPsd.getText().toString();
				String savePassWord = sp.getString("passWord",null);
				if(!TextUtils.isEmpty(passWord)){
					if(passWord.equals(savePassWord)){
						Toast.makeText(HomeActivity.this, "登录成功", 0).show();
						dialog.dismiss();
					}else{
						Toast.makeText(HomeActivity.this, "密码不正确", 0).show();
					}
				}else{
					Toast.makeText(HomeActivity.this, "密码不能为空", 0).show();
				}
			}
		});
		btCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.show();
		
		WindowManager manager = getWindowManager();
		Display display = manager.getDefaultDisplay();//获取屏幕尺寸
		WindowManager.LayoutParams params=dialog.getWindow().getAttributes();
		params.width=(int) (display.getWidth()*0.85);
		dialog.getWindow().setAttributes(params);
	}

	private void showPassWordSetDialog() {
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		final AlertDialog dialog=builder.create();
		View view=View.inflate(this, R.layout.dialog_set_password, null);
		dialog.setView(view, 0,0, 0, 0);//设置边距为0，保证兼容性
		
		Button btOk=(Button)view.findViewById(R.id.bt_ok);
		Button btCancel=(Button)view.findViewById(R.id.bt_cancel);
		final EditText etPsd=(EditText)view.findViewById(R.id.et_psd);
		final EditText etPsdConfirm=(EditText)view.findViewById(R.id.et_psd_confirm);
		
		btOk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String passWord=etPsd.getText().toString();
				String passWordConfirm=etPsdConfirm.getText().toString();
				
				if(!TextUtils.isEmpty(passWord)&&!TextUtils.isEmpty(passWordConfirm)){
					if(passWord.equals(passWordConfirm)){
						Toast.makeText(HomeActivity.this, "登录成功", 0).show();
						sp.edit().putString("passWord", passWord).commit();
						
						dialog.dismiss();
					}else{
						Toast.makeText(HomeActivity.this, "密码不正确", 0).show();
					}
				}else{
					Toast.makeText(HomeActivity.this, "密码不能为空", 0).show();
				}
			}
		});
		btCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.show();
		
		WindowManager manager = getWindowManager();
		Display display = manager.getDefaultDisplay();//获取屏幕尺寸
		WindowManager.LayoutParams params=dialog.getWindow().getAttributes();
		params.width=(int) (display.getWidth()*0.85);
		dialog.getWindow().setAttributes(params);
	}

	class HomeAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return mItems.length;
		}

		@Override
		public Object getItem(int position) {
			return mItems[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if(convertView==null){
				LayoutInflater inflater=LayoutInflater.from(HomeActivity.this);
				convertView=inflater.inflate(R.layout.item_home_list, null);
				holder=new ViewHolder();
				holder.ivItem=(ImageView) convertView.findViewById(R.id.iv_item);
				holder.tvItem=(TextView) convertView.findViewById(R.id.tv_item);
				convertView.setTag(holder);
			}
			holder=(ViewHolder) convertView.getTag();
			holder.ivItem.setImageResource(mPics[position]);
			holder.tvItem.setText(mItems[position]);
			return convertView;
		}

		public class ViewHolder{
			ImageView ivItem;
			TextView tvItem;
		}
	}
	
}
