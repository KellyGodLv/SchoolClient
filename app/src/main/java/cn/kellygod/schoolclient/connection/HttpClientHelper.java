package cn.kellygod.schoolclient.connection;

import android.os.Handler;
import android.os.Message;

import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.conn.ClientConnectionRequest;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.ManagedClientConnection;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cn.kellygod.schoolclient.util.CommonName;
import cn.kellygod.schoolclient.util.CustomException;
import cn.kellygod.schoolclient.util.LogUtils;

/**
 * @author kellygod on 2016/11/4.
 */
public class HttpClientHelper {
    private static  HttpClientHelper mHttpClientHelper=null;
    private static HttpClient mHttpClient=SSLClient.getNewHttpClient();
    private static String charset="utf-8";
    private HttpClientHelper(){
       // mHttpClient.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, false);


    };
/**
 *  使用同步方法创建单例 线程安全
 *
 * */
    public static HttpClientHelper getHttpClientHelperInstance(){
        if(mHttpClientHelper!=null){

        }else {
            try {
                Thread.sleep(200);
                synchronized (HttpClientHelper.class){
                    if(mHttpClientHelper==null)
                        mHttpClientHelper = new HttpClientHelper();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        return mHttpClientHelper;
    }
/**
 *
 *  @param url post地址
 *  @param param 要post到服务器的数据的集合
 *  @param charset 设置编码方式  utf-8 GBK......
 *  @return HttpEntity 使用完必须关闭此流否则会造成httpClient的execute方法 阻塞 getContent.close();
 *  为了维护cookie所以将HttpClient以单例模式加载， 这样做的好处是让HttpClient自行维护cookie，开发者只
 *  需要发送心跳包就能保持常在线，缺点也很明显，一旦HttpEntity没有及时关闭就会造成HttpClient.execute()
 *  方法阻塞。
* */
    public HttpEntity doPost(String url,Map<String,String> param,String charset){
        if(mHttpClientHelper==null){
            return null;
        }
        Header[] mHeader=null;
        HttpPost mHttpPost=new HttpPost(url);
        //创建一个ArrayList用于存储请求头
        List<NameValuePair> list=new ArrayList<>();
        Iterator iterator=param.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String,String> elem= (Map.Entry<String, String>) iterator.next();
            list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));
        }
        UrlEncodedFormEntity entity=null;
        try {
            entity=new UrlEncodedFormEntity(list, charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        mHttpPost.setHeader("User-Agent", CommonName.User_Agent_Desktop);
        //设置超时时间
        mHttpPost.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
        mHttpPost.setEntity(entity);
        HttpResponse response=null;
        if(mHttpClient==null) {
            LogUtils.d("空指针","mHttpClient为空，可能是用户在使用的过程中切换网络导致的");
            return null;
        }
        //提交post数据
        try {
            response=mHttpClient.execute(mHttpPost);
//            Header[] headers=response.getAllHeaders();
//            for(Header header:headers){
//                LogUtils.d("Header",header.getName()+"----->"+header.getValue());
//            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        if(response==null){
            return null;
        }
        LogUtils.d("返回码：",""+response.getStatusLine().getStatusCode());
        if(response.getStatusLine().getStatusCode()!=200 ){
            return null;
        }
        HttpEntity resEntity = response.getEntity();
        return resEntity;

    }

    public HttpEntity doPost(String url,Map<String,String> param,String refer,String charset){
        if(mHttpClientHelper==null){
            return null;
        }
        Header[] mHeader=null;
        HttpPost mHttpPost=new HttpPost(url);
        //创建一个ArrayList用于存储请求头
        List<NameValuePair> list=new ArrayList<>();
        Iterator iterator=param.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String,String> elem= (Map.Entry<String, String>) iterator.next();
            list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));
        }
        UrlEncodedFormEntity entity=null;
        try {
            entity=new UrlEncodedFormEntity(list, charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        mHttpPost.setHeader("User-Agent", CommonName.User_Agent_Desktop);
        mHttpPost.setHeader("Referer",refer);
        //设置超时时间
        mHttpPost.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
        mHttpPost.getParams().setParameter("http.protocol.allow-circular-redirects", false);
        mHttpPost.getParams().setParameter("http.protocol.handle-redirects",true);
        mHttpPost.setEntity(entity);
        HttpResponse response=null;
        if(mHttpClient==null) {
            LogUtils.d("空指针","mHttpClient为空，可能是用户在使用的过程中切换网络导致的");
            return null;
        }
        //提交post数据
        try {
            response=mHttpClient.execute(mHttpPost);
//            Header[] headers=response.getAllHeaders();
//            for(Header header:headers){
//                LogUtils.d("Header",header.getName()+"----->"+header.getValue());
//            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        if(response==null){
            return null;
        }
        LogUtils.d("返回码：",""+response.getStatusLine().getStatusCode());
        if(response.getStatusLine().getStatusCode()!=200 ){
            return null;
        }
        HttpEntity resEntity = response.getEntity();
        return resEntity;

    }
/**
*
*  @param url post地址
*  @return HttpEntity 使用完必须关闭此流否则会造成httpClient的execute方法 阻塞 getContent.close();
*  为了维护cookie所以将HttpClient以单例模式加载， 这样做的好处是让HttpClient自行维护cookie，开发者只
*  需要发送心跳包就能保持常在线，缺点也很明显，一旦HttpEntity没有及时关闭就会造成HttpClient.execute()
*  方法阻塞。
*
* */
    public HttpEntity doGet(String url){
        if(mHttpClient==null){
            return null;
        }
        HttpGet httpGet=new HttpGet(url);
        //设置请求超时时间
        httpGet.setHeader("User-Agent", CommonName.User_Agent_Desktop);
        httpGet.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
        httpGet.getParams().setParameter("http.protocol.allow-circular-redirects", false);
        httpGet.getParams().setParameter("http.protocol.handle-redirects",true);
        HttpResponse response=null;
        if(mHttpClient==null){
            return null;
        }
        try {
            response=mHttpClient.execute(httpGet);
//            Header[] headers=response.getAllHeaders();
//            for(Header header:headers){
//                LogUtils.d("Header",header.getName()+"----->"+header.getValue());
//            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        LogUtils.d("返回码：",""+response.getStatusLine().getStatusCode());
        if(response==null){
            return null;
        }
        HttpEntity entity = response.getEntity();
        return entity;
    }
    /**
     *
     *  @param url post地址
     *  @return HttpEntity 使用完必须关闭此流否则会造成httpClient的execute方法 阻塞 getContent.close();
     *  为了维护cookie所以将HttpClient以单例模式加载， 这样做的好处是让HttpClient自行维护cookie，开发者只
     *  需要发送心跳包就能保持常在线，缺点也很明显，一旦HttpEntity没有及时关闭就会造成HttpClient.execute()
     *  方法阻塞。
     *
     * */
    public HttpEntity doGet(String url,String param){
        if(mHttpClient==null){
            return null;
        }
        HttpGet httpGet=new HttpGet(url);
        //设置请求超时时间
        httpGet.setHeader("User-Agent", CommonName.User_Agent_Desktop);
        httpGet.setHeader("Referer",param);
        httpGet.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
        httpGet.getParams().setParameter("http.protocol.allow-circular-redirects", false);
        httpGet.getParams().setParameter("http.protocol.handle-redirects",false);
        HttpResponse response=null;
        if(mHttpClient==null){
            return null;
        }
        try {
            response=mHttpClient.execute(httpGet);
//            Header[] headers=response.getAllHeaders();
//            for(Header header:headers){
//                LogUtils.d("Header",header.getName()+"----->"+header.getValue());
//            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        LogUtils.d("返回码：",""+response.getStatusLine().getStatusCode());
        if(response==null){
            return null;
        }
        HttpEntity entity = response.getEntity();
        return entity;
    }


    private void setCharSet(String charset){this.charset=charset;}
    private  String getCharSet(){return charset;}
}
