package com.hivian.common.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hivian.common.livedata.SingleLiveData

abstract class BaseViewModel: ViewModel() {

    // FOR ERROR HANDLER
    val snackBarError = SingleLiveData<Int>()

}
