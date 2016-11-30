package cn.kellygod.schoolclient.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.webkit.ClientCertRequest;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import cn.kellygod.schoolclient.R;
import cn.kellygod.schoolclient.connection.SyncCookie;
import cn.kellygod.schoolclient.util.AppManager;
import cn.kellygod.schoolclient.util.CommonName;
import cn.kellygod.schoolclient.util.CommonUtils;
import cn.kellygod.schoolclient.util.LogUtils;
import cn.kellygod.schoolclient.util.ToastUtil;

public class HomeFragment extends BaseFragment implements OnClickListener,OnKeyListener {

	public static final String TAG = "HomeFragment";
	private final String SSL_VPN_HOST="https://202.101.111.206/";
	private final String HOME_PAGE="www.ptu.edu.cn";
	private Activity mActivity;
	private TextView mTitleTv;
	private EditText et_url;
	private WebView webView;
	private Button bt_visit;
	private String _cookie="";

	public static HomeFragment newInstance() {
		HomeFragment homeFragment = new HomeFragment();

		return homeFragment;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.mActivity = activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_brower, container, false);
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initViews(view);
		initEvents();						
		//initWebView(getCookie());
		initWebView(";");
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		webView.loadUrl(HOME_PAGE);	
	}

	private void initViews(View view) {
		mTitleTv = (TextView) view.findViewById(R.id.title_tv);
		mTitleTv.setText(R.string.home);
		
		et_url = (EditText) view.findViewById(R.id.et_brower_search);
		webView = (WebView) view.findViewById(R.id.wv_print);
		bt_visit = (Button) view.findViewById(R.id.bt_brower_search);
		
	}
	
	private void initEvents(){
		bt_visit.setOnClickListener(this);
	}
	
	 private void initWebView(String param_cookie){
		 //
		 webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_NEVER_ALLOW);
		 webView.getSettings().setAllowFileAccess(true);
		 //启用java script
		 webView.getSettings().setJavaScriptEnabled(true);
		 //启用缓存
		 webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		 //
		 webView.getSettings().setAppCacheEnabled(true);
		 webView.getSettings().setDomStorageEnabled(true);
		 webView.getSettings().setDatabaseEnabled(true);
		 //设置User-Agent
		 webView.getSettings().setUserAgentString(CommonName.User_Agent);
		 //忽略https证书验证
		 webView.setWebViewClient(new WebViewClient() {
			 @Override
			 public boolean shouldOverrideUrlLoading(WebView view, String url) {
				 return super.shouldOverrideUrlLoading(view, url);
				// Log.e(TAG, "是否覆盖链接");
				// return true;
			 }

			 @Override
			 public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
				 // TODO Auto-generated method stub
				 Log.e(TAG, "证书错误 忽略证书校验");
				 handler.proceed();
			 }

			 @Override
			 public void onReceivedClientCertRequest(WebView view, ClientCertRequest request) {
				 Log.d(TAG,"证书请求");
				 request.ignore();
				// super.onReceivedClientCertRequest(view, request);
			 }

			 @Override
			 public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
				 Log.d(TAG, request.getUrl().toString());

				 super.onReceivedError(view, request, error);
			 }

			 @Override
			 public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

			 }
		 });

		 webView.setWebChromeClient(new WebChromeClient() {


		 });
		 //启用下载
		 webView.setDownloadListener(new DownloadListener() {

			 @Override
			 public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
										 long contentLength) {
				 // TODO Auto-generated method stub
				 LogUtils.d(TAG, "download 中" + url);
				 Uri uri = Uri.parse(url);
				 Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				 startActivity(intent);
			 }
		 });
		 //

		 webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		 webView.getSettings().setLoadWithOverviewMode(true);
		 //设置返回键监听
		 webView.setOnKeyListener(this);

		 //设置异步cookie
		 //SyncCookie.syncCookie(mActivity.getApplicationContext(), SSL_VPN_HOST, param_cookie);
		 	 		 
		 
		
	 }

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	public String getFragmentName() {
		return TAG;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.bt_brower_search:
			//webView.loadUrl(cat_URL_string(et_url.getText().toString()));
			String urlStr="";
			try {
				urlStr = URLEncoder.encode(et_url.getText().toString(), "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			webView.loadUrl("http://202.101.111.205:8082/search?xc=3&kw="+urlStr);
			et_url.setText("");
		}

	}

	public String setCookie(String cookie){
		return this._cookie=cookie;
	}
	private String getCookie(){
		return this._cookie;
	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		if(event.getAction()==KeyEvent.ACTION_DOWN){
			if(keyCode==KeyEvent.KEYCODE_BACK && webView.canGoBack()){
				webView.goBack();
				return true;
			}
		}
		return false;
	}
}
