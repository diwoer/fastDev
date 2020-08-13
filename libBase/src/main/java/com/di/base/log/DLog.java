package com.di.base.log;

import android.util.Log;

import com.di.base.BuildConfig;

/**
 * 功能描述
 * 1、整体控制log是否打印
 * 2、打印的信息不能是null
 * 3、区分log级别
 * 4、支持打印格式化json
 *
 * 5、TODO 保存日志到本地
 * */
public class DLog {

    private static boolean logFilter = !BuildConfig.DEBUG;//默认跟随打包模式
    private static String sTag = "test"; //默认的打印log

    private static LogPrinter logPrinter = new LogPrinter();
    private static JsonPrinter jsonPrinter = new JsonPrinter();

    /**
     * 整体控制是否打印
     * @param logFilter false 可以打印；true 无法打印;
     * */
    public static void setLogFilter(boolean logFilter){
        DLog.logFilter = logFilter;
    }

    public static boolean logFilter(){
        return logFilter;
    }

    public static void v(String msg){
        logPrinter.print(Log.VERBOSE, sTag, msg);
    }

    public static void v(String tag, String msg){
        logPrinter.print(Log.VERBOSE, tag, msg);
    }

    public static void d(String msg){
        logPrinter.print(Log.DEBUG, sTag, msg);
    }

    public static void d(String tag, String msg){
        logPrinter.print(Log.DEBUG, tag, msg);
    }

    public static void i(String msg){
        logPrinter.print(Log.INFO, sTag, msg);
    }

    public static void i(String tag, String msg){
        logPrinter.print(Log.INFO, tag, msg);
    }

    public static void w(String msg){
        logPrinter.print(Log.WARN, sTag, msg);
    }

    public static void w(String tag, String msg){
        logPrinter.print(Log.WARN, tag, msg);
    }

    public static void e(String msg){
        logPrinter.print(Log.ERROR, sTag, msg);
    }

    public static void e(String tag, String msg){
        logPrinter.print(Log.ERROR, tag, msg);
    }

    public static void json(String msg){
        jsonPrinter.print(sTag, msg);
    }

    public static void json(String tag, String msg){
        jsonPrinter.print(tag, msg);
    }
}
