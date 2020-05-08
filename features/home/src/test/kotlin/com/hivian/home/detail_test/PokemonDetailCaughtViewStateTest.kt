package com.hivian.home.detail_test

import com.hivian.home.pokemon_detail.PokemonDetailCaughtViewState
import com.hivian.home.pokemon_detail.PokemonDetailFavoriteViewState
import org.junit.Assert.*
import org.junit.Test

class PokemonDetailCaughtViewStateTest {

    private lateinit var state: PokemonDetailCaughtViewState

    @Test
    fun `set state as added to caught should be settled`() {
        state = PokemonDetailCaughtViewState.AddedToCaught

        assertTrue(state.isAddedToCaught())
        assertFalse(state.isRemovedFromCaught())
    }

    @Test
    fun `set state as removed from caught should be settled`() {
        state = PokemonDetailCaughtViewState.RemovedFromCaught

        assertTrue(state.isRemovedFromCaught())
        assertFalse(state.isAddedToCaught())
    }

}
