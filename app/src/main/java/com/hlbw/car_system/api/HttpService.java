package com.hlbw.car_system.api;

import com.hlbw.car_system.bean.BaseResult;
import com.hlbw.car_system.bean.CarInfoBean;
import com.hlbw.car_system.bean.UserBean;
import com.hlbw.car_system.bean.VersionBO;

import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created by wuliang on 2017/3/9.
 * <p>
 * 此处存放后台服务器的所有接口数据
 */

public interface HttpService {

    String BASE_URL = "http://taixin.happydoit.com/prod-api/";

    /**
     * 登录
     */
    @POST("applogin")
    Observable<BaseResult<String>> login(@Body Map<String, Object> params);


    /**
     * 车辆信息上传
     * images : ""  图片,多图片用","拼接
     * type: ""    1:登记证书信息 2:行驶证信息 3:查验凭证获取车辆信息4.非盗抢证明信息  5.车主身份证信息 6.整车照上传 7.大架号照上传 8.拆解照上传
     */
    @POST("appApi/uploadCarInfoImage")
    Observable<BaseResult<CarInfoBean>> uploadCarInfoImg(@Body Map<String, Object> params);


    /**
     * 保存车辆信息
     */
    @POST("appApi/saveVehicle")
    Observable<BaseResult<CarInfoBean>> saveVehicle(@Body CarInfoBean bean);


    /**
     * 获取用户信息
     */
    @GET("getAppInfo")
    Observable<BaseResult<UserBean>> getAppInfo();


    /**
     * 退出登录
     */
    @POST("logout")
    Observable<BaseResult<String>> logout();

    /**
     * 检查更新
     */
    @GET("appApi/list")
    Observable<BaseResult<VersionBO>> getVersion();


    /**
     * 图片上传
     */
    @Multipart
    @POST("common/upload/uploadImage2")
    Observable<BaseResult<String>> uploadImage1(@Part MultipartBody.Part file);
}
