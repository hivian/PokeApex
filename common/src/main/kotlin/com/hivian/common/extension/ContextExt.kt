package com.hivian.common.extension

import android.content.Context
import androidx.annotation.DimenRes
import androidx.annotation.StringRes

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

/**
 * Retrieve pixel size rounded to integer from dimensions resource id
 * @param dimenRes Resource dimension value
 */
fun Context.getDimenPx(@DimenRes dimenRes: Int) =
    resources.getDimensionPixelSize(dimenRes)
