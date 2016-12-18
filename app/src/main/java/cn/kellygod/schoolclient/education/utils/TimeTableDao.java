package cn.kellygod.schoolclient.education.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.kellygod.schoolclient.util.CommonUtils;
import cn.kellygod.schoolclient.util.LogUtils;

/**
 * @author kellygod on 2016/11/9.
 */

public class TimeTableDao {

    //将不规则课表数据转化成规则课表
    public static ArrayList<Map<String,TimeTableBean>> queryTimeTable(String education4html){
        ArrayList<Map<String,TimeTableBean>> list =new ArrayList<Map<String,TimeTableBean>>();
        Document doc;
        Element table6=null;
        try{
            doc= Jsoup.parse(education4html);
            table6 = doc.getElementById("Table6");
        }catch (IllegalArgumentException e){
            e.printStackTrace();
            return null;
        }catch (NullPointerException e){
            e.printStackTrace();
            return null;
        }
        String tmp[][]=getTable(table6);
        if(tmp==null)
            return null;
        String table[][]=transform(tmp);
        for(int i=0;i<11;i++) {
            Map<String,TimeTableBean> map=new HashMap<>();
            for (int j = 0; j < 7; j++) {
                int a=j+1;
                String str=table[i][j];
                map.put("week" + a, new TimeTableBean(j,str));
            }
            list.add(map);

        }
        return list;
    }

    private static String[][] getTable(Element table){
        String tableArray[][]={
                //制表还有很多方法
                //        1   2   3   4   5   6   7
                {"a","a","a","a","a","a","a","a","a"},
                {"a","a","a","a","a","a","a","a","a"},
                {"a","a","a","a","a","a","a","a","a"},
                {"a","a","a","a","a","a","a","a","a"},
                {"a","a","a","a","a","a","a","a","a"},
                {"a","a","a","a","a","a","a","a","a"},
                {"a","a","a","a","a","a","a","a","a"},
                {"a","a","a","a","a","a","a","a","a"},
                {"a","a","a","a","a","a","a","a","a"},
                {"a","a","a","a","a","a","a","a","a"},
                {"a","a","a","a","a","a","a","a","a"},
                {"a","a","a","a","a","a","a","a","a"},
                {"a","a","a","a","a","a","a","a","a"}
        };
        if(table==null)
            return null;
        Elements trs = table.select("tr");
        for(int i =0;i<trs.size();i++){
            Elements tds = trs.get(i).select("td");
            for(int j=0;j<tds.size();j++){
                String rowspan = tds.get(j).attr("rowspan");
                String colspan=tds.get(j).attr("colspan");
                String text="";
                //列判断
                if(CommonUtils.isNull(colspan))
                    colspan="1";
                if(CommonUtils.isNull(rowspan))
                    rowspan="1";
                int rowsvalue=Integer.parseInt(rowspan);
                int colvalue=Integer.parseInt(colspan);
                int tmp=0;
                //寻找第一个数据a
                for(tmp=0;tmp<9;tmp++){
                    //达到最大值退出
                    if(tableArray[i][tmp].equals("a"))
                        break;
                }
                //   tmp=tmp-1;
                //存储课程
                tableArray[i][tmp]=tds.get(j).text();
                //覆盖表格
                if(colvalue>1)
                    for(int x=1;x<colvalue;x++){
                        tableArray[i][j+colvalue-1]="t";
                    }


                //行判断
                if(rowsvalue>1){
                    for(int t=1;t<rowsvalue;t++){
                        tableArray[i+t][tmp]="t";
                    }
                }

                text =tds.get(j).text();
                LogUtils.d("AnalyticTable",text);
            }
        }
        for(String s[]:tableArray){
            for(String s1:s){
                System.out.print(s1+"---");
            }
            System.out.println("");
        }
        return tableArray;
    }

    private static String[][] transform(String[][] str){
        String table[][] = new String[11][7];
        for(int i=2;i<13;i++)
            for(int j=2;j<9;j++){
                table[i-2][j-2]=str[i][j];
            }
        return table;
    }
}
