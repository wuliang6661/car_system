package com.hlbw.car_system.api;

import com.hlbw.car_system.api.rx.RxResultHelper;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

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
     * 登录
     */
    public static Observable<String> login(String phone,String password){
        Map<String,Object> params = new HashMap<>();
        params.put("username",phone);
        params.put("password",password);
        return getService().login(params).compose(RxResultHelper.httpRusult());
    }


    /**
     * 上传文件
     */
    public static Observable<String> updateImg(File file) {
        MultipartBody.Part body = MultipartBody.Part.createFormData("", "");
        if (file != null) {
//            File compressedImageFile;
//            try {
//                compressedImageFile = new Compressor(Utils.getApp()).setQuality(30).compressToFile(file);
//            } catch (IOException e) {
//                e.printStackTrace();
//                compressedImageFile = file;
//            }
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), file);
            body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        }
        return getService().uploadImage1(body).compose(RxResultHelper.httpRusult());
    }
}