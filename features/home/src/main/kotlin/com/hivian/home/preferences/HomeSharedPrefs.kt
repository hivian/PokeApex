package com.hivian.home.preferences

import android.content.Context
import com.hivian.common.base.BaseSharedPrefs

class HomeSharedPrefs(androidContext: Context) : BaseSharedPrefs(androidContext) {

    companion object {
        const val PREF_HOME = "pref_home"

        const val PREF_FIRST_INIT = "pref_first_init"
    }

    var firstInit : Boolean?
        get() = get()[PREF_FIRST_INIT, false]
        set(firstInit) = get().set(PREF_FIRST_INIT, firstInit)

    fun get() = customPrefs(PREF_HOME)

}