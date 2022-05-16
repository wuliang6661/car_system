package com.hlbw.car_system.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.gyf.immersionbar.ImmersionBar;

import androidx.annotation.Nullable;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by wuliang on 2017/5/11.
 * <p>
 * 所有Fragment的父类
 */

public abstract class BaseFragment extends SupportFragment {


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onResume() {
        super.onResume();
//        ImmersionBar.with(this).keyboardEnable(true).statusBarDarkFont(true).init();   //解决虚拟按键与状态栏沉浸冲突
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
//        ImmersionBar.destroy(this);
    }

    /**
     * 常用的跳转方法
     */
    public void gotoActivity(Class<?> cls, boolean isFinish) {
        Intent intent = new Intent(getActivity(), cls);
        startActivity(intent);
        if (isFinish) {
            getActivity().finish();
        }
    }

    public void gotoActivity(Class<?> cls, Bundle bundle, boolean isFinish) {
        Intent intent = new Intent(getActivity(), cls);
        intent.putExtras(bundle);
        startActivity(intent);
        if (isFinish) {
            getActivity().finish();
        }
    }


    protected void showToast(String message) {
        ToastUtils.showShort(message);
    }


    public void onRequestEnd() {

    }
}
