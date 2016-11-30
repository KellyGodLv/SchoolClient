package cn.kellygod.schoolclient.widget;


import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog.Builder;
/**
 * @author kellygod 2016/11/27.
 */
public class UpdateDialog {
    public static void ShowDialog(Context context,String info){
        Builder mBuilder=new Builder(context);
        mBuilder.setTitle("服务器消息");
        mBuilder.setCancelable(false);
        mBuilder.setMessage(info);
        mBuilder.setNegativeButton("以后再说", null);
        mBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //下载更新
            }
        });
        mBuilder.create().show();
    }

}
