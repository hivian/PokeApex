package com.hivian.home.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.hivian.common.base.BaseViewModel
import com.hivian.home.R

val NAV_FRAGMENT_LIST_ID = R.id.pokemonListFragment
val NAV_FRAGMENT_DETAIL_ID = R.id.pokemonDetailFragment

/**
 * View model responsible for preparing and managing the data for [HomeFragment].
 *
 * @see ViewModel
 */
class HomeViewModel : BaseViewModel() {

    private val _state = MutableLiveData<HomeViewState>()
    val state: LiveData<HomeViewState>
        get() = _state

    /**
     * Navigation controller add destination changed listener.
     *
     * @param navController Navigation manager.
     */
    fun navigationControllerChanged(navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == NAV_FRAGMENT_LIST_ID) {
                _state.value = HomeViewState.NavigationScreen
            } else if (destination.id == NAV_FRAGMENT_DETAIL_ID) {
                _state.value = HomeViewState.FullScreen
            }
        }
    }
}