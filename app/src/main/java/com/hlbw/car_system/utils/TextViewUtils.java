package com.hlbw.car_system.utils;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

public class TextViewUtils {


    /**
     * CSDN-深海呐
     * TextView部分文字变色
     * keyword = 关键字、需要变色的文字   string = 包含变色文字的全部文字
     */
    public static void stringInterceptionChangeRed(TextView textView, String keyword, String string) {
        if (keyword == null || keyword.trim().length() == 0) return;
        if (!string.contains(keyword)) return;
        int start = string.indexOf(keyword);
        int end = start + keyword.length();
        if (end != 0 && start != -1) {
            final SpannableStringBuilder style = new SpannableStringBuilder();
            style.append(string);
            //设置部分文字颜色
            ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#E30000"));
            style.setSpan(foregroundColorSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            textView.setText(style);
        }
    }
}
