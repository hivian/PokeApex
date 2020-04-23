package com.hivian.home.pokemon_detail

import com.hivian.common.base.BaseViewState


/**
 * Different states for [PokemonDetailFragment].
 *
 * @see BaseViewState
 */
sealed class PokemonFavoriteViewState : BaseViewState {
    /**
     * Added current pokemo  to favorite list.
     */
    object AddedToFavorite : PokemonFavoriteViewState()

    /**
     * Removed current pokemon to favorite list.
     */
    object RemovedFromFavorite : PokemonFavoriteViewState()


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