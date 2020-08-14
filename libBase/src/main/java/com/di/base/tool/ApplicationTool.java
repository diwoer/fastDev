package com.di.base.tool;

import android.app.Application;
import android.content.Context;

import com.di.base.log.DLog;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ApplicationTool {

    private static final String ACTIVITY_THREAD_CLASS_NAME = "android.app.ActivityThread";
    private static final String ACTIVITY_THREAD_METHOD_NAME = "currentActivityThread";
    private static final String APPLICATION_METHOD_NAME = "getApplication";

    private Application mApplication;

    private static volatile ApplicationTool sInstance = null;

    private ApplicationTool(){

    }

    public static ApplicationTool getInstance(){
        if(sInstance == null){
            synchronized (ApplicationTool.class){
                if(sInstance == null){
                    sInstance = new ApplicationTool();
                }
            }
        }
        return sInstance;
    }

    /**
     * 可以在自定义的Application中初始化
     * */
    public void init(Application application){
        DLog.e("[ApplicationTool] [init] " + (application == null));
        if(mApplication == null && application != null){
            mApplication = application;
        }
    }

    public Application getApplication(){
        if(mApplication == null){
            mApplication = getApplicationByReflect();
        }
        return mApplication;
    }

    public Context getAppGlobalContext(){
        if(mApplication == null){
            mApplication = getApplicationByReflect();
            if(mApplication == null){
                return null;
            }
        }
        return mApplication.getApplicationContext();
    }

    private Application getApplicationByReflect(){
        Application app = null;
        Object activityThread;
        try {
            Class acThreadClass = Class.forName(ACTIVITY_THREAD_CLASS_NAME);
            Method acThreadMethod = acThreadClass.getMethod(ACTIVITY_THREAD_METHOD_NAME);
            acThreadMethod.setAccessible(true);
            activityThread = acThreadMethod.invoke(null);
            Method applicationMethod = activityThread.getClass().getMethod(APPLICATION_METHOD_NAME);
            app = (Application)applicationMethod.invoke(activityThread);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            e.printStackTrace();
        }
        DLog.e("[ApplicationTool] [getApplicationByReflect] " + (app == null));
        return app;
    }

    public static void testApplication(){
        DLog.e("[ApplicationTool] [testApplication]" + (getInstance().getApplication() == null));
    }

}
