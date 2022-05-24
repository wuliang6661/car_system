@file:JvmName("FunctionUtils")

package com.hlbw.car_system.kotlin

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.SizeUtils
import com.bumptech.glide.Glide


/**
 *@author wuliang
 *@create 2022/5/21 15:03
 *@des
 */

val Number.dp get() = SizeUtils.dp2px(this.toFloat())

fun Activity.dp(dp: Float) = SizeUtils.dp2px(dp)

fun Fragment.dp(dp: Float) = SizeUtils.dp2px(dp)

fun View.dp(dp: Float) = SizeUtils.dp2px(dp)

fun RecyclerView.ViewHolder.dp(dp: Float) = SizeUtils.dp2px(dp)

fun Context.dp(dp: Float) = SizeUtils.dp2px(dp)

fun Context.getColorInt(@ColorRes color: Int) = ContextCompat.getColor(this, color)

fun Fragment.getColorInt(@ColorRes color: Int) = ContextCompat.getColor(context!!, color)

fun View.getColorInt(@ColorRes color: Int) = ContextCompat.getColor(context, color)

fun View?.bgColor(@ColorRes color: Int) = this?.setBackgroundColor(getColorInt(color))

fun TextView?.textColor(@ColorRes color: Int) = this?.setTextColor(getColorInt(color))

fun ImageView.loadImageUrl(url: String) = Glide.with(context).load(url).into(this)


fun View?.gone() {
    this?.visibility = View.GONE
}

fun View?.visible() {
    this?.visibility = View.VISIBLE
}

fun View?.visibleOrInvisible(visible: Boolean) {
    this?.visibility = if (visible) View.VISIBLE else View.INVISIBLE
}

fun View?.invisible() {
    this?.visibility = View.INVISIBLE
}

fun View?.visibleOrGone(visible: Boolean) {
    this?.visibility = if (visible) View.VISIBLE else View.GONE
}

fun View?.visibleGone(visible: Boolean) {
    this?.visibility = if (visible) View.VISIBLE else View.GONE
}


fun Context.getActivityFromContext(): Activity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) {
            return context
        }
        context = context.baseContext
    }
    return null
}

fun Context.getCompatActivityFromContext(): AppCompatActivity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is AppCompatActivity) {
            return context
        }
        context = context.baseContext
    }
    return null
}

fun View.getCompatActivityFromView(): AppCompatActivity? {
    var context = context
    while (context is ContextWrapper) {
        if (context is AppCompatActivity) {
            return context
        }
        context = context.baseContext
    }
    return null
}

fun View.getActivityFromView(): Activity? {
    var context = context
    while (context is ContextWrapper) {
        if (context is Activity) {
            return context
        }
        context = context.baseContext
    }
    return null
}

inline fun ktTry(block: () -> Unit) {
    try {
        block()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}


