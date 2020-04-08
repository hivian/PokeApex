package com.hivian.common.ui.bindings


import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.hivian.common.extension.capitalize

/**
 * Capitalize text in [TextView]
 */
@BindingAdapter("textCapitalized")
fun textCapitalized(textView : TextView, text: String) {
    textView.text = text.capitalize()
}