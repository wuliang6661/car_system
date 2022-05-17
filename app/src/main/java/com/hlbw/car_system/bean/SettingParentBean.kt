package com.hlbw.car_system.bean

/**
 * 设置父类
 */
class SettingParentBean {


    var settingName: String? = null

    var childSettings: MutableList<SettingChildBean>? = null

    init {
        childSettings = ArrayList()
    }

}