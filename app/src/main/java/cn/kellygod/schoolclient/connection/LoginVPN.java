package cn.kellygod.schoolclient.connection;

import android.os.Handler;
import android.os.Message;

import org.apache.http.HttpEntity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author kellygod on 2016/11/4.
 */
public class LoginVPN {
    public static final String TAG="LoginVPN";
    private static HttpClientHelper mHttpClientHelper=HttpClientHelper.getHttpClientHelperInstance();
    /**
     *  该函数为反射调用，在原生层调用此方法
     *  @param username 用户名
     *  @param password 密码
     *  @param handler  Handler对象
     *
     * */
    public static void login(final String username,final String password,final Handler handler){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg=new Message();
                Map<String,String> mParam=new HashMap<>();
                mParam.put("svpn_name",username);
                mParam.put("svpn_password",password);
                HttpEntity httpEntity = mHttpClientHelper.doGet(CommonName.SSL_VPN_URL);
                if(httpEntity==null){
                    msg.what=CommonName.STATUS_COMMON_ERROR;
                    handler.sendMessage(msg);
                    return;
                }
                try {
                    httpEntity.getContent().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                httpEntity=null;
                httpEntity=mHttpClientHelper.doPost(CommonName.SSL_VPN_LOGIN,mParam,"utf-8");
                if(httpEntity==null) {
                    msg.what = CommonName.STATUS_COMMON_ERROR;
                    handler.sendMessage(msg);
                    return;
                }
                try {
                    httpEntity.getContent().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                msg.what=CommonName.STATUS_COMMON_OK;
                handler.sendMessage(msg);

            }
        }).start();
    }

}
