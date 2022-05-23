package com.hlbw.car_system.api;

import com.blankj.utilcode.util.Utils;
import com.hlbw.car_system.api.rx.RxResultHelper;

import java.io.File;
import java.io.IOException;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;

public class HttpServerImpl {

    private static HttpService service;

    /**
     * 获取代理对象
     *
     * @return
     */
    public static HttpService getService() {
        if (service == null)
            service = ApiManager.getInstance().configRetrofit(HttpService.class, HttpService.BASE_URL);
        return service;
    }






    /**
     * 上传文件
     */
    public static Observable<String> updateImg(File file) {
        MultipartBody.Part body = MultipartBody.Part.createFormData("", "");
        if (file != null) {
            File compressedImageFile;
            try {
                compressedImageFile = new Compressor(Utils.getApp()).setQuality(30).compressToFile(file);
            } catch (IOException e) {
                e.printStackTrace();
                compressedImageFile = file;
            }
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), compressedImageFile);
            body = MultipartBody.Part.createFormData("images", file.getName(), requestFile);
        }
        return getService().uploadImage1(body).compose(RxResultHelper.httpRusult());
    }
}