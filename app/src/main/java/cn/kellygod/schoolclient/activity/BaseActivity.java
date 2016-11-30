package cn.kellygod.schoolclient.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import cn.kellygod.schoolclient.util.AppManager;

public abstract class BaseActivity extends Activity{
	
	private boolean mAllowFullScreen =false;
	
	
	public void setScreenFull(boolean mAllowFullScreen ){
		this.mAllowFullScreen=mAllowFullScreen;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		if(mAllowFullScreen)
			  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		AppManager.getAppManager().addActivity(this);
		
	}
}
