package com.hivian.home.pokemon_detail.views.bindings


import android.animation.Animator
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.view.animation.Animation
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.hivian.common.extension.getDimenPx
import com.hivian.common.extension.loadAnim
import com.hivian.common.extension.loadAnimator
import org.jetbrains.anko.textColorResource
import java.util.*
import com.hivian.common.R as RCommon
import com.hivian.home.R as RHome


@BindingAdapter("bgColorFromType")
fun bgColorFromType(view: TextView, type: String?) {
    val colorRes = colorResFromType(type)
    val mutateDrawable = mutateBackground(view)
    mutateDrawable.setColor(convertColor(colorRes, view.context))
}

@BindingAdapter("bgStrokeColorFromType")
fun bgStrokeColorFromType(view: TextView, type: String?) {
    val colorRes = colorResFromType(type)
    val mutateDrawable = mutateBackground(view)
    mutateDrawable.setStroke(
        view.context.getDimenPx(RCommon.dimen.stroke),
        convertColor(colorRes, view.context))
}

@BindingAdapter("textColorFromType")
fun textColorFromType(view: TextView, type: String?) {
    view.textColorResource = colorResFromType(type)
}

private fun colorResFromType(type: String?) : Int = when (type?.toLowerCase(Locale.getDefault())) {
    "grass", "bug" -> RCommon.color.lightTeal
    "fire" -> RCommon.color.lightRed
    "water", "fighting", "normal" -> RCommon.color.lightBlue
    "electric", "psychic" -> RCommon.color.lightYellow
    "poison", "ghost" -> RCommon.color.lightPurple
    "ground", "rock" -> RCommon.color.lightBrown
    "dark" -> RCommon.color.black
    else -> RCommon.color.lightBlue
}

/**
 * A mutable drawable is guaranteed to not share its state with any other drawable
 */
private fun mutateBackground(view: TextView) : GradientDrawable {
    val bg = view.background as GradientDrawable
    return bg.mutate() as GradientDrawable
}

@ColorInt
private fun convertColor(@ColorRes color: Int, context: Context): Int {
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

@BindingAdapter("animator")
fun animator(view: View,  anim: Animator) {
    view.loadAnimator(anim)
}

@BindingAdapter("anim", "animStart")
fun anim(view: View, anim: Animation, animStart: Boolean) {
    if (animStart)
        view.loadAnim(anim)
}
