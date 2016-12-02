package cn.kellygod.schoolclient.ndk;

import android.os.Handler;

/**
 * @author kellygod 2016/11/29.
 */
public class NativeLoad {
    static{
        System.loadLibrary("secretkey");
    }

    public static void LoginVPN(final Handler mHandler){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Login(mHandler);
            }
        }).start();
    }
    /***
     *  @param mHandler 用来接收消息的Handler
     *  @return  本地执行正常返回true ,否则返回false
     */
    public static native boolean Login(final Handler mHandler);
}
