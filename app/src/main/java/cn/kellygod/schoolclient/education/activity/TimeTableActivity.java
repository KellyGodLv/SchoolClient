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
import java.util.Map;

import cn.kellygod.schoolclient.R;
import cn.kellygod.schoolclient.adapter.EducationTimeTableAdapter;
import cn.kellygod.schoolclient.connection.EducationDao;
import cn.kellygod.schoolclient.education.utils.EducationCode;
import cn.kellygod.schoolclient.education.utils.StudentInfo;
import cn.kellygod.schoolclient.education.utils.TimeTableBean;
import cn.kellygod.schoolclient.education.utils.TimeTableDao;
import cn.kellygod.schoolclient.connection.CommonName;
import cn.kellygod.schoolclient.main.activity.BaseActivity;
import cn.kellygod.schoolclient.util.ToastUtil;

/**
 * @author kellygod on 2016/11/10.
 */

public class TimeTableActivity extends BaseActivity implements View.OnClickListener {
    private static class StudentTimeTableHandler extends Handler{
        WeakReference<TimeTableActivity> mActivity;
        private String TAG="StudentTimeTableHandler";
        public StudentTimeTableHandler(TimeTableActivity activity) {
            mActivity = new WeakReference<>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            TimeTableActivity timeTableActivity =mActivity.get();
            switch (msg.what){
                case CommonName.STATUS_RESOURCE_Table:
                    //获取网页字符串
                    String html = (String)msg.obj;
                    //传入网页解析接口 解析出课程表
                    timeTableActivity.maps = TimeTableDao.queryTimeTable(html);
                    timeTableActivity.adapter.setData(timeTableActivity.maps);
                    timeTableActivity.mListView.setAdapter(timeTableActivity.adapter);

                    timeTableActivity.adapter.notifyDataSetChanged();
                    break;
                case CommonName.STATUS_GET_RESOURCE_ERROR:
                    ToastUtil.show(timeTableActivity.getApplicationContext(),"获取信息失败");
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    }
    private StudentTimeTableHandler studentTimeTableHandler=new StudentTimeTableHandler(this);
    private EducationTimeTableAdapter adapter;
    private ArrayList<Map<String, TimeTableBean>> maps=null;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView mTitleTv;
    private ImageView mBackImg;
    private ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_education_table);
        EducationDao
                .getResource(CommonName.EDUCATION_SYSTEM + EducationCode.STUDENT_TABLE_TIME_QUERY + "?xh="
                                + StudentInfo.getInstance().getStudentCode() + "&" + EducationCode.TIMETABLE_QUERY,
                        CommonName.EDUCATION_SYSTEM + EducationCode.STEUDENT_MAIN + "?xh="
                                + StudentInfo.getInstance().getStudentCode(),
                        studentTimeTableHandler);
        ToastUtil.show(this, "课程表更新中");
        initViews();
        initEvents();
    }

    private void initViews(){
        //初始化title
        mTitleTv = (TextView) findViewById(R.id.title_tv);
        mTitleTv.setText(R.string.student_course_table);
        mBackImg = (ImageView) findViewById(R.id.back_img);
        mBackImg.setVisibility(View.VISIBLE);

        mListView=(ListView)findViewById(R.id.education_table_listView);
        adapter = new EducationTimeTableAdapter(getApplication().getBaseContext());
       // adapter.setData(maps);
        mListView.setAdapter(adapter);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
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

    private void initEvents(){
        mBackImg.setOnClickListener(this);
       // mOkImg.setOnClickListener(this);
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
}
