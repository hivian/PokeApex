package com.hivian.home.pokemon_detail

import com.hivian.common.base.BaseViewState


/**
 * Different states for [PokemonDetailFragment].
 *
 * @see BaseViewState
 */
sealed class PokemonCaughtViewState : BaseViewState {

    /**
     * Added current character to caught list.
     */
    object AddedToCaught : PokemonCaughtViewState()

    /**
     * Removed current pokemon to caught list.
     */
    object RemovedFromCaught : PokemonCaughtViewState()

    // ============================================================================================
    //  Public helpers methods
    // ============================================================================================

    /**
     * Check if current view state is [AddedToCaught].
     *
     * @return True if add to caught state, otherwise false.
     */
    fun isAddedToCaught() = this is AddedToCaught

    /**
     * Check if current view state is [RemovedFromCaught].
     *
     * @return True if remove caught state, otherwise false.
     */
    fun isRemovedFromCaught() = this is RemovedFromCaught
}
