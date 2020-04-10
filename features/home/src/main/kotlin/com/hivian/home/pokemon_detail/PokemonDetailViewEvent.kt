package com.hivian.home.pokemon_detail

import com.hivian.common.base.BaseViewEvent

sealed class PokemonDetailViewEvent : BaseViewEvent {

    /**
     * Dismiss pokemon detail view.
     */
    object DismissPokemonDetailView : PokemonDetailViewEvent()

}