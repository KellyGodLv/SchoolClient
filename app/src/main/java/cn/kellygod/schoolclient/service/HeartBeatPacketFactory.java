package cn.kellygod.schoolclient.service;

import java.util.HashMap;
import java.util.Map;

import cn.kellygod.schoolclient.util.CommonUtils;

/**
 * @author kellygod 2016/11/23.
 * 此类用来管理需要发送的心跳包url
 */
public class HeartBeatPacketFactory {

    public static String EDUCATION_SYSTEM       =   "";

    private final static HeartBeatPacketFactory mHeartBeatPacketFactory=new HeartBeatPacketFactory();
    private static Map<String,String> urlHeartBeatPacket=new HashMap<>();
    private HeartBeatPacketFactory(){}

    public static HeartBeatPacketFactory getHeartBeatPacketFactoryInstance(){
        return mHeartBeatPacketFactory;
    }

    public void addHeartBeatTask(String key,String url){
        if(CommonUtils.isNull(key) ||CommonUtils.isNull(url) )
            return;
        urlHeartBeatPacket.put(key,url);
    }

    public boolean removeHeartBeatTask(String key){
        if(CommonUtils.isNull(key) )
            return false;
        urlHeartBeatPacket.remove(key);
        return true;
    }
    public void removeAllHeartBeatTask(){
        urlHeartBeatPacket.clear();
    }

    public Map<String,String> getUrlHeartBeatPacket(){
        if(mHeartBeatPacketFactory==null)
            throw new NullPointerException("mHeartBeatPacketFactory is null");
        return urlHeartBeatPacket;
    }
}
