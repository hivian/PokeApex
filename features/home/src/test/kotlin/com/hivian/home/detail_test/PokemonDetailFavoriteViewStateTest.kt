package com.hivian.home.detail_test

import com.hivian.home.pokemon_detail.PokemonDetailFavoriteViewState
import org.junit.Assert.*
import org.junit.Test

class PokemonDetailFavoriteViewStateTest {

    private lateinit var state: PokemonDetailFavoriteViewState

    @Test
    fun `set state as AddedToFavorite should be settled`() {
        state = PokemonDetailFavoriteViewState.AddedToFavorite

        assertTrue(state.isAddedToFavorite())
        assertFalse(state.isRemovedFromFavorite())
    }

    @Test
    fun `set state as RemovedFromFavorite should be settled`() {
        state = PokemonDetailFavoriteViewState.RemovedFromFavorite

        assertTrue(state.isRemovedFromFavorite())
        assertFalse(state.isAddedToFavorite())
    }

}
