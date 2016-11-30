package cn.kellygod.schoolclient.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import cn.kellygod.schoolclient.R;
import cn.kellygod.schoolclient.util.CommonUtils;

/**
 *
 * @author kellygod
 *
 */
public class FeedBackActivity extends Activity implements OnClickListener {

	private TextView mTitleTv;
	private ImageView mBackImg;
	private ImageView mOkImg;
	private EditText mContentEdit;
	private EditText mMobileEdit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_feedback);
		
		init();
		initEvents();
	}
	
	private void init() {
		mTitleTv = (TextView) findViewById(R.id.title_tv);
		mTitleTv.setText(R.string.feedback);
		mBackImg = (ImageView) findViewById(R.id.back_img);
		mBackImg.setVisibility(View.VISIBLE);
		mOkImg = (ImageView) findViewById(R.id.right_img);
		mOkImg.setImageResource(R.drawable.check_mark_btn);
		mOkImg.setVisibility(View.VISIBLE);
		
		mContentEdit = (EditText) findViewById(R.id.content_edit);
		mMobileEdit = (EditText) findViewById(R.id.mobile_number_edit);
	}
	
	private void initEvents() {
		mBackImg.setOnClickListener(this);
		mOkImg.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back_img:
			//返回
			this.finish();
			break;
		case R.id.right_img:
			//提交
			CommonUtils.startShakeAnim(this, mContentEdit);

			this.finish();
			break;

		default:
			break;
		}
	}
	
}
