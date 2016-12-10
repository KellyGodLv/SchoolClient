package cn.kellygod.schoolclient.main.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.jsoup.Jsoup;

import java.lang.ref.WeakReference;

import cn.kellygod.schoolclient.R;
import cn.kellygod.schoolclient.connection.EducationDao;
import cn.kellygod.schoolclient.connection.IEducaationLogin;
import cn.kellygod.schoolclient.connection.UpdateAppBean;
import cn.kellygod.schoolclient.connection.UpdateAppDao;
import cn.kellygod.schoolclient.education.utils.StudentInfo;
import cn.kellygod.schoolclient.main.fragment.CollectFragment;
import cn.kellygod.schoolclient.main.fragment.HomeFragment;
import cn.kellygod.schoolclient.main.fragment.SettingFragment;
import cn.kellygod.schoolclient.service.HeartBeatPacketFactory;
import cn.kellygod.schoolclient.service.HeartBeatService;
import cn.kellygod.schoolclient.util.AppManager;
import cn.kellygod.schoolclient.connection.CommonName;
import cn.kellygod.schoolclient.util.CommonUtils;
import cn.kellygod.schoolclient.util.ConstantValues;
import cn.kellygod.schoolclient.util.LogUtils;
import cn.kellygod.schoolclient.util.SavePassword;
import cn.kellygod.schoolclient.util.ToastUtil;
import cn.kellygod.schoolclient.widget.MyTabWidget;
import cn.kellygod.schoolclient.widget.MyTabWidget.OnTabSelectedListener;
import cn.kellygod.schoolclient.widget.UpdateDialog;

/**
 * 主界面
 * 
 * @author kellygod
 * 
 */
