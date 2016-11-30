package cn.kellygod.schoolclient.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Map;

import cn.kellygod.schoolclient.connection.HttpClientHelper;
import cn.kellygod.schoolclient.util.CommonUtils;
import cn.kellygod.schoolclient.util.LogUtils;

/**
 * 该服务用于管理网络服务，定时发送心跳包，防止客户端长期不访问服务器，客户端cookie失效
 *
 * @author kellygod 2016/11/14.
 */
public class HeartBeatService extends Service implements Runnable{
    private final static String TAG ="HeartBeatService";
    public boolean HeartBeatPacket=true;
    private final HeartBeatPacketFactory mHeartBeatPacketFactory=HeartBeatPacketFactory.getHeartBeatPacketFactoryInstance();
    @Override
    public void run() {
        LogUtils.d(TAG,"心跳包线程已启动");
        Map<String, String> urlHeartBeatPacket = mHeartBeatPacketFactory.getUrlHeartBeatPacket();
        while(HeartBeatPacket){
            int size=urlHeartBeatPacket.size();
            if(size==0){
                LogUtils.d(TAG,"无心跳包发送");
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    HeartBeatPacket=false;
                }
                continue;
            }
            for(String url:urlHeartBeatPacket.values()){
                //发送心跳包
                sendHeartBeatPacket(url);

                try {
                    Thread.sleep(60000/size);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    HeartBeatPacket=false;
                }
            }
            LogUtils.d(TAG,"心跳包已发送完毕");
        }


    }


    @Override
    public void onCreate() {
        Thread mThread=new Thread(this);
        mThread.start();
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
      //  mHeartBeatPacketFactory.addHeartBeatTask(intent.getExtras().getString(""),"");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new Binder();
    }

    private void sendHeartBeatPacket(String url){
        HttpEntity httpEntity = HttpClientHelper
                .getHttpClientHelperInstance()
                .doGet(url);
        if(httpEntity==null){
            LogUtils.d(TAG,"心跳包发送失败");
            return;
        }
        try {
            httpEntity.getContent().close();
            LogUtils.d(TAG,"心跳包发送成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        LogUtils.d(TAG,"心跳包已经销毁");
        HeartBeatPacket=false;
        super.onDestroy();
    }
}

