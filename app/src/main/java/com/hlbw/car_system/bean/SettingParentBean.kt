package com.hlbw.car_system.bean

/**
 * 设置父类
 */
class SettingParentBean {


    var settingName: String? = null

    val childSettings: MutableList<SettingChildBean> by lazy {
        ArrayList()
    }

}