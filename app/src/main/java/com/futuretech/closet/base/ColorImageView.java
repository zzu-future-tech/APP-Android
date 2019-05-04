package com.futuretech.closet.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import static android.support.constraint.Constraints.TAG;

public class ColorImageView extends AppCompatImageView {
    //颜色值
    int color;
    Bitmap bitmap;
    Drawable drawable;


    public ColorImageView(Context context) {
        super(context);
        init();
    }

    public ColorImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public ColorImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //Drawable包括color和Drawable
        if (getBackground() != null && getDrawable() == null) {
            //只设置了背景图片
            drawable = getBackground();
        } else if (getBackground() == null && getDrawable() != null) {
            //只设置了资源图片
            drawable = getDrawable();
        } else {
            //即设置了背景图片，又设置了资源图片，这样无法准确确认颜色
            //未设置背景图片，以及资源图片src
            return;
        }
        //Drawable包括color和Drawable
        if (drawable instanceof ColorDrawable) {
            ColorDrawable colorDrawable = (ColorDrawable) drawable;
            color = colorDrawable.getColor();
        } else {
            bitmap = ((BitmapDrawable) drawable).getBitmap();
        }

    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                if (bitmap != null) {
//                    int x = (int) event.getX();
//                    int y = (int) event.getY();
//                    if (x < 0 || x > bitmap.getWidth() || y < 0 || y > bitmap.getHeight()) {
//                        return false;
//                    }
//                    color = bitmap.getPixel(x, y);
//                    Log.d(TAG, "color: " + color);
//                }
//                if (color == 0) {
//                    return false;
//                } else {
//                    if (mOnColorSelectedListener != null) {
//                        mOnColorSelectedListener.onColorSelectedL(color);
//                    }
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//                this.performClick();
//                break;
//            default:
//                break;
//        }
//        return true;
//    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                performClick();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
            default:
        }
        //这里放原先在onTouchEvent里的代码
        if (bitmap != null) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            if (x < 0 || x > bitmap.getWidth() || y < 0 || y > bitmap.getHeight()) {
                return false;
            }
            color = bitmap.getPixel(x, y);
            Log.d(TAG, "color: " + color);
        }
        if (color == 0) {
            return false;
        } else {
            if (mOnColorSelectedListener != null) {
                mOnColorSelectedListener.onColorSelectedL(color);
            }
        }
        return true;
    }

    //回调接口
    public interface OnColorSelectedListener {
        void onColorSelectedL(int color);
    }

    private OnColorSelectedListener mOnColorSelectedListener;
    //set方法方便外部调用接口

    public void setOnColorSelectedListener(OnColorSelectedListener onColorSelectedListener) {
        this.mOnColorSelectedListener = onColorSelectedListener;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }
}
