package com.example.mobilesafe.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

public abstract class MyAdapter<T> extends BaseAdapter{
	private Context context;
	private List<T> data;
	private LayoutInflater inflater;
	
	public MyAdapter(Context context, List<T> data) {
		setContext(context);
		setData(data);
		setInflater();
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		if(context==null){
			throw new IllegalArgumentException("参数context不能为空值");
		}
		this.context = context;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		if(data==null){
			data=new ArrayList<T>();
		}
		this.data = data;
	}

	public LayoutInflater getInflater() {
		return inflater;
	}

	public void setInflater() {
		if(context==null){
			throw new RuntimeException("没有获得有效的context");
		}
		inflater=LayoutInflater.from(this.context);
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

}
