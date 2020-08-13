package com.di.base.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * 输入法键盘帮助类
 *
 * */
public final class SoftKeyBoardUtil {

    /**
     * 关闭输入法
     *
     * @param activity 要关闭输入法的界面
     * */
    public static void closeInputMethod(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 关闭输入法
     *
     * @param activity 需要传入Context对象去获取InputMethodManager
     * @param view 要关闭的view
     * */
    public static void closeInputMethod(Activity activity, EditText view) {
        if (view != null) {
            InputMethodManager imm =  ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE));
            if (imm == null) {
                return;
            }
            imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 打开输入法
     * @param activity 需要传入Context对象去获取InputMethodManager
     * @param view 要关闭的view
     * */
    public static void showInputMethod(Activity activity, EditText view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if(imm == null){
                return;
            }
            view.setFocusable(true);
            view.setFocusableInTouchMode(true);
            view.requestFocus();
            imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
        }
    }
}
