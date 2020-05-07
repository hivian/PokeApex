package com.hivian.home.detail_test

import com.hivian.home.pokemon_detail.PokemonDetailNetworkViewState
import com.hivian.repository.utils.ErrorEntity
import org.junit.Assert.*
import org.junit.Test

class PokemonDetailNetworkViewStateTest {

    private lateinit var state: PokemonDetailNetworkViewState

    @Test
    fun `set state as loaded should be settled`() {
        state = PokemonDetailNetworkViewState.Loaded

        assertTrue(state.isLoaded())
        assertFalse(state.isLoading())
        assertFalse(state.isError())
        assertFalse(state.isErrorWithData())
    }

    @Test
    fun `set state as loading should be settled`() {
        state = PokemonDetailNetworkViewState.Loading

        assertTrue(state.isLoading())
        assertFalse(state.isLoaded())
        assertFalse(state.isError())
        assertFalse(state.isErrorWithData())
    }

    @Test
    fun `set state as error should be settled`() {
        state = PokemonDetailNetworkViewState.Error(ErrorEntity.Unknown)

        assertTrue(state.isError())
        assertFalse(state.isLoaded())
        assertFalse(state.isLoading())
        assertFalse(state.isErrorWithData())
    }

    @Test
    fun `set state as error with data should be settled`() {
        state = PokemonDetailNetworkViewState.ErrorWithData

        assertTrue(state.isErrorWithData())
        assertFalse(state.isError())
        assertFalse(state.isLoaded())
        assertFalse(state.isLoading())
    }

}
