package com.hivian.home.pokemon_detail

import com.hivian.common.base.BaseViewEvent

sealed class PokemonDetailViewEvent : BaseViewEvent {

    /**
     * Dismiss pokemon detail view.
     */
    object DismissPokemonDetailView : PokemonDetailViewEvent()

    /**
     * Added to favorites event
     */
    object AddedToFavorites: PokemonDetailViewEvent()

    /**
     * Removed from favorites event
     */
    object RemovedFromFavorites: PokemonDetailViewEvent()

    /**
     * Added to caught event
     */
    object AddedToCaught: PokemonDetailViewEvent()

    /**
     * Removed from caught event
     */
    object RemovedFromCaught: PokemonDetailViewEvent()

}