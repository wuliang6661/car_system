package com.hlbw.car_system.api

import com.hlbw.car_system.api.rx.RxResultHelper
import com.hlbw.car_system.bean.CarInfoBean
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import rx.Observable
import java.io.File

object HttpServerImpl {
    /**
     * 获取代理对象
     *
     * @return
     */
    private var service: HttpService? = null
        get() {
            if (field == null) field = ApiManager.getInstance().configRetrofit(
                HttpService::class.java, HttpService.BASE_URL
            )
            return field
        }

    /**
     * 登录
     */
    fun login(phone: String, password: String): Observable<String> {
        val params: MutableMap<String, Any> = HashMap()
        params["username"] = phone
        params["password"] = password
        return service!!.login(params).compose(RxResultHelper.httpRusult())
    }

    /**
     * 分析图片
     */
    fun uploadCarInfoImg(images: String, type: Int): Observable<CarInfoBean> {
        val params: MutableMap<String, Any> = HashMap()
        params["images"] = images
        params["type"] = type
        return service!!.uploadCarInfoImg(params).compose(RxResultHelper.httpRusult())
    }

    /**
     * 保存数据
     */
    fun saveVehicle(infoBean: CarInfoBean?): Observable<CarInfoBean> {
        return service!!.saveVehicle(infoBean).compose(RxResultHelper.httpRusult())
    }

    /**
     * 上传文件
     */
    fun updateImg(file: File?): Observable<String> {
        var body = MultipartBody.Part.createFormData("", "")
        if (file != null) {
            val requestFile = file.asRequestBody("image/jpg".toMediaTypeOrNull())
            body = MultipartBody.Part.createFormData("file", file.name, requestFile)
        }
        return service!!.uploadImage1(body).compose(RxResultHelper.httpRusult())
    }
}