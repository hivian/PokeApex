package com.hivian.common.extension

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.hivian.common.R


/**
 * Create an [AlertDialog]
 *
 * @param title The dialog title
 * @param message The dialog message
 * @param positiveBtn The positive button title
 * @param negativeBtn The negative button title
 * @param onPositive The action when user taps on the positive button
 *
 */
fun Fragment.showCustomDialog(@StringRes title : Int? = null, @StringRes message : Int? = null,
                        @StringRes positiveBtn: Int? = null, @StringRes negativeBtn: Int? = null,
                        onPositive : () -> Unit) {
    val builder = AlertDialog.Builder(context, R.style.AppTheme_AlertDialog)

    title?.let { builder.setTitle(it) }
    message?.let { builder.setMessage(it) }

    val dialogClickListener = DialogInterface.OnClickListener{ _, which ->
        when (which){
            DialogInterface.BUTTON_POSITIVE -> {
                onPositive()
            }
        }
    }

    builder.setPositiveButton(positiveBtn ?: R.string.btn_ok, dialogClickListener)
    builder.setNegativeButton(negativeBtn ?: R.string.btn_cancel, dialogClickListener)

    builder.create().show()
}
