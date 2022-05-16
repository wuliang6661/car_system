package com.hlbw.car_system.bean

/**
 * 子设置的bean
 */
data class SettingChildBean(
    var title: String?,//默认开关， 1是数字阀门
    var type: Int = 0, var isOpen: Boolean = true, var openNum: Int?
)