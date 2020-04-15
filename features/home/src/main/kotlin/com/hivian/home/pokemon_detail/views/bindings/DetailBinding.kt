package com.hivian.home.pokemon_detail.views.bindings

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.hivian.common.R
import java.util.*


object DetailBinding {

    @BindingAdapter("bgColorFromType")
    @JvmStatic fun bgColorFromType(view: TextView, type: String?) {
        val colorRes = when (type?.toLowerCase(Locale.getDefault())) {
            "grass", "bug" -> R.color.lightTeal
            "fire" -> R.color.lightRed
            "water", "fighting", "normal" -> R.color.lightBlue
            "electric", "psychic" -> R.color.lightYellow
            "poison", "ghost" -> R.color.lightPurple
            "ground", "rock" -> R.color.lightBrown
            "dark" -> R.color.black
            else -> R.color.lightBlue
        }
        val bg = view.background as GradientDrawable
        bg.setColor(convertColor(colorRes, view.context))
    }

    @ColorInt
    fun convertColor(@ColorRes color: Int, context: Context): Int {
        return ContextCompat.getColor(context, color)
    }

}