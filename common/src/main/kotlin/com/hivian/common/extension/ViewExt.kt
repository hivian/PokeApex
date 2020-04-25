package com.hivian.common.extension

import android.animation.Animator
import android.animation.AnimatorInflater
import android.widget.ImageView
import androidx.annotation.AnimatorRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar

/**
 * Transforms static java function Snackbar.make() to an extension function on View.
 */
fun Fragment.showSnackbar(snackbarText: String, timeLength: Int = Snackbar.LENGTH_LONG) {
    activity?.let { Snackbar.make(it.findViewById(android.R.id.content), snackbarText, timeLength).show() }
}

fun Fragment.showSnackbar(@StringRes snackbarTextRes: Int, timeLength: Int = Snackbar.LENGTH_LONG) {
    activity?.let { Snackbar.make(it.findViewById(android.R.id.content), snackbarTextRes, timeLength).show() }
}

/**
 * Triggers a snackbar message when the value contained by snackbarTaskMessageLiveEvent is modified.
 */
fun Fragment.setupSnackbar(lifecycleOwner: LifecycleOwner, snackbarEvent: LiveData<Int>, timeLength: Int) {
    snackbarEvent.observe(lifecycleOwner, Observer { stringRes ->
        context?.let { showSnackbar(it.getString(stringRes), timeLength) }
    })
}

/**
 * Load a custom animator into [ImageView]
 * @param animatorRes Animator resource Id
 */
fun ImageView.loadAnimator(@AnimatorRes animatorRes: Int) {
    AnimatorInflater.loadAnimator(context, animatorRes)
        .apply {
            setTarget(this@loadAnimator)
            start()
        }
}

/**
 * Load a custom animator into [ImageView]
 * @param animator Animator object
 */
fun ImageView.loadAnimator(animator: Animator) {
    animator.run {
        setTarget(this@loadAnimator)
        start()
    }
}
