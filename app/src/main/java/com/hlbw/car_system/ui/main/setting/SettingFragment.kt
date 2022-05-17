package com.hlbw.car_system.ui.main.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hlbw.car_system.R
import com.hlbw.car_system.base.BaseFragment
import com.hlbw.car_system.weight.SuperExpandableListView

class SettingFragment : BaseFragment() {


    var rootView: View? = null
    var expandAbleList: SuperExpandableListView? = null

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
        val adapter = ExpandAbleAdapter(requireContext())
        expandAbleList?.setAdapter(adapter)
    }

}