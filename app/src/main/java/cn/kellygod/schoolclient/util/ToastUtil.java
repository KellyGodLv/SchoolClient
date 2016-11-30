package cn.kellygod.schoolclient.util;

import android.content.Context;
import android.widget.Toast;

/**
 * @author kellygod
 */
public class ToastUtil {
    public static void show(Context context,String text){
        Toast toast=Toast.makeText(context,text,Toast.LENGTH_LONG);
        toast.show();
    }
}
