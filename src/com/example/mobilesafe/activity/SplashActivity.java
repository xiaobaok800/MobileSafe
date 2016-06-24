package com.example.mobilesafe.activity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.mobilesafe.R;
import com.example.mobilesafe.utils.StreamUtil;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author Administrator
 *
 */
public class SplashActivity extends Activity {

	protected static final int CODE_UPDATE_DIALOG = 1;
	protected static final int CODE_URL_ERROR = 2;
	protected static final int CODE_NET_ERROR = 3;
	protected static final int CODE_JSON_ERROR = 4;
	protected static final int CODE_ENTER_HOME = 5;
	protected static final int DOWNLOAD = 6;
	protected static final int DOWNLOAD_FINISH = 7;
	private TextView tvVersion;
	private HttpURLConnection conn;
	private String mVersionName;
	private int mVersionCode;
	private String mDesc;
	private String mDownLoadUrl;
	private ProgressBar mProgress;
	private boolean cancelUpdate=false;
	private int progress;
	String target;
	
	private Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CODE_UPDATE_DIALOG:
				showUpdateDialog();
				break;
			case CODE_URL_ERROR:
				Toast.makeText(SplashActivity.this, "url����", 0).show();
				enterHome();
				break;
			case CODE_NET_ERROR:
				Toast.makeText(SplashActivity.this, "�������", 0).show();
				enterHome();
				break;
			case CODE_JSON_ERROR:
				Toast.makeText(SplashActivity.this, "�������ݴ���", 0).show();
				enterHome();
				break;
			case CODE_ENTER_HOME:
				enterHome();
				break;
			case DOWNLOAD:
				mProgress.setProgress(progress);
				break;
			case DOWNLOAD_FINISH:
				installApk();
				break;
			}
		};
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		tvVersion=(TextView)findViewById(R.id.tv_version);
		tvVersion.setText("�汾��"+getVersionName());
		checkVersion();
		
	}
	
	/**
	 * ��ȡ�汾����
	 * @return
	 */
	private String getVersionName() {
		PackageManager packageManager = getPackageManager();
		try {
			PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
			String versionName = packageInfo.versionName;
			return versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return "";
	}
	/**
	 * ��ȡ����app�İ汾��
	 * @return
	 */
	private int getVersionCode() {
		PackageManager packageManager = getPackageManager();
		try {
			PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
			int versionCode = packageInfo.versionCode;
			return versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return -1;
	}
	/**
	 * �ӷ�������ȡ�汾��Ϣ����У��
	 */
	private void checkVersion(){
		final long startTimes = System.currentTimeMillis();
		new Thread(){
			@Override
			public void run() {
				Message msg=Message.obtain();
				// ������ַ��localhost, ���������ģ�������ر����ĵ�ַʱ,������ip(10.0.2.2)���滻
				try {
					URL url = new URL("http://10.0.2.2:8080/update.json");
					conn = (HttpURLConnection) url.openConnection();
					conn.setRequestMethod("GET");
					conn.setConnectTimeout(5000);//�������ӳ�ʱ
					conn.setReadTimeout(5000);// ������Ӧ��ʱ, ��������,���������ٳٲ�����Ӧ
					conn.connect();
					
					int responseCode = conn.getResponseCode();
					if(responseCode==200){
						InputStream inputStream = conn.getInputStream();
						String json = StreamUtil.readFromStream(inputStream);
						Log.w("com", json);
						JSONObject object=new JSONObject(json);
						mVersionName=object.getString("versionName");
						mVersionCode=object.getInt("versionCode");
						mDesc=object.getString("description");
						mDownLoadUrl=object.getString("downloadUrl");
						
						if(mVersionCode>getVersionCode()){
							msg.what=CODE_UPDATE_DIALOG;
						}else{
							//û�а汾���£�ֱ�ӽ���������
							msg.what=CODE_ENTER_HOME;
						}
					}
				} catch (MalformedURLException e) {
					// url������쳣
					msg.what=CODE_URL_ERROR;
					e.printStackTrace();
				} catch (IOException e) {
					// �������
					msg.what=CODE_NET_ERROR;
					e.printStackTrace();
				} catch (JSONException e) {
					//json�쳣
					msg.what=CODE_JSON_ERROR;
					e.printStackTrace();
				}finally {
					long endTimes = System.currentTimeMillis();
					long timeUsed=endTimes-startTimes;//���������ʱ��
					if(timeUsed<2000){
						try {
							//ǿ������һ��ʱ�򣬱�֤����ҳչʾ2��
							Thread.sleep(2000-timeUsed);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					handler.sendMessage(msg);
					if(conn!=null){
						conn.disconnect();
					}
				}
			}
		}.start();
	}
	/**
	 * ���������Ի���
	 */
	protected void showUpdateDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("���°汾"+mVersionName).setMessage(mDesc)
		.setPositiveButton("��������", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				showDownloadDialog();
			}
		}).setNegativeButton("�Ժ����", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				enterHome();
			}
		}).setOnCancelListener(new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				enterHome();
			}
		});
		builder.show();
	}
	/**
	 * ��ʾ������ضԻ���
	 */
	private void showDownloadDialog(){
		// ����������ضԻ���
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle("�������");
		// �����ضԻ������ӽ�����
		final LayoutInflater inflater = LayoutInflater.from(this);
		View v = inflater.inflate(R.layout.softupdate_progress, null);
		mProgress = (ProgressBar) v.findViewById(R.id.update_progress);
		builder.setView(v);
		// ȡ������
		builder.setNegativeButton("ȡ��", new OnClickListener(){
			
			@Override
			public void onClick(DialogInterface dialog, int which){
				dialog.dismiss();
				// ����ȡ��״̬
				enterHome();
			}
		});
		builder.show();
		// �����ļ�
		downLoad();
	}
	/**
	 * �ӷ��������APK�ļ�
	 */
	protected void downLoad() {
		HttpUtils httpUtils = new HttpUtils();
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			target=Environment.getExternalStorageDirectory()+"/mobilesafe_2.0.apk";
			httpUtils.download(mDownLoadUrl, target, new RequestCallBack<File>() {

				@Override
				public void onLoading(long total, long current,
						boolean isUploading) {
					super.onLoading(total, current, isUploading);
					progress = (int)current;
					// ���½���
					handler.sendEmptyMessage(DOWNLOAD);
					if (current>=total){
						// �������
						handler.sendEmptyMessage(DOWNLOAD_FINISH);
					}
				}
				@Override
				public void onSuccess(ResponseInfo<File> arg0) {
					System.out.println("���سɹ�");
					installApk();
				}
				
				@Override
				public void onFailure(HttpException arg0, String arg1) {
					Toast.makeText(SplashActivity.this, "����ʧ��", 0).show();
				}
			});
		}else{
			Toast.makeText(SplashActivity.this, "�Ҳ���sd��", 0).show();
		}
	}
	/**
	 * ��װAPK�ļ�
	 */
	private void installApk() {
		Intent intent=new Intent(Intent.ACTION_VIEW);
		intent.addCategory(intent.CATEGORY_DEFAULT);
		intent.setDataAndType(Uri.parse("file://" + target), "application/vnd.android.package-archive");
		//startActivity(intent);
		startActivityForResult(intent, 0);
	}
	/**
	 * ����û�ȡ����װ�Ļ�,�ص��˷���
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		enterHome();
		super.onActivityResult(requestCode, resultCode, data);
	}
	/**
	 * ����������
	 */
	protected void enterHome() {
		Intent intent=new Intent(this,HomeActivity.class);
		startActivity(intent);
		finish();
	}
}
