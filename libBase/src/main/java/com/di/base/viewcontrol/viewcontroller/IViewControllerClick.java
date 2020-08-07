package com.di.base.viewcontrol.viewcontroller;

public interface IViewControllerClick{

    //单击效果
    void bindSingleClicks(int[] resIds);

    //在多长时间内单击效果有效
    void bindSingleClicks(int[] resIds, int seconds);

    //重置单击View的点击时间
    void resetSingleClicks();

    //重置单击View的点击时间
    void resetSingleClicks(int[] resIds);

    //正常点击
    void bindNormalClicks(int[] resIds);
}
