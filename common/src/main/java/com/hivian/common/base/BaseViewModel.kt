package com.hivian.common.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.hivian.common.livedata.SingleLiveData
import com.hivian.common.navigation.NavigationCommand
import com.hivian.common.utils.Event

abstract class BaseViewModel: ViewModel() {

    // FOR ERROR HANDLER
    val snackBarError = SingleLiveData<Int>()

    // FOR NAVIGATION
    private val _navigation = MutableLiveData<Event<NavigationCommand>>()
    val navigation: LiveData<Event<NavigationCommand>> = _navigation

    /**
     * Convenient method to handle navigation from a [ViewModel]
     */
     fun navigate(directions: NavDirections) {
        _navigation.value = Event(NavigationCommand.To(directions))
    }
}
