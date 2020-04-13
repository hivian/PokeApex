package com.hivian.home.pokemon_list.views.customs

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.hivian.home.R
import com.hivian.home.databinding.ViewBaseStatBinding

class BaseStatView: LinearLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initAttrs(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        initAttrs(attrs)
    }

    var viewBinding: ViewBaseStatBinding =
        ViewBaseStatBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        orientation = HORIZONTAL
        gravity = Gravity.CENTER_VERTICAL
    }

    private fun initView(
        title: String?,
        value: String?,
        progressValue: Int
    ) {
        viewBinding.title = title
        viewBinding.value = value
        viewBinding.progressValue = progressValue
    }

    private fun initAttrs(attrs: AttributeSet) {
        val a = getAttrs(attrs)
        try {
            val title = a.getString(R.styleable.BaseStatView_title)
            val value = a.getString(R.styleable.BaseStatView_value)
            val progressValue = a.getInt(R.styleable.BaseStatView_progressValue, 0)

            initView(title, value, progressValue)
        } finally {
            a.recycle()
        }
    }

    private fun getAttrs(attrs: AttributeSet) : TypedArray {
        return context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.BaseStatView,
            0, 0
        )
    }

}