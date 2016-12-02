package cn.kellygod.schoolclient.main.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import cn.kellygod.schoolclient.R;
import cn.kellygod.schoolclient.connection.IEducaationLogin;
import cn.kellygod.schoolclient.education.activity.CourseScoreActivity;
import cn.kellygod.schoolclient.education.activity.ExamAddrActivity;
import cn.kellygod.schoolclient.education.activity.TimeTableActivity;
import cn.kellygod.schoolclient.connection.CommonName;
import cn.kellygod.schoolclient.education.utils.StudentInfo;
import cn.kellygod.schoolclient.util.ToastUtil;

/**
 *
 * 	@author kellygod
 *
 * */
public class CollectFragment extends BaseFragment implements View.OnClickListener {
	private long firstTime=0;
	private IEducaationLogin mIEducaationLogin;
	public void sethandleMessageListenner(IEducaationLogin I){
		mIEducaationLogin=I;
	}

	/**
	 *
	 * 消息监听器
	 *
	 * */
	private static class EducationHandler extends Handler{

		private static final String TAG="EducationHandler";
		WeakReference<CollectFragment> mActivity;
		public EducationHandler(CollectFragment activity) {
			mActivity = new WeakReference<CollectFragment>(activity);
		}
		@Override
		public void handleMessage(Message msg) {
			CollectFragment collectFragment=mActivity.get();
			switch (msg.what){
				case CommonName.STATUS_GET_RESOURCE_ERROR:
					ToastUtil.show(collectFragment.getContext(),"获取资源出错，请检测网络");
					break;
				case CommonName.STATUS_GET_RESOURCE_OK:

			}

			super.handleMessage(msg);
		}
	}
	private Handler educationHandler=new EducationHandler(this);
	private static final String TAG = "CollectFragment";
	private Activity mActivity;
	private TextView mTitleTv;
	//课表查询
	private RelativeLayout mEducationTableLayout;
	//成绩查询
	private RelativeLayout mEducationScoreLayout;
	//考场查询
	private RelativeLayout mEducationExaminationLayout;
	//社区分查询
	private RelativeLayout mEducationCommunityLayout;
	//图书馆借阅查询
	private Button mEducationLibraryLayout;

	public static CollectFragment newInstance() {
		CollectFragment collectFragment = new CollectFragment();

		return collectFragment;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.mActivity=activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_education, container,
				false);
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initViews(view);
		initEvents();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	private void initViews(View view) {
		mTitleTv = (TextView) view.findViewById(R.id.title_tv);
		mTitleTv.setText(R.string.collect);

		mEducationTableLayout=(RelativeLayout)view.findViewById(R.id.education_table_query);
		mEducationScoreLayout=(RelativeLayout)view.findViewById(R.id.education_score_query);
		mEducationExaminationLayout=(RelativeLayout)view.findViewById(R.id.education_examination_room_query);
		mEducationCommunityLayout=(RelativeLayout)view.findViewById(R.id.education_community_score_query);
		mEducationLibraryLayout=(Button)view.findViewById(R.id.education_library_query);
	}

	private void initEvents(){
		mEducationTableLayout.setOnClickListener(this);
		mEducationScoreLayout.setOnClickListener(this);
		mEducationExaminationLayout.setOnClickListener(this);
		mEducationCommunityLayout.setOnClickListener(this);
		mEducationLibraryLayout.setOnClickListener(this);
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
		if(StudentInfo.getInstance().isLogin()) {
			switch (v.getId()) {
				//查询课表
				case R.id.education_table_query:
					startActivity(new Intent(mActivity, TimeTableActivity.class));
					break;
				//查询成绩
				case R.id.education_score_query:
					startActivity(new Intent(mActivity, CourseScoreActivity.class));
					break;
				case R.id.education_examination_room_query:
					startActivity(new Intent(mActivity, ExamAddrActivity.class));
					break;
				case R.id.education_library_query:
					break;
				default:
					break;
			}
		}else{
			mIEducaationLogin.showEducationDialog("login");
		}
	}
}
