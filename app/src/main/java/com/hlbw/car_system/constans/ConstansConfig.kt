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

    companion object {

        fun getSettingData(): List<SettingParentBean> {
            var json: String = MyApplication.spUtils.getString("setting", "")
            val gson = Gson()
            val list: List<SettingParentBean>
            if (json.isNotEmpty()) {
                list = gson.fromJson(json, object : TypeToken<List<SettingParentBean>>() {}.type)
            } else {
                list = getInitData()
                json = gson.toJson(list)
                MyApplication.spUtils.put("setting", json)
            }
            return list
        }


        private fun getInitData(): List<SettingParentBean> {
            val list: MutableList<SettingParentBean> = ArrayList()
            val bean = SettingParentBean()
            bean.settingName = "播报语速设置"
            bean.childSettings.add(SettingChildBean("播报语速设置", 1, true, 50))
            list.add(bean)
            val bean1 = SettingParentBean()
            bean1.settingName = "行驶证播报项设置"
            bean1.childSettings.add(SettingChildBean("号码号牌", 0, true, 0))
            bean1.childSettings.add(SettingChildBean("车辆类型", 0, true, 0))
            bean1.childSettings.add(SettingChildBean("所有人", 0, true, 0))
            bean1.childSettings.add(SettingChildBean("住址", 0, true, 0))
            bean1.childSettings.add(SettingChildBean("使用性质", 0, true, 0))
            bean1.childSettings.add(SettingChildBean("品牌型号", 0, true, 0))
            bean1.childSettings.add(SettingChildBean("车辆识别代码", 0, true, 0))
            bean1.childSettings.add(SettingChildBean("发动机号码", 0, true, 0))
            bean1.childSettings.add(SettingChildBean("注册日期", 0, true, 0))
            list.add(bean1)
            val bean2 = SettingParentBean()
            bean2.settingName = "登记证书播报项设置"
            bean2.childSettings.add(SettingChildBean("机动车所有人", 0, true, 0))
            bean2.childSettings.add(SettingChildBean("号码号牌", 0, true, 0))
            bean2.childSettings.add(SettingChildBean("所有人证件号码", 0, true, 0))
            bean2.childSettings.add(SettingChildBean("车辆类型", 0, true, 0))
            bean2.childSettings.add(SettingChildBean("车辆型号", 0, true, 0))
            bean2.childSettings.add(SettingChildBean("车架号", 0, true, 0))
            bean2.childSettings.add(SettingChildBean("发动机号", 0, true, 0))
            bean2.childSettings.add(SettingChildBean("使用性质", 0, true, 0))
            bean2.childSettings.add(SettingChildBean("注册日期", 0, true, 0))
            val bean3 = SettingParentBean()
            bean3.settingName = "非盗抢证明播报项设置"
            bean3.childSettings.add(SettingChildBean("品牌号码", 0, true, 0))
            bean3.childSettings.add(SettingChildBean("品牌", 0, true, 0))
            bean3.childSettings.add(SettingChildBean("型号", 0, true, 0))
            bean3.childSettings.add(SettingChildBean("车辆类型", 0, true, 0))
            bean3.childSettings.add(SettingChildBean("车辆识别代码", 0, true, 0))
            bean3.childSettings.add(SettingChildBean("发动机号", 0, true, 0))
            bean3.childSettings.add(SettingChildBean("使用性质", 0, true, 0))
            bean3.childSettings.add(SettingChildBean("所有人", 0, true, 0))
            bean3.childSettings.add(SettingChildBean("登记住所", 0, true, 0))
            bean3.childSettings.add(SettingChildBean("登记日期", 0, true, 0))
            val bean4 = SettingParentBean()
            bean4.settingName = "专网识别播报项设置"
            bean4.childSettings.add(SettingChildBean("号牌号码", 0, true, 0))
            bean4.childSettings.add(SettingChildBean("住所地址", 0, true, 0))
            bean4.childSettings.add(SettingChildBean("中文品牌", 0, true, 0))
            bean4.childSettings.add(SettingChildBean("型号", 0, true, 0))
            bean4.childSettings.add(SettingChildBean("车辆识别代码", 0, true, 0))
            bean4.childSettings.add(SettingChildBean("发动机号", 0, true, 0))
            bean4.childSettings.add(SettingChildBean("使用性质", 0, true, 0))
            bean4.childSettings.add(SettingChildBean("所有人", 0, true, 0))
            bean4.childSettings.add(SettingChildBean("初次登记日期", 0, true, 0))
            bean4.childSettings.add(SettingChildBean("荷载载客数", 0, true, 0))
            return list
        }
    }
}