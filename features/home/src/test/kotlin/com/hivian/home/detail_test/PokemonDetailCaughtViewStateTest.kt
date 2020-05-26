package com.hivian.home.detail_test

import com.hivian.home.common.PokemonHomeCaughtViewState
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class PokemonDetailCaughtViewStateTest {

    private lateinit var state: PokemonHomeCaughtViewState

    @Test
    fun `set state as added to caught should be settled`() {
        state = PokemonHomeCaughtViewState.AddedToCaught

        assertTrue(state.isAddedToCaught())
        assertFalse(state.isRemovedFromCaught())
    }

    @Test
    fun `set state as removed from caught should be settled`() {
        state = PokemonHomeCaughtViewState.RemovedFromCaught

        assertTrue(state.isRemovedFromCaught())
        assertFalse(state.isAddedToCaught())
    }

}
