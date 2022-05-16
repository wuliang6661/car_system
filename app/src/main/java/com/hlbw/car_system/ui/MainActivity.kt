package com.hlbw.car_system.ui

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import com.gyf.immersionbar.ImmersionBar
import com.hlbw.car_system.R
import com.hlbw.car_system.base.BaseActivity
import com.hlbw.car_system.ui.main.none.NoneFragment1
import com.hlbw.car_system.ui.main.none.NoneFragment2
import com.hlbw.car_system.ui.main.none.NoneFragment3
import com.hlbw.car_system.utils.AppManager
import com.xyz.tabitem.BottmTabItem
import me.yokeyword.fragmentation.SupportFragment
import kotlin.system.exitProcess


/**
 * 主页
 */
class MainActivity : BaseActivity(), View.OnClickListener {

    var main1: BottmTabItem? = null
    var main2: BottmTabItem? = null
    var main3: BottmTabItem? = null

    private val mFragments: MutableList<SupportFragment> by lazy {
        mutableListOf()
    }
    private val buttons: MutableList<BottmTabItem?> by lazy {
        mutableListOf()
    }

    private var selectPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        buttons.add(main1)
        buttons.add(main2)
        buttons.add(main3)
        initFragment()
    }


    private fun initView() {
        main1 = findViewById(R.id.main1)
        main2 = findViewById(R.id.main2)
        main3 = findViewById(R.id.main3)
        main1?.setOnClickListener(this)
        main2?.setOnClickListener(this)
        main3?.setOnClickListener(this)
    }


    /**
     * 设置底部按钮显示
     */
    private fun setButton(position: Int) {
        for (i in buttons.indices) {
            if (position == i) {
                buttons[i]?.setSelectState(true)
            } else {
                buttons[i]?.setSelectState(false)
            }
        }
    }


    /**
     * 初始化fragment
     */
    private fun initFragment() {
        val firstFragment: SupportFragment? = findFragment(NoneFragment1::class.java)
        mFragments.clear()
        if (firstFragment == null) {
            mFragments.add(NoneFragment1())
            mFragments.add(NoneFragment2())
            mFragments.add(NoneFragment3())
            loadMultipleRootFragment(
                R.id.fragment_container, 0,
                mFragments[0],
                mFragments[1],
                mFragments[2]
            )
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
            // 这里我们需要拿到mFragments的引用
            mFragments.add(firstFragment)
            mFragments.add(findFragment(NoneFragment2::class.java))
            mFragments.add(findFragment(NoneFragment3::class.java))
        }
    }


    override fun getLayout(): Int {
        return R.layout.activity_main
    }


    //记录用户首次点击返回键的时间
    private var firstTime: Long = 0

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_BACK -> {
                val secondTime = System.currentTimeMillis()
                if (secondTime - firstTime > 2000) {
                    showToast("再按一次退出程序")
                    firstTime = secondTime
                    return true
                } else {
                    AppManager.getAppManager().finishAllActivity()
                    exitProcess(0)
                }
            }
        }
        return super.onKeyUp(keyCode, event)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.main1 -> {
                showHideFragment(mFragments[0], mFragments[selectPosition])
                selectPosition = 0
                setButton(0)
            }
            R.id.main2 -> {
                showHideFragment(mFragments[1], mFragments[selectPosition])
                selectPosition = 1
                setButton(1)
            }
            R.id.main3 -> {
                showHideFragment(mFragments[2], mFragments[selectPosition])
                selectPosition = 2
                setButton(2)
            }
        }
    }

}