public class MainActivity extends FragmentActivity implements
		OnTabSelectedListener,IEducaationLogin{

	private static final String TAG = "MainActivity";
	private long firstTime=0;
	private android.support.v7.app.AlertDialog dialog=null;
	private MyTabWidget mTabWidget;
	private HomeFragment mHomeFragment;
	private CollectFragment mCollectFragment;
	private SettingFragment mSettingFragment;
	private int mIndex = ConstantValues.COLLECT_FRAGMENT_INDEX;
	private FragmentManager mFragmentManager;
	private ImageView ivCheckCode=null;
	private EditText etPassWord=null;
	private EditText etUsernNme=null;
	private EditText etCheckCode=null;
	private CheckBox cbRememberPassword=null;
	private String studentNumber="";

	@Override
	public void showEducationDialog(String message) {
		if("login".equals(message))
			//获取教务系统验证码
			EducationDao.refleshCheckCode(getApplicationContext(), MyHandler);
			dialog.show();
			dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if(etUsernNme.getText().toString().equals("") || etPassWord.getText().toString().equals("")) {
						//这里暂时不做任何处理
						ToastUtil.show(MainActivity.this, "信息不能为空");
						//用于关闭AlertDialog
						//dialog.dismiss();
						return ;
					}
					//登陸接口
					EducationDao.login(
							etUsernNme.getText().toString(),
							etPassWord.getText().toString(),
							etCheckCode.getText().toString(),
							MyHandler);
					//dialog.dismiss();
				}
			});
	}

	/**
	 *  EducationHandler用于处理其他线程发来的消息所有的UI更新必须在主线程中更新
	 *
	 *
	 * */
	private static class EducationHandler extends Handler{
		WeakReference<MainActivity> mActivity;
		private String TAG="MyHandler";
		public EducationHandler(MainActivity activity) {
			mActivity = new WeakReference<MainActivity>(activity);
		}
		@Override
		public void handleMessage(Message msg) {
			MainActivity mainActivity = mActivity.get();
			Log.d(TAG,"Myhandler revice message");
			switch (msg.what){
				//获取验证码
				case CommonName.STATUS_CHECK_CODE_OK:
					Glide
							.with(mainActivity)
							.load(mainActivity.getApplicationInfo().dataDir + "/CheckCode.gif")
							.diskCacheStrategy(DiskCacheStrategy.NONE)
							.skipMemoryCache(true)
							.into(mainActivity.ivCheckCode);
					break;
				case CommonName.STATUS_CHECK_CODE_ERROR:
					ToastUtil.show(mainActivity,"获取验证码失败");
					break;
				case CommonName.STATUS_COMMON_ERROR:
					ToastUtil.show(mainActivity,"填写的信息有误");
					break;
				//信息发送成功
				case CommonName.STATUS_COMMON_OK:
					//获取返回的字符串
					String html=(String)msg.obj;
					String xhxm="";
					try {
						xhxm = Jsoup.parse(html)
								.getElementById("xhxm")
								.text();
					}catch (NullPointerException e){
						xhxm="";
					}
					//通过获取姓名判断是否登陆成功
					if(!CommonUtils.isNull(xhxm)){
						//判断是否保存密码
						if(mainActivity.cbRememberPassword.isChecked()) {
							SavePassword
									.getSavePasswordInstance(mainActivity)
									.saveData(SavePassword.KEY_USERNAME,
											mainActivity.etUsernNme.getText().toString());
							SavePassword
									.getSavePasswordInstance(mainActivity)
									.saveData(SavePassword.KEY_PASSWORD,
											mainActivity.etPassWord.getText().toString());
							SavePassword
									.getSavePasswordInstance(mainActivity)
									.saveData(SavePassword.KEY_SAVE_MESSAGE,"true");
							LogUtils.d(TAG,"信息已经保存");
						}else{
							SavePassword
									.getSavePasswordInstance(mainActivity)
									.saveData(SavePassword.KEY_USERNAME,"");
							SavePassword
									.getSavePasswordInstance(mainActivity)
									.saveData(SavePassword.KEY_PASSWORD, "");
						}
						ToastUtil.show(mainActivity, "你好：" + xhxm);
						//读取学号
						mainActivity.studentNumber=mainActivity
								.etUsernNme
								.getText()
								.toString();

						StudentInfo
								.getInstance()
								.setStudentCode(mainActivity.studentNumber);
						StudentInfo
								.getInstance()
								.setLogin(true);
						HeartBeatPacketFactory
								.getHeartBeatPacketFactoryInstance()
								.addHeartBeatTask("education",
										CommonName.EDUCATION_SYSTEM + "xs_main.aspx?xh="
												+ StudentInfo.getInstance().getStudentCode());

						mainActivity.startService(new Intent(mainActivity, HeartBeatService.class));
						if(mainActivity.dialog!=null)
							mainActivity.dialog.dismiss();
					}else{
						ToastUtil.show(mainActivity,"填写的信息有误,请重新输入！");
						EducationDao.refleshCheckCode(mainActivity.getApplicationContext(), mainActivity.MyHandler);
						mainActivity.etCheckCode.setText("");
					}
					break;
				case CommonName.STATUS_GET_RESOURCE_ERROR:
					ToastUtil.show(mainActivity,"服务器访问异常");
					break;
				case CommonName.STATUS_RESOURCE_Table:
					//String content=(String)msg.obj;
					//AnalyticTable.getAnalyticTable(content);
					//LogUtils.d(TAG,content);

				case UpdateAppDao.UPDATE_APP_ERROR:
					ToastUtil.show(mainActivity,"版本检测异常");
					break;
				case UpdateAppDao.UPDATE_APP_OK:
					ToastUtil.show(mainActivity,"当前版本已最新");
					break;
				case UpdateAppDao.UPDATE_APP_CHECKED:
					UpdateAppBean info=(UpdateAppBean)msg.obj;
					UpdateDialog.ShowDialog(mainActivity,info);
				default:
					break;
			}

			super.handleMessage(msg);
		}
	}
	private Handler MyHandler =new EducationHandler(this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//向教务系统发出请求
		//EducationDao.getResource(CommonName.EDUCATION_SYSTEM, MyHandler);

		init();
		initEvents();
		callLoginDialog();
		//检测版本更新
		UpdateAppDao.UpdateAppDao(this,MyHandler);
	}

	private void init() {
		mFragmentManager = getSupportFragmentManager();
		mTabWidget = (MyTabWidget) findViewById(R.id.tab_widget);
	}

	private void initEvents() {
		mTabWidget.setOnTabSelectedListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		onTabSelected(mIndex);
		mTabWidget.setTabsDisplay(this, mIndex);
		mTabWidget.setIndicateDisplay(this, 3, true);
	}

	@Override
	protected void onDestroy() {
		//停止服务
		stopService(new Intent(this,HeartBeatService.class));
		AppManager.getAppManager().AppExit(this);
		super.onDestroy();
	}

	@Override
	public void onTabSelected(int index) {
		FragmentTransaction transaction = mFragmentManager.beginTransaction();
		hideFragments(transaction);
		switch (index) {
		case ConstantValues.HOME_FRAGMENT_INDEX:
			if (null == mHomeFragment) {
				mHomeFragment = new HomeFragment();
				String cookie =getIntent().getStringExtra("Cookie");
				LogUtils.d(TAG, cookie);
				mHomeFragment.setCookie(cookie);
				transaction.add(R.id.center_layout, mHomeFragment);
			} else {
				transaction.show(mHomeFragment);
			}
			break;
		case ConstantValues.COLLECT_FRAGMENT_INDEX:
			if (null == mCollectFragment) {
				mCollectFragment = new CollectFragment();
				mCollectFragment.sethandleMessageListenner(this);
				transaction.add(R.id.center_layout, mCollectFragment);
			} else {
				transaction.show(mCollectFragment);
			}
			break;
		case ConstantValues.SETTING_FRAGMENT_INDEX:
			if (null == mSettingFragment) {
				mSettingFragment = new SettingFragment();
				transaction.add(R.id.center_layout, mSettingFragment);
			} else {
				transaction.show(mSettingFragment);
			}
			break;

		default:
			break;
		}
		mIndex = index;
		transaction.commitAllowingStateLoss();
	}

	private void hideFragments(FragmentTransaction transaction) {
		if (null != mHomeFragment) {
			transaction.hide(mHomeFragment);
		}
		if (null != mCollectFragment) {
			transaction.hide(mCollectFragment);
		}
		if (null != mSettingFragment) {
			transaction.hide(mSettingFragment);
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		//
		// super.onSaveInstanceState(outState);
		outState.putInt("index", mIndex);
	}
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// super.onRestoreInstanceState(savedInstanceState);
		mIndex = savedInstanceState.getInt("index");
	}
	//为了防止非本校学生使用客户端，采用教务系统账号密码进行验证
	protected void callLoginDialog(){
		//创建AlertDialog
		final android.support.v7.app.AlertDialog.Builder mBuilder=new android.support.v7.app.AlertDialog.Builder(this);
		mBuilder.setTitle("登陆教务系统");
		//装在布局文件
		final View loginView=getLayoutInflater().inflate(R.layout.alert_login,null);
		etUsernNme=(EditText)loginView.findViewById(R.id.et_login_username);
		etPassWord=(EditText)loginView.findViewById(R.id.et_login_password);
		ivCheckCode=(ImageView)loginView.findViewById(R.id.iv_check_code);
		etCheckCode=(EditText)loginView.findViewById(R.id.et_login_checkcode);
		cbRememberPassword=(CheckBox)loginView.findViewById(R.id.cb_remember_password);
		//读取配置信息
		String sRememberPassword=SavePassword
				.getSavePasswordInstance(this)
				.loadData(SavePassword.KEY_SAVE_MESSAGE);
		//读取账号密码
		if("true".equals(sRememberPassword) ) {
			cbRememberPassword.setChecked(true);
			String username = SavePassword
					.getSavePasswordInstance(this)
					.loadData(SavePassword.KEY_USERNAME);
			if (!CommonUtils.isNull(username))
				etUsernNme.setText(username);
			String password = SavePassword
					.getSavePasswordInstance(this)
					.loadData(SavePassword.KEY_PASSWORD);
			if (!CommonUtils.isNull(password))
				etPassWord.setText(password);
		}else{
			String username = SavePassword
					.getSavePasswordInstance(this)
					.loadData("username");
			if (!CommonUtils.isNull(username))
				etUsernNme.setText(username);
		}
		ivCheckCode.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				EducationDao.refleshCheckCode(getApplicationContext(), MyHandler);
			}
		});
		mBuilder.setView(loginView);
		//
		mBuilder.setPositiveButton(R.string.login_ok, null);
		//取消操作则结束APP
		mBuilder.setNegativeButton(R.string.login_cancel, null);
		//AlertDialog窗口以外事件不触发
		mBuilder.setCancelable(false);
		dialog=mBuilder.create();

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch(keyCode)
		{
			case KeyEvent.KEYCODE_BACK:
				long secondTime = System.currentTimeMillis();
				if (secondTime - firstTime > 2000) {
					ToastUtil.show(this, "再按一次退出程序");
					firstTime = secondTime;//更新firstTime
					return true;
				} else {
					AppManager.getAppManager().AppExit(this);
				}
				break;
		}
		return super.onKeyDown(keyCode, event);
	}
}
