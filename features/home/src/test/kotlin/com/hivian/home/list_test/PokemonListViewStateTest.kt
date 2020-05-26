package com.hivian.home.list_test

import com.hivian.home.pokemon_list.PokemonListViewState
import com.hivian.repository.utils.ErrorEntity
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class PokemonListViewStateTest {

    private lateinit var state: PokemonListViewState

    @Test
    fun `set state as loaded should be settled`() {
        state = PokemonListViewState.Loaded

        assertTrue(state.isLoaded())
        assertFalse(state.isLoading())
        assertFalse(state.isEmpty())
        assertFalse(state.isEmptyFavorite())
        assertFalse(state.isEmptyCaught())
        assertFalse(state.isError())
        assertFalse(state.isErrorWithData())
        assertFalse(state.isAddError())
        assertFalse(state.isNoMoreElements())
    }

    @Test
    fun `set state as loading should be settled`() {
        state = PokemonListViewState.Loading

        assertTrue(state.isLoading())
        assertFalse(state.isLoaded())
        assertFalse(state.isEmpty())
        assertFalse(state.isEmptyFavorite())
        assertFalse(state.isEmptyCaught())
        assertFalse(state.isError())
        assertFalse(state.isErrorWithData())
        assertFalse(state.isAddError())
        assertFalse(state.isNoMoreElements())
    }

    @Test
    fun `set state as empty should be settled`() {
        state = PokemonListViewState.Empty

        assertTrue(state.isEmpty())
        assertFalse(state.isLoaded())
        assertFalse(state.isEmptyFavorite())
        assertFalse(state.isEmptyCaught())
        assertFalse(state.isLoading())
        assertFalse(state.isError())
        assertFalse(state.isErrorWithData())
        assertFalse(state.isAddError())
        assertFalse(state.isNoMoreElements())
    }

    @Test
    fun `set state as empty favorite should be settled`() {
        state = PokemonListViewState.EmptyFavorite

        assertTrue(state.isEmptyFavorite())
        assertFalse(state.isEmpty())
        assertFalse(state.isLoaded())
        assertFalse(state.isEmptyCaught())
        assertFalse(state.isLoading())
        assertFalse(state.isError())
        assertFalse(state.isErrorWithData())
        assertFalse(state.isAddError())
        assertFalse(state.isNoMoreElements())
    }

    @Test
    fun `set state as empty caught should be settled`() {
        state = PokemonListViewState.EmptyCaught

        assertTrue(state.isEmptyCaught())
        assertFalse(state.isEmpty())
        assertFalse(state.isLoaded())
        assertFalse(state.isEmptyFavorite())
        assertFalse(state.isLoading())
        assertFalse(state.isError())
        assertFalse(state.isErrorWithData())
        assertFalse(state.isAddError())
        assertFalse(state.isNoMoreElements())
    }

    @Test
    fun `set state as error should be settled`() {
        state = PokemonListViewState.Error(ErrorEntity.Unknown)

        assertTrue(state.isError())
        assertFalse(state.isLoaded())
        assertFalse(state.isEmpty())
        assertFalse(state.isEmptyFavorite())
        assertFalse(state.isEmptyCaught())
        assertFalse(state.isLoading())
        assertFalse(state.isErrorWithData())
        assertFalse(state.isAddError())
        assertFalse(state.isNoMoreElements())
    }

    @Test
    fun `set state as error with data should be settled`() {
        state = PokemonListViewState.ErrorWithData

        assertTrue(state.isErrorWithData())
        assertFalse(state.isError())
        assertFalse(state.isLoaded())
        assertFalse(state.isEmpty())
        assertFalse(state.isEmptyFavorite())
        assertFalse(state.isEmptyCaught())
        assertFalse(state.isLoading())
        assertFalse(state.isAddError())
        assertFalse(state.isNoMoreElements())
    }

    @Test
    fun `set state as add error with data should be settled`() {
        state = PokemonListViewState.AddError

        assertTrue(state.isAddError())
        assertFalse(state.isErrorWithData())
        assertFalse(state.isError())
        assertFalse(state.isLoaded())
        assertFalse(state.isEmpty())
        assertFalse(state.isEmptyFavorite())
        assertFalse(state.isEmptyCaught())
        assertFalse(state.isLoading())
        assertFalse(state.isNoMoreElements())
    }

    @Test
    fun `set state as no more elements should be settled`() {
        state = PokemonListViewState.NoMoreElements

        assertTrue(state.isNoMoreElements())
        assertFalse(state.isError())
        assertFalse(state.isAddError())
        assertFalse(state.isLoaded())
        assertFalse(state.isEmpty())
        assertFalse(state.isEmptyFavorite())
        assertFalse(state.isEmptyCaught())
        assertFalse(state.isLoading())
        assertFalse(state.isErrorWithData())
    }

}
