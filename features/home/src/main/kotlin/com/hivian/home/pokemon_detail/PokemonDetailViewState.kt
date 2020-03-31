package com.hivian.home.pokemon_detail

import com.hivian.common.base.BaseViewState


/**
 * Different states for [PokemonDetailFragment].
 *
 * @see BaseViewState
 */
sealed class PokemonDetailViewState : BaseViewState {

    /**
     * Loaded characters list.
     */
    object Loaded : PokemonDetailViewState()

    /**
     * Loading characters list.
     */
    object Loading : PokemonDetailViewState()

    /**
     * Empty characters list.
     */
    object Empty : PokemonDetailViewState()

    /**
     * Error on loading characters list.
     */
    object Error : PokemonDetailViewState()

    /**
     * No more elements for adding into characters list.
     */
    object NoMoreElements : PokemonDetailViewState()

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
     * Check if current view state is [Empty].
     *
     * @return True if is empty state, otherwise false.
     */
    fun isEmpty() = this is Empty

    /**
     * Check if current view state is [Error].
     *
     * @return True if is error state, otherwise false.
     */
    fun isError() = this is Error

    /**
     * Check if current view state is [NoMoreElements].
     *
     * @return True if is no more elements state, otherwise false.
     */
    fun isNoMoreElements() = this is NoMoreElements
}
