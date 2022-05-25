package com.hlbw.car_system.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.SizeUtils
import com.hlbw.car_system.R
import com.hlbw.car_system.api.HttpResultSubscriber
import com.hlbw.car_system.api.HttpServerImpl
import com.hlbw.car_system.base.BaseActivity
import com.hlbw.car_system.bean.CarInfoBean
import com.hlbw.car_system.bean.ItemVoListBean
import com.hlbw.car_system.kotlin.loadImageUrl
import com.hlbw.car_system.utils.AppManager
import com.hlbw.car_system.utils.SystemTTS
import com.hlbw.car_system.weight.lgrecycleadapter.LGRecycleViewAdapter
import com.hlbw.car_system.weight.lgrecycleadapter.LGViewHolder

/**
 *@author wuliang
 *@create 2022/5/25
 *@des This is 牌照识别的结果页
 */
class CarenaResultActivity : BaseActivity() {

    private val recycleView: RecyclerView by lazy {
        findViewById(R.id.recycle_view)
    }

    private val carImg: ImageView by lazy {
        findViewById(R.id.car_img)
    }
    private val title: TextView by lazy {
        findViewById(R.id.title)
    }

    private var imageUrl: String = ""
    private var type: Int = 0
    private var carInfo: CarInfoBean? = null
    private var ttsVoice: SystemTTS? = null

    override fun getLayout(): Int {
        return R.layout.act_carema_result
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ttsVoice = SystemTTS.getInstance(this)
        recycleView.let {
            it.layoutManager = LinearLayoutManager(this)
            it.isNestedScrollingEnabled = false
        }
        intent.extras?.let {
            type = it.getInt("type")
            imageUrl = it.getString("image").toString()
        }
        carImg.loadImageUrl(imageUrl)
        syncImage()
        findViewById<View>(R.id.commit).setOnClickListener {
            carInfo?.let {
                saveMsg()
            }
        }
        findViewById<View>(R.id.go_home).setOnClickListener {
            AppManager.getAppManager().goHome()
        }
        findViewById<View>(R.id.go_carema).setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("type", type)
            gotoActivity(CameraActivity::class.java, bundle, true)
        }
        findViewById<View>(R.id.bobao).setOnClickListener {
            carInfo?.let { data ->
                var bobaoText = ""
                data.itemVoList.map {
                    bobaoText += it.name
                    bobaoText += it.value
                }
                SystemTTS.getInstance(this)?.play(bobaoText)
            }
        }
    }


    private fun syncImage() {
        showProgress()
        HttpServerImpl.uploadCarInfoImg(imageUrl, type)
            .subscribe(object : HttpResultSubscriber<CarInfoBean>() {

                override fun onSuccess(t: CarInfoBean?) {
                    stopProgress()
                    carInfo = t
                    carInfo?.let {
                        initView()
                    }
                }

                override fun onFiled(message: String?) {
                    stopProgress()
                    showToast(message)
                }
            })
    }


    private fun saveMsg() {
        carInfo?.images = imageUrl
        carInfo?.type = type
        showProgress()
        HttpServerImpl.saveVehicle(carInfo).subscribe(object : HttpResultSubscriber<CarInfoBean>() {

            override fun onSuccess(t: CarInfoBean?) {
                showToast("保存成功！")
                stopProgress()
            }

            override fun onFiled(message: String?) {
                stopProgress()
                showToast(message)
            }
        })
    }


    private fun initView() {
        title.text = carInfo?.title
        val adapter = ItemAdapter(carInfo?.itemVoList)
        adapter.setOnItemClickListener(R.id.item_view) { _, position ->
            showEditDialog(position)
        }
        recycleView.adapter = adapter
    }


    /**
     * 显示修改弹窗
     */
    private fun showEditDialog(position: Int) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this, R.style.dialogNoBg)
        val view: View =
            LayoutInflater.from(this).inflate(R.layout.dialog_edit_item, null)
        builder.setView(view)
        val dialog: AlertDialog = builder.create()
        view.findViewById<View>(R.id.close).setOnClickListener {
            dialog.dismiss()
        }
        val title = view.findViewById<TextView>(R.id.title)
        title.text = "请输入正确的${carInfo?.itemVoList?.get(position)?.name}"
        val editText = view.findViewById<EditText>(R.id.edit_text)
        view.findViewById<View>(R.id.commit).setOnClickListener {
            val text = editText.text.toString()
            if (text.isEmpty()) {
                showToast("请填写正确内容！")
                return@setOnClickListener
            }
            showReDialog(position, text)
            dialog.dismiss()
        }
        dialog.show()
        val manager: WindowManager = this.windowManager
        val display = manager.defaultDisplay
        val window = dialog.window
        val params = window?.attributes
        params?.width = display.width - SizeUtils.dp2px(30f)
        dialog.window?.attributes = params
    }


    /**
     * 显示确定弹窗
     */
    private fun showReDialog(position: Int, text: String) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this, R.style.dialogNoBg)
        val view: View =
            LayoutInflater.from(this).inflate(R.layout.dialog_re_queren, null)
        builder.setView(view)
        val dialog: AlertDialog = builder.create()
        view.findViewById<View>(R.id.close).setOnClickListener {
            dialog.dismiss()
        }
        val title = view.findViewById<TextView>(R.id.title)
        title.text = "${carInfo?.itemVoList?.get(position)?.name}"
        val message = view.findViewById<TextView>(R.id.message)
        message.text = text
        view.findViewById<View>(R.id.commit).setOnClickListener {
            carInfo?.itemVoList?.get(position)?.value = text
            initView()
            dialog.dismiss()
        }
        dialog.show()
        val manager: WindowManager = this.windowManager
        val display = manager.defaultDisplay
        val window = dialog.window
        val params = window?.attributes
        params?.width = display.width - SizeUtils.dp2px(30f)
        dialog.window?.attributes = params
    }


    class ItemAdapter(dataList: MutableList<ItemVoListBean>?) :
        LGRecycleViewAdapter<ItemVoListBean>(
            dataList
        ) {

        override fun getLayoutId(viewType: Int): Int {
            return R.layout.item_result
        }

        override fun convert(holder: LGViewHolder?, t: ItemVoListBean?, position: Int) {
            holder?.setText(R.id.name, "${t?.name} : ${t?.value}")
        }
    }
}