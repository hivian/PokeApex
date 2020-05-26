package com.hivian.home.common

import com.hivian.common.base.BaseViewState


/**
 * Different states for [PokemonDetailFragment].
 *
 * @see BaseViewState
 */
sealed class PokemonHomeFavoriteViewState : BaseViewState {
    /**
     * Added current pokemo  to favorite list.
     */
    object AddedToFavorite : PokemonHomeFavoriteViewState()

    /**
     * Removed current pokemon to favorite list.
     */
    object RemovedFromFavorite : PokemonHomeFavoriteViewState()


    // ============================================================================================
    //  Public helpers methods
    // ============================================================================================

    /**
     * Check if current view state is [AddedToFavorite].
     *
     * @return True if add to favorite state, otherwise false.
     */
    fun isAddedToFavorite() = this is AddedToFavorite

    /**
     * Check if current view state is [RemovedFromFavorite].
     *
     * @return True if remove favorite state, otherwise false.
     */
    fun isRemovedFromFavorite() = this is RemovedFromFavorite
}
