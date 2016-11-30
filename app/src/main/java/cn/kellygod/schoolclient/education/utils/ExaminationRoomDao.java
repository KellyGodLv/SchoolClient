package cn.kellygod.schoolclient.education.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * @author kellygod 2016/11/14.
 */
public class ExaminationRoomDao {
    public static ArrayList<ExaminationRoomBean> queryExaminationTime(String education4html){
        ArrayList<ExaminationRoomBean> list=new ArrayList<ExaminationRoomBean>();
        Document doc;
        Element table=null;
        try {
            doc = Jsoup.parse(education4html);
            //获取考场表格 ID直接查看网页源码即可得到
            table=doc.getElementById("DataGrid1");
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        //因为单元格不需要合并，所以直接遍历所有单元格就能得到所有的考场信息
        if(table==null)
            throw new NullPointerException();
        //得到所有行
        Elements trs=table.select("tr");
        //第一行省去
        //<td>选课课号</td><td>课程名称</td><td>姓名</td><td>考试时间</td><td>考试地点</td><td>考试形式
        // </td><td>座位号</td><td>校区</td>
        for(int i=1;i<trs.size();i++){
            //得到每一行的所有列
            Elements tds=trs.get(i).select("td");
            if(tds.size()!=8) {
              //  System.out.print(education4html);
                throw new IndexOutOfBoundsException("列数：" + tds.size() + "  行数：" + trs.size());
            }
            //使用arraylist存储表格
            ExaminationRoomBean info=new ExaminationRoomBean();
            //选课课号
            info.setCourseCode(tds.get(0).text());
            //课程名称
            info.setCourseName((tds.get(1).text()));
            //姓名
            info.setUserName(tds.get(2).text());
            //考试时间
            info.setExamTime(tds.get(3).text());
            //考试地点
            info.setExamAddr(tds.get(4).text());
            //考试形式
            info.setExamType(tds.get(5).text());
            //座位号
            info.setSeatNum(tds.get(6).text());
            //校区
            info.setCampusAddr(tds.get(7).text());
            //装载
            list.add(info);
        }
        return list;
    }
}
