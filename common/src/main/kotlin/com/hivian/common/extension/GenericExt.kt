package com.hivian.common.extension

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


fun <T> T.toLiveData(): LiveData<T> {
    val results = MutableLiveData<T>()
    results.value = this
    return results
}