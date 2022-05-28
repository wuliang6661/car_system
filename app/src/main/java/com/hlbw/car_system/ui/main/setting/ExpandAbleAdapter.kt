package com.hlbw.car_system.ui.main.setting

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.blankj.utilcode.util.LogUtils
import com.hlbw.car_system.R
import com.hlbw.car_system.bean.SettingParentBean
import com.hlbw.car_system.constans.ConstansConfig

class ExpandAbleAdapter(context: Context?) : BaseExpandableListAdapter() {

    var context: Context? = null

    init {
        this.context = context
    }

    private val list: List<SettingParentBean> by lazy {
        ConstansConfig().getSettingData()
    }


    override fun getGroupCount(): Int {
        return list.size
    }

    override fun getChildrenCount(p0: Int): Int {
        return list[p0].childSettings?.size!!
    }

    override fun getGroup(p0: Int): Any {
        return list[p0]
    }

    override fun getChild(p0: Int, p1: Int): Any {
        return list[p0].childSettings!![p1]
    }

    override fun getGroupId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getChildId(p0: Int, p1: Int): Long {
        return p1.toLong()
    }


    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getGroupView(
        groupIndex: Int,
        expand: Boolean,
        v: View?,
        group: ViewGroup?
    ): View? {
        val view: View?
        val viewHolder: GroupViewHolder?
        if (v == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_setting_groups, group, false)
            viewHolder = GroupViewHolder(view)
            view.tag = viewHolder
        } else {
            view = v
            viewHolder = view.tag as GroupViewHolder?
        }
        viewHolder?.title?.text = list[groupIndex].settingName
        val resId = if (expand) R.mipmap.ic_up else R.mipmap.ic_down
        viewHolder?.settingImg?.setImageResource(resId)
        view?.setBackgroundColor(if (expand) Color.parseColor("#F0F0F0") else Color.parseColor("#ffffff"))
        return view
    }


    override fun getChildView(
        groupIndex: Int,
        childIndex: Int,
        expand: Boolean,
        v: View?,
        group: ViewGroup?
    ): View? {
        val view: View?
        val viewHolder: ChildViewHolder?
        if (v == null) {
            view =
                LayoutInflater.from(context).inflate(R.layout.item_setting_child, group, false)
            viewHolder = ChildViewHolder(view)
            view.tag = viewHolder
        } else {
            view = v
            viewHolder = view.tag as ChildViewHolder?
        }

        if (list[groupIndex].childSettings!![childIndex].type == 0){
            viewHolder?.checkLayout?.visibility = View.VISIBLE
            viewHolder?.processLayout?.visibility = View.GONE
            list[groupIndex].childSettings?.let {
                viewHolder?.childText?.text = it[childIndex].title
                viewHolder?.checkBox?.setOnCheckedChangeListener(null)
                viewHolder?.checkBox?.isChecked = it[childIndex].isOpen
                viewHolder?.checkBox?.tag = childIndex
                viewHolder?.checkBox?.setOnCheckedChangeListener { checkBox, isChecked ->
                    LogUtils.e("groupIndex ==  $groupIndex childIndex == $childIndex  checkBox.tag == ${checkBox.tag}")
                    it[checkBox.tag as Int].isOpen = isChecked
                    ConstansConfig().saveSettingData(list)
                }
            }
        }else{
            viewHolder?.checkLayout?.visibility = View.GONE
            viewHolder?.processLayout?.visibility = View.VISIBLE
            list[groupIndex].childSettings?.let {
                viewHolder?.seekBar?.progress = it[childIndex].openNum!!.toInt()
                viewHolder?.seekBar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
                    override fun onProgressChanged(
                        seekBar: SeekBar?,
                        progress: Int,
                        fromUser: Boolean
                    ) {
                    }

                    override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    }

                    override fun onStopTrackingTouch(seekBar: SeekBar?) {
                        LogUtils.e(seekBar?.progress)
                        it[childIndex].openNum = seekBar?.progress?.toFloat()
                        ConstansConfig().saveSettingData(list)
                    }
                })
            }
        }
        return view
    }


    override fun isChildSelectable(p0: Int, p1: Int): Boolean {
        return false
    }


    class GroupViewHolder(view: View?) {

        var title: TextView? = null
        var settingImg: ImageView? = null

        init {
            title = view?.findViewById(R.id.setting_title)
            settingImg = view?.findViewById(R.id.setting_img)
        }

    }


    class ChildViewHolder(view: View?) {

        var childText: TextView? = null
        var checkBox: CheckBox? = null
        var checkLayout: LinearLayout? = null
        var processLayout: LinearLayout? = null
        var seekBar : SeekBar? = null

        init {
            childText = view?.findViewById(R.id.child_text)
            checkBox = view?.findViewById(R.id.child_check)
            checkLayout = view?.findViewById(R.id.check_layout)
            processLayout = view?.findViewById(R.id.seek_bar_layout)
            seekBar = view?.findViewById(R.id.seek_bar)
        }

    }


}