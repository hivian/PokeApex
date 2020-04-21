package com.hivian.common.extension

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
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

/**
 * Display predefined Toast message from [CharSequence]
 */
fun Fragment.toast(message: CharSequence) {
    val toast = Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT)
    toast.customize(requireContext())
    toast.show()
}
/**
 * Display predefined Toast message from [Int] resource.
 */
fun Context.toast(@StringRes messageId: Int) {
    val toast = Toast.makeText(this, messageId, Toast.LENGTH_SHORT)
    toast.customize(this)
    toast.show()
}

private fun Toast.customize(context: Context) {
    view.background.setTint(ContextCompat.getColor(context, R.color.colorPrimaryDark))
    view.findViewById<TextView>(android.R.id.message).apply {
        setTextColor(ContextCompat.getColor(context, R.color.textHeadline))
    }
}

/**
 * CLear current focus and hide soft keyboard
 */
fun Fragment.hideKeyboard() {
    // check if no view has focus:
    val activity = requireActivity()
    val view = activity.currentFocus
    view?.clearFocus()
    (activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
        .hideSoftInputFromWindow(view?.windowToken, 0)
}
