package com.hlbw.car_system.api;

import com.hlbw.car_system.bean.BaseResult;

import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by wuliang on 2017/3/9.
 * <p>
 * 此处存放后台服务器的所有接口数据
 */

public interface HttpService {

    String BASE_URL = "https://47.114.136.191:8080/";



    @POST("appApi/uploadCarInfoImage")
    Observable<BaseResult<String>> uploadCarInfoImg();



}
