package com.hivian.home.pokemon_detail

import com.hivian.common.base.BaseViewState


/**
 * Different states for [PokemonDetailFragment].
 *
 * @see BaseViewState
 */
sealed class PokemonDetailViewState : BaseViewState {

    /**
     * Loaded pokemon detail info.
     */
    object Loaded : PokemonDetailViewState()

    /**
     * Loading pokemon detail info.
     */
    object Loading : PokemonDetailViewState()

    /**
     * Error on loading pokemon detail info.
     */
    object Error : PokemonDetailViewState()

    /**
     * Add current pokemon to favorite list.
     */
    object AddToFavorite : PokemonDetailViewState()

    /**
     * Added current pokemon to favorite list.
     */
    object AddedToFavorite : PokemonDetailViewState()

    // ============================================================================================
    //  Public helpers methods
    // ============================================================================================

    /**
     * Check if current view state is [Loaded].
     *
     * @return True if is loaded state, otherwise false.
     */
    fun isLoaded() = this is Loaded

    /**
     * Check if current view state is [Loading].
     *
     * @return True if is loading state, otherwise false.
     */
    fun isLoading() = this is Loading

    /**
     * Check if current view state is [Error].
     *
     * @return True if is error state, otherwise false.
     */
    fun isError() = this is Error

    /**
     * Check if current view state is [AddToFavorite].
     *
     * @return True if is add to favorite state, otherwise false.
     */
    fun isAddToFavorite() = this is AddToFavorite

    /**
     * Check if current view state is [AddedToFavorite].
     *
     * @return True if is added to favorite state, otherwise false.
     */
    fun isAddedToFavorite() = this is AddedToFavorite
}
