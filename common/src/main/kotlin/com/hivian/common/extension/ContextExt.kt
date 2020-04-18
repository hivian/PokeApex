package com.hivian.common.extension

import android.content.Context
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.hivian.common.R

/**
 * Get resource string from optional id
 *
 * @param resId Resource string identifier.
 * @return The key value if exist, otherwise empty.
 */
fun Context.getString(@StringRes resId: Int?) =
    resId?.let {
        getString(it)
    } ?: run {
        ""
    }
