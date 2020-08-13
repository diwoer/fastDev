package com.di.base.log;

import android.util.Log;

public class LogPrinter {

    public LogPrinter(){}

    public void print(int level, String tag, String msg) {

        if(DLog.logFilter() || ThrowLogFilter.checkNull(msg)){
            return;
        }

        synchronized (LogPrinter.class){

            switch (level){
                case Log.VERBOSE:
                    Log.v(tag, msg);
                    break;
                case Log.WARN:
                    Log.w(tag, msg);
                    break;
                case Log.DEBUG:
                    Log.d(tag, msg);
                    break;
                case Log.INFO:
                    Log.i(tag, msg);
                    break;
                case Log.ERROR:
                    Log.e(tag, msg);
                    break;
            }
        }
    }
}
