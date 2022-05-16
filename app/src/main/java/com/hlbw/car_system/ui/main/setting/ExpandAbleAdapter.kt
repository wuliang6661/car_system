package com.hlbw.car_system.ui.main.setting

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import com.hlbw.car_system.bean.SettingChildBean
import com.hlbw.car_system.bean.SettingParentBean
import com.hlbw.car_system.constans.ConstansConfig

class ExpandAbleAdapter(context: Context) : BaseExpandableListAdapter() {

    private var list: List<SettingParentBean> = ConstansConfig.getSettingData()


    override fun getGroupCount(): Int {
       return list.size
    }

    override fun getChildrenCount(p0: Int): Int {
        return list[p0].childSettings.size
    }

    override fun getGroup(p0: Int): Any {
        return list[p0]
    }

    override fun getChild(p0: Int, p1: Int): Any {
        return list[p0].childSettings[p1]
    }

    override fun getGroupId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getChildId(p0: Int, p1: Int): Long {
        return p1.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getGroupView(groupIndex: Int, expand: Boolean, view: View?, group: ViewGroup?): View {
        TODO("Not yet implemented")
    }

    override fun getChildView(groupIndex: Int, childIndex: Int, expand: Boolean, view: View?, group: ViewGroup?): View {
        TODO("Not yet implemented")
    }

    override fun isChildSelectable(p0: Int, p1: Int): Boolean {
        return false
    }
}