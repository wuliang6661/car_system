package com.hlbw.car_system.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.blankj.utilcode.util.SizeUtils
import com.hlbw.car_system.R
import com.hlbw.car_system.base.BaseFragment
import com.hlbw.car_system.base.MyApplication
import com.hlbw.car_system.ui.CameraActivity
import com.hlbw.car_system.ui.LoginActivity
import com.hlbw.car_system.utils.TextViewUtils

/**
 *
 * 首页的fragment
 *
 * by wuliang
 */
class HomeFragment : BaseFragment(), View.OnClickListener {

    var tvHint: TextView? = null
    var rootView: View? = null
    var carMsg: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fra_home, container, false)
        return rootView
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        setListener()
    }


    private fun initView() {
        tvHint = rootView?.findViewById(R.id.tv_hint)
        carMsg = rootView?.findViewById(R.id.car_msg)
        TextViewUtils.stringInterceptionChangeRed(tvHint,
                                                  "仅用于匹配车辆信息条目，无需完全纠正，",
                                                  "整车照、大架号、拆解照、切割大架照种的识别结果仅用于匹配车辆信息条目，无需完全纠正，只要能匹配正确条目即可")
    }


    private fun setListener() {
        carMsg?.setOnClickListener {
            showDialog()
        }
        rootView?.findViewById<View>(R.id.car_person_msg)?.setOnClickListener(this)
        rootView?.findViewById<View>(R.id.zhengchezhao)?.setOnClickListener(this)
        rootView?.findViewById<View>(R.id.dajiahaozhao)?.setOnClickListener(this)
        rootView?.findViewById<View>(R.id.chaijiezhao)?.setOnClickListener(this)
        rootView?.findViewById<View>(R.id.yingyezhizhao)?.setOnClickListener(this)
        rootView?.findViewById<View>(R.id.qiegedajiaohaozhao)?.setOnClickListener {
            showToast("敬请期待！")
        }
        rootView?.findViewById<View>(R.id.dianchizhengming)?.setOnClickListener {
            showToast("敬请期待！")
        }
        rootView?.findViewById<View>(R.id.zhuxiaozhengming)?.setOnClickListener {
            showToast("敬请期待！")
        }
    }


    private fun showDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext(), R.style.dialogNoBg)
        val view: View =
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_car_message, null)
        builder.setView(view)
        val dialog: AlertDialog = builder.create()
        view.findViewById<View>(R.id.close).setOnClickListener {
            dialog.dismiss()
        }
        view.findViewById<View>(R.id.dengjizheng).setOnClickListener(this)
        view.findViewById<View>(R.id.xingshizheng).setOnClickListener(this)
        view.findViewById<View>(R.id.xinxidan).setOnClickListener(this)
        view.findViewById<View>(R.id.chayanpinzheng).setOnClickListener(this)
        dialog.show()
        val manager: WindowManager = requireActivity().windowManager
        val display = manager.defaultDisplay
        val window = dialog.window
        val params = window?.attributes
        params?.width = display.width - SizeUtils.dp2px(70f)
        dialog.window?.attributes = params
    }

    override fun onClick(p0: View) {
        if (MyApplication.getToken().isEmpty()) {
            gotoActivity(LoginActivity::class.java, false)
            return
        }
        var type = 0
        when (p0.id) {
            R.id.dengjizheng -> type = 1
            R.id.xingshizheng -> type = 2
            R.id.xinxidan -> type = 3
            R.id.chayanpinzheng -> type = 4
            R.id.car_person_msg -> type = 5
            R.id.zhengchezhao -> type = 6
            R.id.dajiahaozhao -> type = 7
            R.id.chaijiezhao -> type = 8
            R.id.yingyezhizhao -> type = 9
        }
        val bundle = Bundle()
        bundle.putInt("type", type)
        gotoActivity(CameraActivity::class.java, bundle, false)
    }
}