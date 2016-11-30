package cn.kellygod.schoolclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.kellygod.schoolclient.R;
import cn.kellygod.schoolclient.education.utils.ScoreBean;

/**
 * @author kellygod 2016/11/25.
 */
public class EducationScoreAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<ScoreBean> list=new ArrayList<>();
    public EducationScoreAdapter(Context context){mContext=context;}
    public void setData(ArrayList<ScoreBean> list){ this.list=list;}
    @Override
    public int getCount() { return list==null?0:list.size(); }

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
        ViewHolder viewHolder=null;
        ScoreBean mScoreBean=(ScoreBean)getItem(position);
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.lv_jw_stu_score,null);
            viewHolder.tvSchoolYear=(TextView)convertView.findViewById(R.id.tv_score_school_year);
            viewHolder.tvTerm=(TextView)convertView.findViewById(R.id.tv_score_term);
            viewHolder.tvCourseName=(TextView)convertView.findViewById(R.id.tv_score_course_name);
            viewHolder.tvScore=(TextView)convertView.findViewById(R.id.tv_score_grade);
            viewHolder.tvFixScore=(TextView)convertView.findViewById(R.id.tv_score_fix_grade);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }
        viewHolder.tvSchoolYear.setText(mScoreBean.getXn());
        viewHolder.tvTerm.setText(mScoreBean.getXq());
        //重修检测
        if(isNumber(mScoreBean.getCxbj()) && Integer.valueOf(mScoreBean.getCxbj())==1)
            viewHolder.tvCourseName.setText("(重修)" + mScoreBean.getKcmc());
        else
            viewHolder.tvCourseName.setText(mScoreBean.getKcmc());

            viewHolder.tvScore.setText(mScoreBean.getCj());
            viewHolder.tvFixScore.setText(mScoreBean.getBkcj());

        return convertView;
    }

    private boolean isNumber(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }
    private class ViewHolder{
        TextView tvSchoolYear;
        TextView tvTerm;
        TextView tvCourseName;
        TextView tvScore;
        TextView tvFixScore;
    }
}
