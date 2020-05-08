package com.hivian.home.pokemon_list

import com.hivian.common.base.BaseViewState
import com.hivian.repository.utils.ErrorEntity


/**
 * Different states for [PokemonListFragment].
 *
 * @see BaseViewState
 */
sealed class PokemonListViewState : BaseViewState {

    /**
     * Loaded characters list.
     */
    object Loaded : PokemonListViewState()

    /**
     * Loading characters list.
     */
    object Loading : PokemonListViewState()

    /**
     * Loading on add more elements into characters list.
     */
    object AddLoading : PokemonListViewState()

    /**
     * Empty pokemon list.
     */
    object Empty : PokemonListViewState()

    /**
     * Error on loading characters list.
     */
    data class Error(val error: ErrorEntity) : PokemonListViewState()

    /**
     * Error on loading characters list with local in db.
     */
    object ErrorWithData : PokemonListViewState()

    /**
     * Error on add more elements into characters list.
     */
    object AddError : PokemonListViewState()

    /**
     * No more elements for adding into characters list.
     */
    object NoMoreElements : PokemonListViewState()

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
     * Check if current view state is [Error] with local data.
     *
     * @return True if is error state, otherwise false.
     */
    fun isErrorWithData() = this is ErrorWithData

    /**
     * Check if current view state is [AddError].
     *
     * @return True if is add error state, otherwise false.
     */
    fun isAddError() = this is AddError

    /**
     * Check if current view state is [NoMoreElements].
     *
     * @return True if is no more elements state, otherwise false.
     */
    fun isNoMoreElements() = this is NoMoreElements
}
