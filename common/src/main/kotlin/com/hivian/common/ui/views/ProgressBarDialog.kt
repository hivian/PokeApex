package com.hivian.common.ui.views

import android.content.Context
import android.view.LayoutInflater
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import com.hivian.common.R
import com.hivian.common.databinding.ViewProgressDialogBinding
import com.hivian.common.extension.getString

/**
 * Custom progress dialog to display as alert during on long process user waiting.
 *
 * @see AlertDialog
 */
class ProgressBarDialog(
    context: Context
) : AlertDialog(context, R.style.AppTheme_AlertDialog) {

    lateinit var viewBinding: ViewProgressDialogBinding

    /**
     * Start the dialog and display it on screen. The window is placed in the application
     * layer and opaque.
     *
     * @see AlertDialog.show
     */
    override fun show() {
        show(null)
    }

    /**
     * Start the dialog and display it on screen. The window is placed in the application
     * layer and opaque.
     *
     * @param messageRes Message resource identifier.
     * @see show
     */
    fun show(@StringRes messageRes: Int?) {
        super.show()
        viewBinding = ViewProgressDialogBinding.inflate(LayoutInflater.from(context))
        setContentView(viewBinding.root)
        setCanceledOnTouchOutside(false)
        setCancelable(false)

        viewBinding.isLoading = true
        viewBinding.message = context.getString(messageRes)
    }

    /**
     * Dismiss this dialog, removing it from the screen. This method can be invoked safely
     * from any thread.
     *
     * @param messageRes Message resource identifier.
     * @see AlertDialog.dismiss
     */
    fun dismissWithErrorMessage(@StringRes messageRes: Int) {
        setCanceledOnTouchOutside(true)
        setCancelable(true)

        viewBinding.isLoading = false
        viewBinding.message = context.getString(messageRes)
    }
}
