package com.di.base.widget.et;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;

import com.di.base.R;

public class DeleteEditText extends AppCompatEditText {

    private static final int EXTRA_AREA = 30;//额外点击范围

    private Drawable delDrawable;
    private boolean isIconShow;

    public DeleteEditText(Context context) {
        this(context, null);
    }

    public DeleteEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttr(context, attrs);
        init();
    }

    public void initAttr(Context context, AttributeSet attrs){
        //获取自定义属性。
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.DeleteEditText);
        int resId = ta.getResourceId(R.styleable.DeleteEditText_delIcon,R.drawable.ic_delete);
        delDrawable = ContextCompat.getDrawable(context,resId);
        ta.recycle();
    }

    private void init() {

        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    isIconShow = true;
                    setCompoundDrawablesWithIntrinsicBounds(null, null, delDrawable, null);
                } else {
                    isIconShow = false;
                    setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                }
            }
        });

        setOnFocusChangeListener((View view, boolean b)-> {
                if (b && getText().toString().length() > 0) {
                    isIconShow = true;
                    setCompoundDrawablesWithIntrinsicBounds(null, null, delDrawable, null);
                } else {
                    isIconShow = false;
                    setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                }
        });

        setLongClickable(false);
    }

    public final void resetCloseDrawable(Context context, int resId){
        delDrawable = ContextCompat.getDrawable(context, resId);

        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    isIconShow = true;
                    setCompoundDrawablesWithIntrinsicBounds(null, null, delDrawable, null);
                } else {
                    isIconShow = false;
                    setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                }
            }
        });

        setOnFocusChangeListener((View view, boolean b)-> {
            if(getText() == null){
                return;
            }
            if (b && getText().toString().length() > 0) {
                isIconShow = true;
                setCompoundDrawablesWithIntrinsicBounds(null, null, delDrawable, null);
            } else {
                isIconShow = false;
                setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            }
        });

        setLongClickable(false);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isIconShow) {
            Rect bounds = getCompoundDrawables()[2].getBounds();
            int x = (int) event.getX();
            int rectX = getWidth() - bounds.width() - EXTRA_AREA - getPaddingRight();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (x > rectX) {
                        clearText();
                        performClick();
                        return true;
                    }
                    break;
            }

        }
        return super.onTouchEvent(event);
    }

    private void clearText() {
        setText("");
        isIconShow = false;
        //获取输入焦点
        setFocusable(true);
        setFocusableInTouchMode(true);
        requestFocus();
        findFocus();
    }

    /**
     * 只能输入特定字符集， 长度固定
     *
     * @param filterChars 符合要求的字符集
     * @param maxLength 最大长度
     * */
    public void setInputNumberAndChar(final String filterChars, final int maxLength){
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(s != null){
                    if(s.length() > maxLength){//字符长度限制
                        return;
                    }

                    try {
                        String temp = s.toString();
                        String tem = temp.substring(temp.length()-1);
                        char[] temC = tem.toCharArray();
                        int mid = temC[0];
                        boolean isFilter = true;
                        for(char filter : filterChars.toCharArray()){
                            if(filter == mid){
                                isFilter = false;
                                break;
                            }
                        }
                        if(isFilter){
                            s.delete(temp.length()-1, temp.length());
                            setText(s.toString());
                        }
                    }catch (Exception e){

                    }
                }
            }
        });
    }
}
