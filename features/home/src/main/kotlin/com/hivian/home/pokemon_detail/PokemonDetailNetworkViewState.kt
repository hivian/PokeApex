package com.hivian.home.pokemon_detail

import com.hivian.common.base.BaseViewState
import com.hivian.repository.utils.ErrorEntity


/**
 * Different states for [PokemonDetailFragment].
 *
 * @see BaseViewState
 */
sealed class PokemonDetailNetworkViewState : BaseViewState {

    /**
     * Loaded pokemon detail info.
     */
    object Loaded : PokemonDetailNetworkViewState()

    /**
     * Loading pokemon detail info.
     */
    object Loading : PokemonDetailNetworkViewState()

    /**
     * Error on loading pokemon detail info.
     */
    data class Error(val error: ErrorEntity) : PokemonDetailNetworkViewState()

    /**
     * Error on loading pokemons list with local in db.
     */
    object ErrorWithData : PokemonDetailNetworkViewState()

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
}
