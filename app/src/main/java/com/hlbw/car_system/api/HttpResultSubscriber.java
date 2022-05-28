package com.hlbw.car_system.api;

import android.accounts.NetworkErrorException;
import android.app.Activity;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.hlbw.car_system.R;
import com.hlbw.car_system.utils.AppManager;

import java.net.SocketException;
import java.net.UnknownHostException;

import androidx.appcompat.app.AlertDialog;
import rx.Subscriber;

/**
 * Created by wuliang on 2018/11/13.
 * <p>
 * 自定义的Subscriber订阅者
 */

public abstract class HttpResultSubscriber<T> extends Subscriber<T> {


    @Override
    public void onNext(T t) {
        onSuccess(t);
    }


    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (e instanceof NetworkErrorException) {
            onFiled("网络出错，请检查您的网络！");
        } else if (e instanceof SocketException) {
            onFiled("网络出错，请检查您的网络！");
        } else if (e instanceof UnknownHostException) {
            onFiled("网络出错，请检查您的网络！");
        } else if (e instanceof DialogCallException) {
            showReDialog(e.getMessage());
        } else {
            onFiled(e.getMessage());
        }
    }


    private void showReDialog(String message) {
        Activity context = AppManager.getAppManager().curremtActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialogNoBg);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_hint_error, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        TextView title = view.findViewById(R.id.title);
        title.setText(message);
        view.findViewById(R.id.commit).setOnClickListener(v -> {
            context.finish();
            dialog.dismiss();
        });
        dialog.show();
        WindowManager manager = context.getWindowManager();
        Display display = manager.getDefaultDisplay();
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = display.getWidth() - SizeUtils.dp2px(30f);
        dialog.getWindow().setAttributes(params);
    }


    @Override
    public void onCompleted() {

    }


    public abstract void onSuccess(T t);

    public abstract void onFiled(String message);
}
