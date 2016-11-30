package cn.kellygod.schoolclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cn.kellygod.schoolclient.R;
import cn.kellygod.schoolclient.education.ExaminationRoomBean;

/**
 * @author kellygod 2016/11/21.
 *
 * 考场查询适配器
 */
public class EducationExamTimeAdapter extends BaseAdapter{
    private Context mContext;
    ArrayList<ExaminationRoomBean> list =new ArrayList<>();

    public EducationExamTimeAdapter(Context ctx){this.mContext=ctx;}
    public void setData(ArrayList<ExaminationRoomBean> list){ this.list=list;}
    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder=null;
        ExaminationRoomBean mExaminationRoomBean =(ExaminationRoomBean) getItem(position);
        if(convertView==null){
            mViewHolder=new ViewHolder();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.lv_jw_stu_exam_time,null);
            mViewHolder.tvCourse=(TextView)convertView.findViewById(R.id.tv_course_name);
            mViewHolder.tvExamTime=(TextView)convertView.findViewById(R.id.tv_course_exam_time);
            mViewHolder.tvExamAddr=(TextView)convertView.findViewById(R.id.tv_course_exam_addr);
            mViewHolder.tvSeatNum=(TextView)convertView.findViewById(R.id.tv_course_exam_seat_num);

            convertView.setTag(mViewHolder);
        }else{
            mViewHolder=(ViewHolder)convertView.getTag();
        }

        mViewHolder.tvCourse.setText(mExaminationRoomBean.getCourseName());
        mViewHolder.tvExamTime.setText(mExaminationRoomBean.getExamTime());
        mViewHolder.tvExamAddr.setText(mExaminationRoomBean.getExamAddr());
        mViewHolder.tvSeatNum.setText(mExaminationRoomBean.getSeatNum());

        return convertView;
    }


    private class ViewHolder{
        TextView tvCourse;
        TextView tvExamTime;
        TextView tvExamAddr;
        TextView tvSeatNum;
    }
}