package com.hlbw.car_system.ui.main.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import com.hlbw.car_system.R
import com.hlbw.car_system.base.BaseFragment
import com.hlbw.car_system.base.MyApplication
import com.hlbw.car_system.weight.SuperExpandableListView

class SettingFragment : BaseFragment() {


    var rootView: View? = null
    var expandAbleList: SuperExpandableListView? = null

    var bobaoSwitch: Switch? = null
    var xiangceSwitch: Switch? = null
    var addXiangceSwitch: Switch? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fra_setting, container, false)
        return rootView
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        expandAbleList = rootView?.findViewById(R.id.expand_able_list)

        bobaoSwitch = rootView?.findViewById(R.id.bobao_switch)
        xiangceSwitch = rootView?.findViewById(R.id.xiangce_switch)
        addXiangceSwitch = rootView?.findViewById(R.id.add_xiangce_switch)
    }


    override fun onSupportVisible() {
        super.onSupportVisible()
        bobaoSwitch?.isChecked = MyApplication.getSpUtils().getBoolean("isOpenBoBao", true)
        xiangceSwitch?.isChecked = MyApplication.getSpUtils().getBoolean("isOpenXiangCe", true)
        addXiangceSwitch?.isChecked = MyApplication.getSpUtils().getBoolean("isAddXiangCe", true)
        bobaoSwitch?.setOnCheckedChangeListener { _, isChecked ->
            MyApplication.getSpUtils().put("isOpenBoBao", isChecked)
        }
        xiangceSwitch?.setOnCheckedChangeListener { _, isChecked ->
            MyApplication.getSpUtils().put("isOpenXiangCe", isChecked)
        }
        addXiangceSwitch?.setOnCheckedChangeListener { _, isChecked ->
            MyApplication.getSpUtils().put("isAddXiangCe", isChecked)
        }
        val adapter = ExpandAbleAdapter(requireContext())
        expandAbleList?.setAdapter(adapter)
    }
}