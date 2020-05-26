package com.hivian.home.detail_test

import com.hivian.home.common.PokemonHomeFavoriteViewState
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class PokemonDetailFavoriteViewStateTest {

    private lateinit var state: PokemonHomeFavoriteViewState

    @Test
    fun `set state as AddedToFavorite should be settled`() {
        state = PokemonHomeFavoriteViewState.AddedToFavorite

        assertTrue(state.isAddedToFavorite())
        assertFalse(state.isRemovedFromFavorite())
    }

    @Test
    fun `set state as RemovedFromFavorite should be settled`() {
        state = PokemonHomeFavoriteViewState.RemovedFromFavorite

        assertTrue(state.isRemovedFromFavorite())
        assertFalse(state.isAddedToFavorite())
    }

}
