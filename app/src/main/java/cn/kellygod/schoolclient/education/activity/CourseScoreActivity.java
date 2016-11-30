package cn.kellygod.schoolclient.education.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import cn.kellygod.schoolclient.R;
import cn.kellygod.schoolclient.adapter.EducationScoreAdapter;
import cn.kellygod.schoolclient.connection.EducationDao;
import cn.kellygod.schoolclient.education.utils.EducationCode;
import cn.kellygod.schoolclient.education.utils.ScoreBean;
import cn.kellygod.schoolclient.education.utils.ScoreDao;
import cn.kellygod.schoolclient.education.utils.StudentInfo;
import cn.kellygod.schoolclient.connection.CommonName;
import cn.kellygod.schoolclient.main.activity.BaseActivity;
import cn.kellygod.schoolclient.util.ToastUtil;

/**
 * @author kellygod 2016/11/25.
 */

public class CourseScoreActivity extends BaseActivity implements View.OnClickListener{

    private static class StudentScoreHandler extends Handler {
        WeakReference<CourseScoreActivity> mActivity;
        private String TAG="StudentSocreHandler";
        public StudentScoreHandler(CourseScoreActivity activity){
            mActivity=new WeakReference<CourseScoreActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            CourseScoreActivity courseScoreActivity=mActivity.get();
            switch (msg.what){
                case CommonName.STATUS_RESOURCE_Table:
                    String html=(String)msg.obj;
                    courseScoreActivity.list= ScoreDao.queryScore(html);
                    courseScoreActivity.adapter.setData(courseScoreActivity.list);
                    courseScoreActivity.mListView.setAdapter(courseScoreActivity.adapter);

                    courseScoreActivity.adapter.notifyDataSetChanged();
                    ToastUtil.show(courseScoreActivity.getApplicationContext(), "期末成绩已更新");
                    break;
                case CommonName.STATUS_GET_RESOURCE_ERROR:
                    ToastUtil.show(courseScoreActivity.getApplicationContext(), "网络请求异常");
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    }
    private StudentScoreHandler mStudentScoreHandler=new StudentScoreHandler(this);
    private EducationScoreAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<ScoreBean> list =null;
    private TextView mTitleTv;
    private ImageView mBackImg;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_education_exam_socre);
        EducationDao.postResource(CommonName.EDUCATION_SYSTEM + EducationCode.STUDENT_SCORE_QUERY + "?xh="
                        + StudentInfo.getInstance().getStudentCode() + "&" + EducationCode.SCORE_QUERY,
                EducationCode.RECORD_SCORE_QUERY_BUTTON,
                mStudentScoreHandler);
        initViews();
        initEvents();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                this.finish();
                break;
            default:
                break;
        }
    }
    private void initViews(){
        //初始化title
        mTitleTv = (TextView) findViewById(R.id.title_tv);
        mTitleTv.setText(R.string.student_course_score);
        mBackImg = (ImageView) findViewById(R.id.back_img);
        mBackImg.setVisibility(View.VISIBLE);

        mListView=(ListView)findViewById(R.id.educaation_score_listView);
        adapter=new EducationScoreAdapter(getApplication().getBaseContext());
        mListView.setAdapter(adapter);
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.educaation_score_swipeRefreshLayout);
        // 设置卷内的颜色
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        // 设置下拉刷新监听
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 停止刷新动画
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
    private void initEvents(){mBackImg.setOnClickListener(this);}
}
