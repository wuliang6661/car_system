package com.hlbw.car_system.api;

import com.hlbw.car_system.bean.BaseResult;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by wuliang on 2017/3/9.
 * <p>
 * 此处存放后台服务器的所有接口数据
 */

public interface HttpService {

    String BASE_URL = "https://47.114.136.191:8080/";


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
    @POST("appApi/uploadUserImage")
    Observable<BaseResult<String>> uploadUserImage(@Field("images") String images);


}
