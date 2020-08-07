package com.di.fast.state.child;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.di.fast.state.R;
import com.di.fast.state.listener.OnRetryListener;
import com.di.fast.state.manger.StateViewManger;

public class DefaultStateView extends LinearLayout {

    private ImageView ivIcon;
    private TextView tvTips;
    private Button btnRetry;

    public DefaultStateView(Context context) {
        super(context, null);
    }

    public DefaultStateView(Context context,  @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DefaultStateView(Context context,  @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ivIcon = findViewById(R.id.iv_status);
        tvTips = findViewById(R.id.tv_status_tip);
        btnRetry = findViewById(R.id.btn_status_retry);

        if(StateViewManger.instance().getReLoadBtnType() == 0){
            btnRetry.setBackgroundResource(R.drawable.shape_teacher_reload_bg);
            btnRetry.setTextColor(Color.WHITE);
        }else if(StateViewManger.instance().getReLoadBtnType() == 1){
            btnRetry.setBackgroundResource(R.drawable.shape_student_reload_bg);
            btnRetry.setTextColor(Color.parseColor("#333333"));
        }
    }

    public void showEmptyLayout(String tip){
        ivIcon.setImageResource(R.drawable.ic_empty_data);
        if(TextUtils.isEmpty(tip)){
            tip = "数据为空";
        }
        tvTips.setText(tip);
        btnRetry.setVisibility(GONE);
    }

    public void showNetErrorLayout(final OnRetryListener listener){
        ivIcon.setImageResource(R.drawable.ic_no_net);
        tvTips.setText("(＞﹏＜)啊哦~与网络失去连接...");
        btnRetry.setVisibility(VISIBLE);
        btnRetry.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.retry();
                }
            }
        });
    }
}
