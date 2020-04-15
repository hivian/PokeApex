package com.hivian.common.ui.bindings


import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.hivian.common.extension.capitalize
import java.util.*

/**
 * Capitalize text in [TextView]
 */
@BindingAdapter("textCapitalized")
fun textCapitalized(textView : TextView, text: String?) {
    textView.text = text?.capitalize()
}

/**
 * Capitalize text in [TextView]
 */
@BindingAdapter("textUppercased")
fun textUppercased(textView : TextView, text: String?) {
    textView.text = text?.toUpperCase(Locale.getDefault())
}
