package com.di.base.log;

public class ThrowLogFilter {

    public static boolean checkNull(String msg){
        if(msg == null){
            DLog.e("log_filter", "the log message is null");
            return true;
        }
        return false;
    }
}
