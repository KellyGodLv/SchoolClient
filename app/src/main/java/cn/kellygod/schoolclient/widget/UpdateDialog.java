package cn.kellygod.schoolclient.widget;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog.Builder;

import cn.kellygod.schoolclient.connection.UpdateAppBean;

/**
 * @author kellygod 2016/11/27.
 */
public class UpdateDialog {
    public static void ShowDialog(final Context context,final UpdateAppBean info){
        Builder mBuilder=new Builder(context);
        mBuilder.setTitle("服务器消息");
        mBuilder.setCancelable(false);
        mBuilder.setMessage(info.getMessage());
        mBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //下载更新
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(info.getUrl());
                intent.setData(content_url);
                context.startActivity(intent);
            }
        });
        mBuilder.create().show();
    }

}
