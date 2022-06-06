package com.hlbw.car_system.constans

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hlbw.car_system.base.MyApplication
import com.hlbw.car_system.bean.SettingChildBean
import com.hlbw.car_system.bean.SettingParentBean


/**
 * 全局参数 基础设置
 */
class ConstansConfig {


    fun getSettingData(): List<SettingParentBean> {
        var json: String = MyApplication.getSpUtils().getString("setting", "")
        val gson = Gson()
        val list: List<SettingParentBean>
        if (json.isNotEmpty()) {
            list = gson.fromJson(json, object : TypeToken<List<SettingParentBean>>() {}.type)
        } else {
            list = getInitData()
            json = gson.toJson(list)
            MyApplication.getSpUtils().put("setting", json)
        }
        return list
    }


    fun saveSettingData(data: List<SettingParentBean>) {
        val json = Gson().toJson(data)
        MyApplication.getSpUtils().put("setting", json)
    }


    private fun getInitData(): List<SettingParentBean> {
        val list: MutableList<SettingParentBean> = ArrayList()
        val bean = SettingParentBean()
        bean.settingName = "播报语速设置"
        bean.childSettings?.add(SettingChildBean(0, "播报语速设置", 1, true, 100f))
        list.add(bean)
        val bean1 = SettingParentBean()
        bean1.settingName = "登记证书播报项设置"
        bean1.childSettings?.add(SettingChildBean(1, "机动车所有人", 0, true, 0f))
        bean1.childSettings?.add(SettingChildBean(2, "号牌号码", 0, true, 0f))
        bean1.childSettings?.add(SettingChildBean(3, "所有人证件号码", 0, true, 0f))
        bean1.childSettings?.add(SettingChildBean(4, "车辆类型", 0, true, 0f))
        bean1.childSettings?.add(SettingChildBean(5, "车辆型号", 0, true, 0f))
        bean1.childSettings?.add(SettingChildBean(6, "车架号", 0, true, 0f))
        bean1.childSettings?.add(SettingChildBean(7, "发动机号", 0, true, 0f))
        bean1.childSettings?.add(SettingChildBean(8, "使用类型", 0, true, 0f))
        bean1.childSettings?.add(SettingChildBean(9, "注册日期", 0, true, 0f))
        list.add(bean1)
        val bean2 = SettingParentBean()
        bean2.settingName = "机动车信息单播报项设置"
        bean2.childSettings?.add(SettingChildBean(1, "品牌号码", 0, true, 0f))
        bean2.childSettings?.add(SettingChildBean(2, "品牌", 0, true, 0f))
        bean2.childSettings?.add(SettingChildBean(3, "型号", 0, true, 0f))
        bean2.childSettings?.add(SettingChildBean(4, "车辆类型", 0, true, 0f))
        bean2.childSettings?.add(SettingChildBean(5, "车辆识别代码", 0, true, 0f))
        bean2.childSettings?.add(SettingChildBean(6, "发动机号", 0, true, 0f))
        bean2.childSettings?.add(SettingChildBean(7, "使用性质", 0, true, 0f))
        bean2.childSettings?.add(SettingChildBean(8, "所有人", 0, true, 0f))
        bean2.childSettings?.add(SettingChildBean(9, "登记住所", 0, true, 0f))
        bean2.childSettings?.add(SettingChildBean(10, "登记日期", 0, true, 0f))
        list.add(bean2)
        val bean3 = SettingParentBean()
        bean3.settingName = "行驶证播报项设置"
        bean3.childSettings?.add(SettingChildBean(1, "号牌号码", 0, true, 0f))
        bean3.childSettings?.add(SettingChildBean(2, "车辆类型", 0, true, 0f))
        bean3.childSettings?.add(SettingChildBean(3, "所有人", 0, true, 0f))
        bean3.childSettings?.add(SettingChildBean(4, "住址", 0, true, 0f))
        bean3.childSettings?.add(SettingChildBean(5, "使用性质", 0, true, 0f))
        bean3.childSettings?.add(SettingChildBean(6, "品牌型号", 0, true, 0f))
        bean3.childSettings?.add(SettingChildBean(7, "车辆识别代码", 0, true, 0f))
        bean3.childSettings?.add(SettingChildBean(8, "发动机号码", 0, true, 0f))
        bean3.childSettings?.add(SettingChildBean(9, "注册日期", 0, true, 0f))
        bean3.childSettings?.add(SettingChildBean(10, "核定载人数", 0, true, 0f))
        bean3.childSettings?.add(SettingChildBean(11, "整备质量", 0, true, 0f))
        bean3.childSettings?.add(SettingChildBean(12, "使用性质", 0, true, 0f))
        list.add(bean3)
        val bean4 = SettingParentBean()
        bean4.settingName = "机动车查验凭证播报项设置"
        bean4.childSettings?.add(SettingChildBean(1, "所有人", 0, true, 0f))
        bean4.childSettings?.add(SettingChildBean(2, "型号", 0, true, 0f))
        bean4.childSettings?.add(SettingChildBean(3, "车架号", 0, true, 0f))
        bean4.childSettings?.add(SettingChildBean(4, "车辆类型", 0, true, 0f))
        bean4.childSettings?.add(SettingChildBean(5, "号牌号码", 0, true, 0f))
        list.add(bean4)
        return list
    }
}