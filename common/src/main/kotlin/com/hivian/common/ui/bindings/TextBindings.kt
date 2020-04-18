package com.hivian.common.ui.bindings


import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.hivian.common.extension.capitalize
import java.util.*

/**
 * Capitalize text in [TextView]
 */
@BindingAdapter("textCapitalized")
fun textCapitalized(view : TextView, text: String?) {
    view.text = text?.capitalize()
}

/**
 * Capitalize text in [TextView]
 */
@BindingAdapter("textCapitalized")
fun textCapitalized(textView : CollapsingToolbarLayout, text: String?) {
    textView.title = text?.capitalize()
}

/**
 * Capitalize text in [TextView]
 */
@BindingAdapter("textUppercased")
fun textUppercased(view : TextView, text: String?) {
    view.text = text?.toUpperCase(Locale.getDefault())
}
