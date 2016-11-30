package cn.kellygod.schoolclient.education.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * @author kellygod 2016/11/25.
 */
public class ScoreDao {

    public static ArrayList<ScoreBean> queryScore(String education4html){
        ArrayList<ScoreBean> list=new ArrayList<>();
        Document doc;
        Element table=null;
        try {
            doc = Jsoup.parse(education4html);
            table=doc.getElementById("Datagrid1");
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        if(table==null)
            throw new NullPointerException("score table is null");

        Elements trs=table.select("tr");
        for(int i=1;i<trs.size();i++){
            Elements tds=trs.get(i).select("td");
            if(tds.size()!=16)
              throw new IndexOutOfBoundsException("列数：" + tds.size() + "  行数：" + trs.size());

            ScoreBean info=new ScoreBean();
            info.setXn(tds.get(0).text());
            info.setXq(tds.get(1).text());
            info.setKcdm(tds.get(2).text());
            info.setKcmc(tds.get(3).text());
            info.setKcxz(tds.get(4).text());
            info.setKcgs(tds.get(5).text());
            info.setXf(tds.get(6).text());
            info.setJd(tds.get(7).text());
            info.setCj(tds.get(8).text());
            info.setFxbj(tds.get(9).text());
            info.setBkcj(tds.get(10).text());
            info.setCxcj(tds.get(11).text());
            info.setXymc(tds.get(12).text());
            info.setBz(tds.get(13).text());
            info.setCxbj(tds.get(14).text());
            info.setKcywmc(tds.get(15).text());
            list.add(info);

        }

        return list;
    }
}
