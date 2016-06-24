package com.example.mobilesafe.activity;

import com.example.mobilesafe.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeActivity extends Activity {

	private GridView gvHome;
	private String[] mItems = new String[] { "�ֻ�����", "ͨѶ��ʿ", "�������", "���̹���",
			"����ͳ��", "�ֻ�ɱ��", "��������", "�߼�����", "��������" };

	private int[] mPics = new int[] { R.drawable.home_safe,
			R.drawable.home_callmsgsafe, R.drawable.home_apps,
			R.drawable.home_taskmanager, R.drawable.home_netmanager,
			R.drawable.home_trojan, R.drawable.home_sysoptimize,
			R.drawable.home_tools, R.drawable.home_settings };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		gvHome=(GridView)findViewById(R.id.gv_home);
		gvHome.setAdapter(new HomeAdapter());
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
