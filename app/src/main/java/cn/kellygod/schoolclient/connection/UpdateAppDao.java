package cn.kellygod.schoolclient.connection;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Xml;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

import cn.kellygod.schoolclient.R;
import cn.kellygod.schoolclient.util.CommonUtils;
import cn.kellygod.schoolclient.util.LogUtils;

/**
 * @author kellygod 2016/11/27.
 */
public class UpdateAppDao {
    //检测错误
    public static final int UPDATE_APP_ERROR    =   0x000010;
    //没有检测到新版本
    public static final int UPDATE_APP_OK       =   0x000011;
    //检测到新版本
    public static final int UPDATE_APP_CHECKED  =   0x000012;
    //版本检测接口
    public static void UpdateAppDao(final Context context,final Handler handler){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg=new Message();
                UpdateAppBean info= getUpdateInfo();
                if(info ==null){
                    msg.what=UPDATE_APP_ERROR;
                    handler.sendMessage(msg);
                    return ;
                }
                String version = CommonUtils.softVersion(context);
                if(!version.equals(info.getVersion())){
                    msg.what=UPDATE_APP_CHECKED;
                    msg.obj=info.getUrl();
                    handler.sendMessage(msg);
                    return ;
                }

                msg.what=UPDATE_APP_OK;
                handler.sendMessage(msg);
            }
        }).start();
    }

    private static UpdateAppBean getUpdateInfo()  {
        Document document=null;
        try {
            document = Jsoup.connect("http://kellygod.cn/update.xml").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        UpdateAppBean info =new UpdateAppBean();
        Element version = document.getElementById("version");
        Element url = document.getElementById("url");
        LogUtils.d("UpdateAppDao", version.text()+"---"+url.text());
        info.setVersion(version.text());
        info.setUrl(url.text());

        return info;
    }
}
