package com.hlbw.car_system.ui

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import com.hlbw.car_system.R
import com.hlbw.car_system.api.HttpResultSubscriber
import com.hlbw.car_system.api.HttpServerImpl
import com.hlbw.car_system.base.BaseActivity
import com.hlbw.car_system.base.MyApplication
import com.hlbw.car_system.kotlin.gone

class LoginActivity : BaseActivity() {

    private val userName: EditText by lazy {
        findViewById(R.id.user_name)
    }

    private val passWord: EditText by lazy {
        findViewById(R.id.pass_word)
    }

    private val login: TextView by lazy {
        findViewById(R.id.login)
    }

    override fun getLayout(): Int {
        return R.layout.act_login
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        login.setOnClickListener {
            login()
        }
    }


    private fun login() {
        val strUserName = userName.text.toString()
        val strPassWord = passWord.text.toString()
        if (strUserName.isEmpty()) {
            showToast("请输入手机号！")
            return
        }
        if (strPassWord.isEmpty()) {
            showToast("请输入密码！")
            return
        }
        HttpServerImpl.login(strUserName, strPassWord)
            .subscribe(object : HttpResultSubscriber<String>() {

                override fun onSuccess(t: String?) {
                    MyApplication.saveToken(t)
                    showToast("登录成功！")
                    finish()
                }

                override fun onFiled(message: String?) {
                    showToast(message)
                }
            })
    }
}

