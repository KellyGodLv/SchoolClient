package cn.kellygod.schoolclient.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author kellygod 2016/11/24.
 */
public class SavePassword {

    public static final String KEY_USERNAME=    "username";

    public static final String KEY_PASSWORD=    "password";

    public static final String KEY_SAVE_MESSAGE="savemessage";

    private static SavePassword mSavePassword=null;
    private SharedPreferences sharedPreferences ;
    SavePassword(Context context){
        sharedPreferences =context. getSharedPreferences("password", Context.MODE_PRIVATE);
    }
    public static SavePassword getSavePasswordInstance(Context context){
        if(mSavePassword==null){
            mSavePassword=new SavePassword(context);
        }
        return mSavePassword;
    }
    public void saveData(String key,String value){
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(key,value);
        edit.commit();
    }
    public String loadData(String key){
        return sharedPreferences.getString(key,"");

    }
}
