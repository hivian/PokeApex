package com.hivian.home.pokemon_detail

import com.hivian.common.base.BaseViewState


/**
 * Different states for [PokemonDetailFragment].
 *
 * @see BaseViewState
 */
sealed class PokemonNetworkViewState : BaseViewState {

    /**
     * Loaded pokemon detail info.
     */
    object Loaded : PokemonNetworkViewState()

    /**
     * Loading pokemon detail info.
     */
    object Loading : PokemonNetworkViewState()

    /**
     * Error on loading pokemon detail info.
     */
    object Error : PokemonNetworkViewState()

    /**
     * Error on loading pokemons list with local in db.
     */
    object ErrorWithData : PokemonNetworkViewState()

    /**
     * Added current character to caught list.
     */
    object AddedToCaught : PokemonNetworkViewState()

    /**
     * Removed current pokemon to caught list.
     */
    object RemovedFromCaught : PokemonNetworkViewState()

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
     * Check if current view state is [Error] with local data.
     *
     * @return True if is error state, otherwise false.
     */
    fun isErrorWithData() = this is ErrorWithData

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
