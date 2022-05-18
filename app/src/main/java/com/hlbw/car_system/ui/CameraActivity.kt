package com.hlbw.car_system.ui

import android.os.Bundle
import android.view.View
import com.hlbw.car_system.R
import com.hlbw.car_system.base.BaseActivity

class CameraActivity : BaseActivity() {


    override fun getLayout(): Int {
        return R.layout.act_carema
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        findViewById<View>(R.id.back).setOnClickListener {
            finish()
        }
    }
}