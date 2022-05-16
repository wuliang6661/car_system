package com.hlbw.car_system.base;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.hlbw.car_system.utils.AppManager;

import androidx.annotation.Nullable;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * 作者 by wuliang 时间 16/10/31.
 * <p>
 * 所有activity的基类，此处建立了一个activity的栈，用于管理activity
 */

public abstract class BaseActivity extends SupportActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getLayout());
        ImmersionBar.with(this).statusBarDarkFont(true).init();   //解决虚拟按键与状态栏沉浸冲突
        AppManager.getAppManager().addActivity(this);
        // 避免从桌面启动程序后，会重新实例化入口类的activity
        if (!this.isTaskRoot()) {
            Intent intent = getIntent();
            if (intent != null) {
                String action = intent.getAction();
                if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(action)) {
                    finish();
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        KeyboardUtils.hideSoftInput(this);
        ImmersionBar.destroy(this, null);
        AppManager.getAppManager().removeActivity(this);
    }

    /**
     * 常用的跳转方法
     */
    public void gotoActivity(Class<?> cls, boolean isFinish) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
        if (isFinish) {
            finish();
        }
    }

    public void gotoActivity(Class<?> cls, Bundle bundle, boolean isFinish) {
        Intent intent = new Intent(this, cls);
        intent.putExtras(bundle);
        startActivity(intent);
        if (isFinish) {
            finish();
        }
    }


    /**
     * 设置返回
     */
//    protected void goBack() {
//        LinearLayout imageView = findViewById(R.id.back);
//        imageView.setVisibility(View.VISIBLE);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//    }
//
//    /**
//     * 设置标题
//     *
//     * @param title
//     */
//    protected void setTitleText(String title) {
//        TextView titleTex = findViewById(R.id.title_text);
//        titleTex.setText(title);
//    }
    protected void showToast(String message) {
        ToastUtils.showShort(message);
    }


    //调用隐藏系统默认的输入法
    public void showOrHide() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }


    //获取输入法打开的状态
    public boolean isShowing(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.isActive();//isOpen若返回true，则表示输入法打开
    }


    protected abstract int getLayout();

    public void onRequestEnd() {

    }
}
