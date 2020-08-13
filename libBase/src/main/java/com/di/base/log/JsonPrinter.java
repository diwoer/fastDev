package com.di.base.log;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonPrinter{

    // 每个级别的缩进字符数
    private static final int JSON_INDENT = 2;

    /**
     * 一次最大打印字符数, 不能修改， Log字符最大值；
     * android的logcat系统存在打印字符长度限制；
     * 所以这里控制了打印的最大字符数去循环打印；
     */
    private static final int MAX_SEGMENT_SIZE = 3*1024;

    public JsonPrinter(){}

    public synchronized void print(String tag, String json){

        if (DLog.logFilter()){
            return;
        }

        if (TextUtils.isEmpty(json)) {
            return;
        }

        try {
            json = json.trim();
            if (json.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(json);
                logJson(tag, jsonObject.toString(JSON_INDENT), json);
                return;
            }
            if (json.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(json);
                logJson(tag, jsonArray.toString(JSON_INDENT), json);
                return;
            }
            Log.d(tag, json);//适当打印内容
        } catch (JSONException e) {
            Log.e(tag, "Invalid Json");
        }
    }

    private void logJson(String tag, String json, String unJsonStr){

        int length = unJsonStr.length();

        // 未超过限制直接打印出格式化的json字符串
        if(length <= MAX_SEGMENT_SIZE){
            Log.d(tag, unJsonStr);
            Log.d(tag, json);
            return;
        }

        // 超出限制，适当打印
        logLongString(tag, unJsonStr);
    }

    private void logLongString(String tag, String json){
        int length = json.length();
        // 循环分段打印日志
        while (length > MAX_SEGMENT_SIZE) {
            String logContent = json.substring(0, MAX_SEGMENT_SIZE);
            Log.d(tag, logContent);
            json = json.replace(logContent, "");
            length = json.length();//重新计算字符长度
        }
        Log.d(tag, json);// 打印剩余日志
    }
}
