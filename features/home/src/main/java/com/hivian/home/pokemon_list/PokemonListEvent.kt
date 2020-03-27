package com.hivian.home.pokemon_list

sealed class PokemonListEvent {

    /**
     * Open pokemon detail view.
     *
     * @param id Pokemon identifier
     */
    data class OpenPokemonDetail(val id: Int) : PokemonListEvent()

}