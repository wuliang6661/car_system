package com.hlbw.car_system.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.Utils;
import com.hlbw.car_system.R;

/**
 * @author wuliang
 * @create 2022/6/6
 * @des This is ...
 */
public class MyToastUtils {


    /**
     * 自定义Toast的布局展示
     *
     * @param text 文字
     */
    public static void showToast(String text) {
        Context context = AppManager.getAppManager().curremtActivity();
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.toast_layout, null);
        LinearLayout root_layout = layout.findViewById(R.id.root_layout);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(SizeUtils.dp2px(150), SizeUtils.dp2px(100));
        root_layout.setLayoutParams(params);
        TextView textView = (TextView) layout.findViewById(R.id.toast_text);
        textView.setText(text);
        Toast toast = new Toast(Utils.getApp());
        //设置Toast的位置
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        //让Toast显示为我们自定义的样子
        toast.setView(layout);
        toast.show();
    }
}
