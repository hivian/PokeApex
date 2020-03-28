package com.hivian.home.pokemon_list

import com.hivian.common.base.BaseViewEvent


sealed class PokemonListViewEvent : BaseViewEvent {

    /**
     * Open pokemon detail view.
     *
     * @param name Pokemon identifier
     */
    data class OpenPokemonDetailView(val name: String) : PokemonListViewEvent()

}