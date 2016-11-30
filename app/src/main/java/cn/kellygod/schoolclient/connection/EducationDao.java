package cn.kellygod.schoolclient.connection;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import cn.kellygod.schoolclient.util.CommonUtils;
import cn.kellygod.schoolclient.util.LogUtils;

/**
 * @author  kellygod
 *
 */
public class EducationDao {
    public static final String TAG="EducationDao";
    private static HttpClientHelper mHttpClientHelper=HttpClientHelper.getHttpClientHelperInstance();

    /**
     *  @param username     用户名
     *  @param password     用户密码
     *  @param checkCode    验证码
     *  @param handler      用于接收的Handler
     *
     * */
    public static void login(final String username, final String password, final String checkCode,final Handler handler){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg=new Message();
                Map<String,String> mParam=new HashMap<>();
                //Post参数信息
                mParam.put("__VIEWSTATE","dDwyODE2NTM0OTg7Oz4zOAT3uOyNHJrN//ytI6OcAHzLRg==");
                //                        dDwyODE2NTM0OTg7Oz4zOAT3uOyNHJrN//ytI6OcAHzLRg==
                //                        dDw2ODkyMjQwMDI7Oz5/wbzuPJ2zuBpy86iXGB+8V4HHjA==
                mParam.put("txtUserName",username);
                mParam.put("TextBox2",password);
                mParam.put("txtSecretCode",checkCode);
                mParam.put("RadioButtonList1","学生");
                mParam.put("Button1","");
                mParam.put("lbLanguage","");
                mParam.put("hidPdrs","");
                mParam.put("hidsc","");
                HttpEntity httpEntity=mHttpClientHelper.doPost(CommonName.EDUCATION_SYSTEM,mParam,"GBK");
               // 使用内网直接链接
             //   HttpEntity httpEntity=mHttpClientHelper.doPost("http://172.16.8.201/default2.aspx",mParam,"GBK");
                if(httpEntity==null){
                    msg.what= CommonName.STATUS_COMMON_ERROR;
                    handler.sendMessage(msg);

                    return ;
                }
                String str="";
                    try {
                        str+= EntityUtils.toString(httpEntity, "gb2312");
                     //   System.out.print(str);
                      //  LogUtils.d(TAG,str);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                msg.obj=str;
                msg.what=CommonName.STATUS_COMMON_OK;
                handler.sendMessage(msg);
            }
        }).start();
    }

    public static void refleshCheckCode(final Context context,final Handler handler){

        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg=new Message();
                HttpEntity httpEntity = mHttpClientHelper.doGet(CommonName.EDUCATION_CHECKCODE);
               // HttpEntity httpEntity1 = mHttpClientHelper.doGet("http://172.16.8.201");
                if(httpEntity!=null){
                    try {
                        InputStream content = httpEntity.getContent();
                        savePicToDisk(content, context.getApplicationInfo().dataDir, "/CheckCode.gif");
                        msg.what=CommonName.STATUS_CHECK_CODE_OK;
                        handler.sendMessage(msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                        msg.what=CommonName.STATUS_CHECK_CODE_ERROR;
                        handler.sendMessage(msg);
                    }
                }
            }
        }).start();
    }

    public static void getResource(final String url, final Handler handler){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg =new Message();
                if(CommonUtils.isNull(url)){
                    msg.what=CommonName.STATUS_GET_RESOURCE_ERROR;
                    handler.sendMessage(msg);
                    return;
                }
                HttpEntity httpEntity=mHttpClientHelper.doGet(url);
                if(httpEntity==null){
                    msg.what=CommonName.STATUS_GET_RESOURCE_ERROR;
                    handler.sendMessage(msg);
                    return;
                }
                String str="";
                    try {
                        str+= EntityUtils.toString(httpEntity, "gb2312");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                LogUtils.d("字符串长度为：",Integer.toString(str.length()));
                // String content=new String(sb);
                if(str.length() > 4000) {
                    for(int i=0;i<str.length();i+=4000){
                        if(i+4000<str.length())
                            Log.i("rescounter"+i,str.substring(i, i+4000));
                        else
                            Log.i("rescounter"+i,str.substring(i, str.length()));
                    }
                } else
                    Log.i("resinfo",str);

                String content=str;
                msg.what=CommonName.STATUS_GET_RESOURCE_OK;
                msg.obj=content;
                handler.sendMessage(msg);

            }
        }).start();
    }

    public static void getResource(final String url,final String param, final Handler handler){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg =new Message();
                if(CommonUtils.isNull(url)){
                    msg.what=CommonName.STATUS_GET_RESOURCE_ERROR;
                    handler.sendMessage(msg);
                    return;
                }
                HttpEntity httpEntity=mHttpClientHelper.doGet(url,param);
                if(httpEntity==null){
                    msg.what=CommonName.STATUS_GET_RESOURCE_ERROR;
                    handler.sendMessage(msg);
                    return;
                }
                String str="";
                try {
                    str+= EntityUtils.toString(httpEntity, "gb2312");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                LogUtils.d("字符串长度为：",Integer.toString(str.length()));
                String content=str;
                msg.what=CommonName.STATUS_RESOURCE_Table;
                msg.obj=content;
                handler.sendMessage(msg);

            }
        }).start();
    }

    /**
     *
     *  @param url      查询成绩的url（包含完整的参数连接）
     *  @param Value    查询方式
     *  @param handler  Handler用于返回消息
     *
     * */
    public static void postResource(final String url, final String Value,final Handler handler){

        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg =new Message();
                Map<String,String> param=new HashMap<String, String>();
                param.put("Button2",Value);

                if(CommonUtils.isNull(url)){
                    msg.what=CommonName.STATUS_GET_RESOURCE_ERROR;
                    handler.sendMessage(msg);
                    return;
                }
                HttpEntity httpEntity=mHttpClientHelper.doPost(url,param,url,"GBK");
                String __VIEWSTATE="";
                if(httpEntity==null) {
                    LogUtils.e(TAG, "httpEntity is null");
                    msg.what = CommonName.STATUS_GET_RESOURCE_ERROR;
                    handler.sendMessage(msg);
                    return;
                }
                try {
                    String content =EntityUtils.toString(httpEntity,"bg2312");
                    __VIEWSTATE=Jsoup.parse(content).select("input[name=__VIEWSTATE]").val();
                  //  System.out.print(content);
                } catch (IOException e) {
                    e.printStackTrace();
                    msg.what=CommonName.STATUS_GET_RESOURCE_ERROR;
                    handler.sendMessage(msg);
                    return;
                }
                param.put("__VIEWSTATE",__VIEWSTATE);
                httpEntity=mHttpClientHelper.doPost(url,param,url,"GBK");
                if(httpEntity==null) {
                    LogUtils.e(TAG, "httpEntity is null");
                    msg.what = CommonName.STATUS_GET_RESOURCE_ERROR;
                    handler.sendMessage(msg);
                    return;
                }
                try {
                    String content =EntityUtils.toString(httpEntity,"bg2312");
                   // System.out.print(content);
                    msg.what=CommonName.STATUS_RESOURCE_Table;
                    msg.obj=content;
                    handler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                    msg.what=CommonName.STATUS_GET_RESOURCE_ERROR;
                    handler.sendMessage(msg);
                    return;
                }

            }
        }).start();
    }
    /**
     * 将图片写到 硬盘指定目录下
     * @param in            图片输入流
     * @param dirPath       保存路径
     * @param filePath      文件名
     */
    private static void savePicToDisk(InputStream in, String dirPath,
                                      String filePath) throws IOException{

        File dir = new File(dirPath);
        if (dir == null || !dir.exists()) {
            dir.mkdirs();
        }

        //文件真实路径
        String realPath = dirPath.concat(filePath);
        File file = new File(realPath);
        if(file.exists())
            file.delete();
        if (file == null || !file.exists()) {
            file.createNewFile();
        }

        FileOutputStream fos = new FileOutputStream(file);
        byte[] buf = new byte[1024];
        int len = 0;

        while ((len = in.read(buf)) !=-1) {
            fos.write(buf, 0, len);
        }
        fos.flush();
        fos.close();
        in.close();

    }
}
