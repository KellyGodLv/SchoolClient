package cn.kellygod.schoolclient.ndkentry;

import android.os.Handler;

/**
 * @author kellygod 2016/11/29.
 */
public class NativeLoad {
    static{
        System.loadLibrary("secretkey");
    }


    public static native boolean Login(Handler mHandler);
}
