package cn.kellygod.schoolclient.connection;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import cn.kellygod.schoolclient.util.LogUtils;
/**
 *
 * @author kellygod
 * 该类为异步COOKIE用来维护webview 与 HttpClient 间的cookie同步
 *
 * */
public class SyncCookie {
	private final static String TAG="SyncCookie";
	public static void syncCookie(Context context,String url,String cookie){
		CookieSyncManager.createInstance(context);  
	    CookieManager cookieManager = CookieManager.getInstance();  
	    cookieManager.setAcceptCookie(true);  
	    //cookieManager.removeSessionCookie();//�Ƴ�  
	    ArrayList<String> cookieArray = getCookieArray(cookie);
	    for(String cookieString:cookieArray){	    	
	    	cookieManager.setCookie(url, cookieString);//cookies����HttpClient�л�õ�cookie  
	    }
	    LogUtils.d(TAG, "After inject cookie="+cookieManager.getCookie(url));
	    CookieSyncManager.getInstance().sync();  
	}
	 private static ArrayList<String> getCookieArray(String mcookie){
		 ArrayList<String> local_cookie =new ArrayList<String>();
		 if(mcookie==null)
			 LogUtils.e(TAG, "mcookie equals null!");
			String[] split = mcookie.split(";");
			for (String single_cookie:split){
				local_cookie.add(single_cookie);			
			}
			return local_cookie;
		}
}
