package com.hivian.home

import com.hivian.home.pokemon_list.PokemonListViewState
import org.junit.Assert
import org.junit.Test

class PokemonListViewStateTest {

    private lateinit var state: PokemonListViewState

    @Test
    fun `set state as loaded should be settled`() {
        state = PokemonListViewState.Loaded

        Assert.assertTrue(state.isLoaded())
        Assert.assertFalse(state.isLoading())
        Assert.assertFalse(state.isEmpty())
        Assert.assertFalse(state.isError())
        Assert.assertFalse(state.isNoMoreElements())
    }

    @Test
    fun `set state as loading should be settled`() {
        state = PokemonListViewState.Loading

        Assert.assertTrue(state.isLoading())
        Assert.assertFalse(state.isLoaded())
        Assert.assertFalse(state.isEmpty())
        Assert.assertFalse(state.isError())
        Assert.assertFalse(state.isNoMoreElements())
    }

    @Test
    fun `set state as empty should be settled`() {
        state = PokemonListViewState.Empty

        Assert.assertTrue(state.isEmpty())
        Assert.assertFalse(state.isLoaded())
        Assert.assertFalse(state.isLoading())
        Assert.assertFalse(state.isError())
        Assert.assertFalse(state.isNoMoreElements())
    }

    @Test
    fun `set state as error should be settled`() {
        state = PokemonListViewState.Error

        Assert.assertTrue(state.isError())
        Assert.assertFalse(state.isLoaded())
        Assert.assertFalse(state.isLoading())
        Assert.assertFalse(state.isEmpty())
        Assert.assertFalse(state.isNoMoreElements())
    }

    @Test
    fun `set state as no more elements should be settled`() {
        state = PokemonListViewState.NoMoreElements

        Assert.assertTrue(state.isNoMoreElements())
        Assert.assertFalse(state.isLoaded())
        Assert.assertFalse(state.isLoading())
        Assert.assertFalse(state.isEmpty())
        Assert.assertFalse(state.isError())
    }

}
