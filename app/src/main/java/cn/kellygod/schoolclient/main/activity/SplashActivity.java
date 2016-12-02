/*
*	This code is free, you use this code at anywhere ,but don't remove these line
*	@author kellygod <kellygod95@gmail.com>
*/
package cn.kellygod.schoolclient.main.activity;
import android.support.v7.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import cn.kellygod.schoolclient.R;
import cn.kellygod.schoolclient.ndk.NativeLoad;
import cn.kellygod.schoolclient.util.AppManager;
import cn.kellygod.schoolclient.connection.CommonName;
import cn.kellygod.schoolclient.util.CommonUtils;
import cn.kellygod.schoolclient.util.ToastUtil;
/**
 *  @author kellygod
 *  启动界面
 *
 * */
public class SplashActivity extends BaseActivity {
	private final String TAG = "SplashActivity";
	private static class StartHandler extends Handler{
		private final String TAG="SplashActivity_handler";
		WeakReference<SplashActivity> mActivity;
		public StartHandler(SplashActivity activity) {
			mActivity = new WeakReference<>(activity);
		}

		@Override
		public void handleMessage(Message msg) {
			final SplashActivity splashActivity=mActivity.get();
			switch (msg.what){
				case CommonName.STATUS_COMMON_OK:
					ToastUtil.show(splashActivity.getApplicationContext(), "网络连接成功");
					//启动服务
					Intent intent = new Intent(splashActivity, MainActivity.class);
					splashActivity.startActivity(intent);
					AppManager.getAppManager().finishActivity();
					break;
				case CommonName.STATUS_COMMON_ERROR:
					//此Dialog的Context必须为SplashActivity
					new AlertDialog.Builder(splashActivity,android.R.style.Theme_Material_Light_Dialog_Alert)
							.setTitle("网络错误")
							.setMessage("服务器无响应，请检查网络连接")
							.setPositiveButton("确定", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									AppManager.getAppManager().AppExit(splashActivity.getApplicationContext());
								}
							})
							.setCancelable(false)
							.show();
					break;
				default:
					break;
			}
			super.handleMessage(msg);
		}
	}

	private Handler mHandler=new StartHandler(this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		//使用native登陆VPN网络
		NativeLoad.LoginVPN(mHandler);
		//使用java层登陆VPN网络 账号，密码
		//LoginVPN.login("","",mHandler);
		TextView tv_app_version = (TextView) findViewById(R.id.tv_app_version);
		tv_app_version.setText("版本：" + CommonUtils.softVersion(getApplicationContext()) + " 正式版");
	}


}

