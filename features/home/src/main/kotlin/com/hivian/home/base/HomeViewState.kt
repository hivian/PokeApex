package com.hivian.home.base

import com.hivian.common.base.BaseViewState

/**
 * Different states for [HomeFragment].
 *
 * @see BaseViewState
 */
sealed class HomeViewState : BaseViewState {

    /**
     * Full screen mode.
     */
    object FullScreen : HomeViewState()

    /**
     * Navigation screen mode with bottom bar.
     */
    object NavigationScreen : HomeViewState()

    // ============================================================================================
    //  Public helpers methods
    // ============================================================================================

    /**
     * Check if current view state is [FullScreen].
     *
     * @return True if is full screen state, otherwise false.
     */
    fun isFullScreen() = this is FullScreen

    /**
     * Check if current view state is [NavigationScreen].
     *
     * @return True if is navigation screen state, otherwise false.
     */
    fun isNavigationScreen() = this is NavigationScreen
}
