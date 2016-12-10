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
import cn.kellygod.schoolclient.adapter.EducationExamTimeAdapter;
import cn.kellygod.schoolclient.connection.EducationDao;
import cn.kellygod.schoolclient.education.utils.EducationCode;
import cn.kellygod.schoolclient.education.utils.ExaminationRoomBean;
import cn.kellygod.schoolclient.education.utils.ExaminationRoomDao;
import cn.kellygod.schoolclient.education.utils.StudentInfo;
import cn.kellygod.schoolclient.connection.CommonName;
import cn.kellygod.schoolclient.main.activity.BaseActivity;
import cn.kellygod.schoolclient.util.ToastUtil;

/**
 * @author kellygod on 2016/11/21.
 */
public class ExamAddrActivity extends BaseActivity implements View.OnClickListener{
    private static class StudentExamTimeHandler extends Handler{
        WeakReference<ExamAddrActivity> mActivity;
        private String TAG="StudentExamTimeHandler";
        public StudentExamTimeHandler(ExamAddrActivity activity) {
            mActivity = new WeakReference<ExamAddrActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            ExamAddrActivity examAddrActivity =  mActivity.get();
            switch (msg.what){
                case CommonName.STATUS_RESOURCE_Table:
                    String html=(String)msg.obj;
                    examAddrActivity.list= ExaminationRoomDao.queryExaminationTime(html);
                    examAddrActivity.adapter.setData(examAddrActivity.list);
                    examAddrActivity.mListView.setAdapter(examAddrActivity.adapter);
                    examAddrActivity.adapter.notifyDataSetChanged();
                    break;
                case CommonName.STATUS_GET_RESOURCE_ERROR:
                    ToastUtil.show(examAddrActivity.getApplicationContext(),"获取信息失败");
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    }
    private StudentExamTimeHandler studentExamTimeHandler=new StudentExamTimeHandler(this);
    private EducationExamTimeAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<ExaminationRoomBean> list=null;
    private TextView mTitleTv;
    private ImageView mBackImg;
    private ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_education_exam_addr);
        EducationDao.getResource(CommonName.EDUCATION_SYSTEM + EducationCode.STUDENT_EXSM_ROOM_QUERY + "?xh="
                        + StudentInfo.getInstance().getStudentCode() + "&" + EducationCode.EXAMINATION_QUERY,
                CommonName.EDUCATION_SYSTEM + "xs_main.aspx?xh="
                        + StudentInfo.getInstance().getStudentCode(), studentExamTimeHandler);
        ToastUtil.show(this, "考场信息更新中");
        initViews();
        initEvents();
    }


    private void initViews(){
        //初始化title
        mTitleTv = (TextView) findViewById(R.id.title_tv);
        mTitleTv.setText(R.string.student_course_exam_addr);
        mBackImg = (ImageView) findViewById(R.id.back_img);
        mBackImg.setVisibility(View.VISIBLE);

        mListView=(ListView)findViewById(R.id.education_exam_addr_listView);
        adapter=new EducationExamTimeAdapter(getApplication().getBaseContext());
        mListView.setAdapter(adapter);
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.education_exam_addr_swipeRefreshLayout);
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
