package cn.kellygod.schoolclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

import cn.kellygod.schoolclient.R;
import cn.kellygod.schoolclient.education.TimeTableBean;

/**
 * @author  on 2016/11/9.
 *
 * 课程表查询适配器
 */
public class EducationTimeTableAdapter extends BaseAdapter {
    private Context mContext;

    ArrayList<Map<String, TimeTableBean>> list = new ArrayList<Map<String, TimeTableBean>>();
    public EducationTimeTableAdapter(Context ctx) {
        this.mContext = ctx;
    }
    public void setData(ArrayList<Map<String, TimeTableBean>> list) {
        this.list = list;
    }
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
        //初始化组件
        ViewHolder viewHolder = null;
        final Map<String, TimeTableBean> map = (Map<String, TimeTableBean>) getItem(position);
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.lv_jw_stu_time_table,null);
            viewHolder.tv_time1=(TextView)convertView.findViewById(R.id.tv_time1);
            viewHolder.tv_time2=(TextView)convertView.findViewById(R.id.tv_time2);
            viewHolder.tv_week1=(TextView)convertView.findViewById(R.id.tv_week1);
            viewHolder.tv_week2=(TextView)convertView.findViewById(R.id.tv_week2);
            viewHolder.tv_week3=(TextView)convertView.findViewById(R.id.tv_week3);
            viewHolder.tv_week4=(TextView)convertView.findViewById(R.id.tv_week4);
            viewHolder.tv_week5=(TextView)convertView.findViewById(R.id.tv_week5);
            viewHolder.tv_week6=(TextView)convertView.findViewById(R.id.tv_week6);
            viewHolder.tv_week7=(TextView)convertView.findViewById(R.id.tv_week7);
            //装载
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }

        //时间表
        switch (position){
            case 0:
                viewHolder.tv_time1.setText("8:00");
                viewHolder.tv_time2.setText("8.45\n");
                break;
            case 1:
                viewHolder.tv_time1.setText("8:55");
                viewHolder.tv_time2.setText("9.40\n");
                break;
            case 2:
                viewHolder.tv_time1.setText("10:00\n");
                viewHolder.tv_time2.setText("10.45\n");
                break;
            case 3:
                viewHolder.tv_time1.setText("10:55\n");
                viewHolder.tv_time2.setText("11.40\n");
                break;
            case 4:
                viewHolder.tv_time1.setText("14:00\n");
                viewHolder.tv_time2.setText("14.45\n");
                break;
            case 5:
                viewHolder.tv_time1.setText("14:55\n");
                viewHolder.tv_time2.setText("15.40\n");
            case 6:
                viewHolder.tv_time1.setText("15:50\n");
                viewHolder.tv_time2.setText("16.35\n");
            case 7:
                viewHolder.tv_time1.setText("16:45\n");
                viewHolder.tv_time2.setText("17.30\n");
            case 8:
                viewHolder.tv_time1.setText("19:00\n");
                viewHolder.tv_time2.setText("19.45\n");
            case 9:
                viewHolder.tv_time1.setText("19:55\n");
                viewHolder.tv_time2.setText("20.40\n");
            case 10:
                viewHolder.tv_time1.setText("20:55\n");
                viewHolder.tv_time2.setText("21.30\n");
                break;
            default:
                break;
        }
        //初始化一行数据
        //过滤掉非课程名
        int minLength=5;
        if(map!=null) {
            TimeTableBean week1 = map.get("week1");
            if (week1.getTimeTable().length() > minLength)
                viewHolder.tv_week1.setText(week1.getTimeTable());
            else
                viewHolder.tv_week1.setText("");

            TimeTableBean week2 = map.get("week2");
            if (week1.getTimeTable().length() > minLength)
                viewHolder.tv_week2.setText(week2.getTimeTable());
            else
                viewHolder.tv_week2.setText("");
            TimeTableBean week3 = map.get("week3");
            if (week3.getTimeTable().length() > minLength)
                viewHolder.tv_week3.setText(week3.getTimeTable());
            else
                viewHolder.tv_week3.setText("");
            TimeTableBean week4 = map.get("week4");
            if (week4.getTimeTable().length() > minLength)
                viewHolder.tv_week4.setText(week4.getTimeTable());
            else
                viewHolder.tv_week4.setText("");
            TimeTableBean week5 = map.get("week5");
            if (week5.getTimeTable().length() > minLength)
                viewHolder.tv_week5.setText(week5.getTimeTable());
            else
                viewHolder.tv_week5.setText("");
            TimeTableBean week6 = map.get("week6");
            if (week6.getTimeTable().length() > minLength)
                viewHolder.tv_week6.setText(week6.getTimeTable());
            else
                viewHolder.tv_week6.setText("");
            TimeTableBean week7 = map.get("week7");
            if (week7.getTimeTable().length() > minLength)
                viewHolder.tv_week7.setText(week7.getTimeTable());
            else
                viewHolder.tv_week7.setText("");
        }
        return convertView;
    }

    private class ViewHolder {
        TextView tv_week1;
        TextView tv_week2;
        TextView tv_week3;
        TextView tv_week4;
        TextView tv_week5;
        TextView tv_week6;
        TextView tv_week7;
        TextView tv_time1;
        TextView tv_time2;
    }
}