package com.hlbw.car_system.ui.main.mine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.blankj.utilcode.util.SizeUtils
import com.hlbw.car_system.R
import com.hlbw.car_system.api.HttpResultSubscriber
import com.hlbw.car_system.api.HttpServerImpl
import com.hlbw.car_system.base.BaseFragment
import com.hlbw.car_system.base.MyApplication
import com.hlbw.car_system.bean.UserBean
import com.hlbw.car_system.kotlin.gone
import com.hlbw.car_system.kotlin.loadImageUrl
import com.hlbw.car_system.kotlin.visible
import com.hlbw.car_system.ui.LoginActivity
import com.hlbw.car_system.utils.UpdateUtils

class MineFragment : BaseFragment() {


    var rootView: View? = null
    var personImg : ImageView?  = null
    var personId : TextView? = null
    var personName : TextView? = null
    var personGs : TextView? = null

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
        rootView?.findViewById<View>(R.id.check_update)?.setOnClickListener {
            UpdateUtils().checkUpdate(
                requireActivity()
            ) {
                showToast("当前已是最新版本！")
            }
        }
        rootView?.findViewById<View>(R.id.clear_cache)?.setOnClickListener {
            MyApplication.getSpUtils().remove("setting")
            MyApplication.getSpUtils().remove("isOpenBoBao")
            MyApplication.getSpUtils().remove("isOpenXiangCe")
            MyApplication.getSpUtils().remove("isAddXiangCe")
            showToast("已清除！")
        }
        rootView?.findViewById<View>(R.id.logout)?.setOnClickListener {
            showReDialog()
        }
        personImg = rootView?.findViewById(R.id.person_img)
        personId = rootView?.findViewById(R.id.person_id)
        personName = rootView?.findViewById(R.id.person_name)
        personGs = rootView?.findViewById(R.id.person_gs)

    }


    override fun onSupportVisible() {
        super.onSupportVisible()
        if (MyApplication.getToken().isNotEmpty()){
            rootView?.findViewById<View>(R.id.logout).visible()
            getUserInfo()
        }else{
            rootView?.findViewById<View>(R.id.logout).gone()
            personImg?.setImageResource(R.mipmap.ic_launcher_round)
            personName?.text = ""
            personId?.text = "请登录"
            personGs?.text = ""
        }
    }


    private fun getUserInfo() {
        HttpServerImpl.getUserInfo().subscribe(object : HttpResultSubscriber<UserBean>() {

            override fun onSuccess(t: UserBean?) {
                t?.let {
                    personImg?.loadImageUrl(it.principalImg)
                    personName?.text = it.nickName
                    personId?.text = it.userName
                    personGs?.text = it.firmName
                }
            }

            override fun onFiled(message: String?) {
                showToast(message)
            }

        })
    }


    private fun showReDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext(), R.style.dialogNoBg)
        val view: View =
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_re_queren, null)
        builder.setView(view)
        val dialog: AlertDialog = builder.create()
        view.findViewById<View>(R.id.close).setOnClickListener {
            dialog.dismiss()
        }
        val title = view.findViewById<TextView>(R.id.title)
        title.text = "退出登录"
        val message = view.findViewById<TextView>(R.id.message)
        message.text = "是否确认退出登录？"
        view.findViewById<View>(R.id.commit).setOnClickListener {
            logout()
            dialog.dismiss()
        }
        dialog.show()
        val manager: WindowManager = requireActivity().windowManager
        val display = manager.defaultDisplay
        val window = dialog.window
        val params = window?.attributes
        params?.width = display.width - SizeUtils.dp2px(30f)
        dialog.window?.attributes = params
    }


    private fun logout() {
        HttpServerImpl.logout().subscribe(object : HttpResultSubscriber<String>() {

            override fun onSuccess(t: String?) {
                MyApplication.removeToken()
                rootView?.findViewById<View>(R.id.logout).gone()
                personImg?.setImageResource(R.mipmap.ic_launcher_round)
                personName?.text = ""
                personId?.text = "请登录"
                personGs?.text = ""
            }

            override fun onFiled(message: String?) {
                showToast(message)
            }
        })
    }
}