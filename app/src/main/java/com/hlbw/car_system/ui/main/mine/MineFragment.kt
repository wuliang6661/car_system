package com.hlbw.car_system.ui.main.mine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hlbw.car_system.R
import com.hlbw.car_system.base.BaseFragment
import com.hlbw.car_system.ui.LoginActivity

class MineFragment : BaseFragment() {


    var rootView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fra_mine, container, false)
        return rootView
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rootView?.findViewById<View>(R.id.user_layout)?.setOnClickListener {
            gotoActivity(LoginActivity::class.java, false)
        }
    }
}