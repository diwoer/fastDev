package com.di.base.widget.et;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;

import com.di.base.R;
import com.di.base.viewcontrol.absviewlistener.TextWatcherImpl;

public class IconEditText extends AppCompatEditText {

    private static final int EXTRA_AREA = 30;//额外点击范围

    private Drawable rightIcon;//显示在右侧的icon

    /**
     * 右侧icon是否可见
     * */
    private boolean rightIconShowing;

    public IconEditText(Context context) {
        this(context, null);
    }

    public IconEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
        init();
    }

    private void initAttrs(Context context, AttributeSet attrs){
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.IconEditText);
        int rightIconResId = ta.getResourceId(R.styleable.IconEditText_delIcon, R.drawable.ic_delete);
        rightIcon = ContextCompat.getDrawable(context, rightIconResId);
        ta.recycle();
    }

    private void init(){
        addTextChangedListener(new TextWatcherImpl() {

            @Override
            public void afterTextChanged(Editable s) {
                setIcons(!TextUtils.isEmpty(s));
            }
        });
        setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                setIcons(hasFocus && !TextUtils.isEmpty(getText()));
            }
        });
        setLongClickable(false);
    }

    private void setIcons(boolean showRightIcon){
        this.rightIconShowing = showRightIcon;
        Drawable rightDrawable = null;
        if(showRightIcon){
            rightDrawable = rightIcon;
        }
        setCompoundDrawablesWithIntrinsicBounds(null, null, rightDrawable, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("test", "onTouchEvent rightIconShowing=" + rightIconShowing);
        if(rightIconShowing){
            Rect bounds = getCompoundDrawables()[2].getBounds();
            int iconMinX = getWidth() - bounds.width() - EXTRA_AREA - getPaddingRight();
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if ((int) event.getX() >= iconMinX) {
                    clearText();
                    performClick();
                    return true;
                }
            }
        }
        return super.onTouchEvent(event);
    }

    private void clearText() {
        setText("");
        rightIconShowing = false;
        setFocusable(true);
        setFocusableInTouchMode(true);
        requestFocus();
        findFocus();
    }

}
