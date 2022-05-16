package com.hlbw.car_system.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.hlbw.car_system.R
import com.hlbw.car_system.base.BaseFragment
import com.hlbw.car_system.utils.TextViewUtils

/**
 *
 * 首页的fragment
 *
 * by wuliang
 */
class HomeFragment : BaseFragment() {

    var tvHint: TextView? = null
    var rootView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fra_home, container, false)
        return rootView
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }


    private fun initView() {
        tvHint = rootView?.findViewById(R.id.tv_hint);
        TextViewUtils.stringInterceptionChangeRed(
            tvHint,
            "仅用于匹配车辆信息条目，无需完全纠正，",
            "整车照、大架号、拆解照、切割大架照种的识别结果仅用于匹配车辆信息条目，无需完全纠正，只要能匹配正确条目即可"
        )
    }
}