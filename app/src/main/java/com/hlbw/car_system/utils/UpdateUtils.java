package com.hlbw.car_system.utils;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hlbw.car_system.R;
import com.hlbw.car_system.api.HttpResultSubscriber;
import com.hlbw.car_system.api.HttpServerImpl;
import com.hlbw.car_system.bean.VersionBO;
import com.hlbw.car_system.constans.FileConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2019/6/2111:14
 * desc   : App检查更新的工具类
 * version: 1.0
 */
public class UpdateUtils {


    private Activity context;

    private boolean mIsCancel = false;
    private String version = "teach.apk";

    public void checkUpdate(Activity context, onUpdateListener listener) {
        this.context = context;
        HttpServerImpl.INSTANCE.getVersionInfo().subscribe(new HttpResultSubscriber<VersionBO>() {
            @Override
            public void onSuccess(VersionBO s) {
                if (s == null) {
                    if (listener != null) {
                        listener.noUpdate();
                    }
                    return;
                }
                if (Integer.parseInt(s.versionCode) > AppUtils.getAppVersionCode()) {
                    if (Integer.parseInt(s.minVersionCode) > AppUtils.getAppVersionCode()) { //强制更新
                        checkPrission(s.url);
                    } else {
                        showReDialog(s.url);
                    }
                } else {
                    if (listener != null) {
                        listener.noUpdate();
                    }
                }
            }

            @Override
            public void onFiled(String message) {
                ToastUtils.showShort(message);
            }
        });
    }


    public interface onUpdateListener {
        void noUpdate();
    }


    private ProgressDialog progressDialog;

    /*
     * 显示正在下载对话框
     */
    private void showDownloadDialog(String url) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMessage("正在下载...");
        progressDialog.setCancelable(false);//不能手动取消下载进度对话框
        progressDialog.setProgressNumberFormat("");
        progressDialog.show();

        // 下载文件
        downloadAPK(url);
    }


    private void showReDialog(String url) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialogNoBg);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_re_queren, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        view.findViewById(R.id.close).setOnClickListener(v -> dialog.dismiss());
        TextView title = view.findViewById(R.id.title);
        title.setText("有新版本！");
        TextView message = view.findViewById(R.id.message);
        message.setText("存在新的版本？\n是否更新？");
        view.findViewById(R.id.commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadAPK(url);
                dialog.dismiss();
            }
        });
        dialog.show();
        WindowManager manager = context.getWindowManager();
        Display display = manager.getDefaultDisplay();
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = display.getWidth() - SizeUtils.dp2px(30f);
        dialog.getWindow().setAttributes(params);
    }


    private void checkPrission(String url) {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(context,
                    new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    }, 1);
        } else {
            downloadAPK(url);
        }
    }


    /* 开启新线程下载apk文件
     */
    public void downloadAPK(String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mIsCancel = false;
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        File dir = new File(FileConfig.getApkFile());
                        if (!dir.exists()) {
                            dir.mkdirs();
                        }
                        // 下载文件
                        HttpsURLConnection conn = (HttpsURLConnection) new URL(url).openConnection();
                        conn.connect();
                        InputStream is = conn.getInputStream();
//                        int length = conn.getContentLength();

                        File apkFile = new File(FileConfig.getApkFile(), version);
                        FileOutputStream fos = new FileOutputStream(apkFile);

                        int count = 0;
                        byte[] buffer = new byte[1024];

                        while (!mIsCancel) {
                            int numread = is.read(buffer);
                            count += numread;
                            Message message = Message.obtain();
                            message.obj = count;
                            handler.sendMessage(message);
                            // 下载完成
                            if (numread < 0) {
                                handler.sendEmptyMessage(0x22);
                                AppUtils.installApp(apkFile);
                                break;
                            }
                            fos.write(buffer, 0, numread);
                        }
                        fos.close();
                        is.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LogUtils.e(Thread.currentThread().getName(), "2");
        }
    };


}
