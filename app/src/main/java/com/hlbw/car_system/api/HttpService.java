package com.hlbw.car_system.api;

import com.hlbw.car_system.bean.BaseResult;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by wuliang on 2017/3/9.
 * <p>
 * 此处存放后台服务器的所有接口数据
 */

public interface HttpService {

    String BASE_URL = "http://47.114.136.191:8080/";

    /**
     * 登录
     */
    @FormUrlEncoded
    @POST("applogin")
    Observable<BaseResult<String>> login(@Field("username") String username, @Field("password") String password);


    /**
     * 车辆信息上传
     * images : ""  图片,多图片用","拼接
     * type: ""   1:识别车辆登记证获取车辆信息 2:识别车辆行驶证获取信息 3:查验凭证获取车辆信息
     */
    @FormUrlEncoded
    @POST("appApi/uploadCarInfoImage")
    Observable<BaseResult<String>> uploadCarInfoImg(@Field("images") String images, @Field("type") int type);


    /**
     * 车主信息上传
     */
    @FormUrlEncoded
    @POST("appApi/uploadUserImage")
    Observable<BaseResult<String>> uploadUserImage(@Field("images") String images);


    /**
     * 整车照上传
     */
    @FormUrlEncoded
    @POST("appApi/uploadVehicleImage")
    Observable<BaseResult<String>> uploadVehicleImage(@Field("images") String images);


    /**
     * 大架号照上传
     */
    @FormUrlEncoded
    @POST("appApi/uploadLargeImage")
    Observable<BaseResult<String>> uploadLargeImage(@Field("images") String images);


    /**
     * 拆解照上传
     */
    @FormUrlEncoded
    @POST("appApi/uploadDismantleImage")
    Observable<BaseResult<String>> uploadDismantleImage(@Field("images") String images);


    /**
     * 注销证明上传
     */
    @FormUrlEncoded
    @POST("appApi/uploadTextRecognizeImage")
    Observable<BaseResult<String>> uploadTextRecognizeImage(@Field("images") String images);


    /**
     * 图片上传
     */
    @Multipart
    @POST("common/upload/uploadImage1")
    Observable<BaseResult<String>> uploadImage1(@Field("images") String images);
}
