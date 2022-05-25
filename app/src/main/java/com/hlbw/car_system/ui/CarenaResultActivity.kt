package com.hlbw.car_system.ui

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hlbw.car_system.R
import com.hlbw.car_system.base.BaseActivity

/**
 *@author wuliang
 *@create 2022/5/25
 *@des This is 牌照识别的结果页
 */
class CarenaResultActivity  : BaseActivity(){

    private val recycleView : RecyclerView by lazy {
        findViewById(R.id.recycle_view)
    }

    override fun getLayout(): Int {
        return R.layout.act_carema_result
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        recycleView.let {
            it.layoutManager = LinearLayoutManager(this)
            it.isNestedScrollingEnabled = false
        }
    }






}