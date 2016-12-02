package cn.kellygod.schoolclient.util;

import java.util.Stack;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import cn.kellygod.schoolclient.main.activity.BaseActivity;
/**
 *
 *  @author kellygod
 * */
public class AppManager {
    private static Stack<BaseActivity> activityStack;
    private static AppManager instance;

    private AppManager() {
    }

    /**
     * 使用单例模式管理Activity
     */
    public static AppManager getAppManager() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    /**
     * 添加Activity
     */
    public void addActivity(BaseActivity activity) {
        if (activityStack == null) {
            activityStack = new Stack<BaseActivity>();
        }
        activityStack.add(activity);
    }

    /**
     *  获取当前Activity
     */
    public BaseActivity currentActivity() {
        if (activityStack == null || activityStack.isEmpty()) {
            return null;
        }
        BaseActivity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 查找活跃的Activity 不存在返回null
     */
    public BaseActivity findActivity(Class<?> cls) {
        BaseActivity activity = null;
        for (BaseActivity aty : activityStack) {
            if (aty.getClass().equals(cls)) {
                activity = aty;
                break;
            }
        }
        return activity;
    }

    /**
     * 结束当前Activity
     */
    public void finishActivity() {
        BaseActivity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束对应的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     *
     */
    public void finishActivity(Class<?> cls) {
        for (BaseActivity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     *
     * 
     * @param cls
     */
    public void finishOthersActivity(Class<?> cls) {
        for (BaseActivity activity : activityStack) {
            if (!(activity.getClass().equals(cls))) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 将所有Activity出栈
     */
    public void finishAllActivity() {
        if(activityStack!=null) {
            for (int i = 0, size = activityStack.size(); i < size; i++) {

                if (null != activityStack.get(i))
                    activityStack.get(i).finish();

            }
            activityStack.clear();
        }
    }

    /**
     *   退出程序
     */
    public void AppExit(Context context) {
        try {
            finishAllActivity();
            ActivityManager activityMgr = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.killBackgroundProcesses(context.getPackageName());
            System.exit(0);
        }catch (Exception e){
            System.exit(0);
        }
    }
}