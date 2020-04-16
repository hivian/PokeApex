package com.hivian.home.pokemon_detail.views.bindings


import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.hivian.common.R as RCommon
import com.hivian.home.R as RHome
import java.util.*


@BindingAdapter("bgColorFromType")
fun bgColorFromType(view: TextView, type: String?) {
    val colorRes = when (type?.toLowerCase(Locale.getDefault())) {
        "grass", "bug" -> RCommon.color.lightTeal
        "fire" -> RCommon.color.lightRed
        "water", "fighting", "normal" -> RCommon.color.lightBlue
        "electric", "psychic" -> RCommon.color.lightYellow
        "poison", "ghost" -> RCommon.color.lightPurple
        "ground", "rock" -> RCommon.color.lightBrown
        "dark" -> RCommon.color.black
        else -> RCommon.color.lightBlue
    }
    val bg = view.background as GradientDrawable
    bg.setColor(convertColor(colorRes, view.context))
}

@ColorInt
fun convertColor(@ColorRes color: Int, context: Context): Int {
    return ContextCompat.getColor(context, color)
}

/**
 * Display Pokemon's height
 * @param height height in tenths of a meter (decimeters)
 */
@BindingAdapter("heightMeter")
fun heightMeter(view : TextView, height: Int) {
    view.text = view.context.getString(RHome.string.pokemon_detail_description_height_value, height.toFloat() / 10)
}

/**
 * Display Pokemon's weight
 * @param weight weight in tenths of a kilogram (hectograms)
 */
@BindingAdapter("weightKg")
fun weightKg(view : TextView, weight: Int) {
    view.text = view.context.getString(RHome.string.pokemon_detail_description_weight_value, weight.toFloat() / 10)
}
