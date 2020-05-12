package com.hivian.home.common

object CommonStateHandler {

   fun favoriteToViewState(favorite: Boolean) = if (favorite) {
        PokemonHomeFavoriteViewState.AddedToFavorite
    } else {
        PokemonHomeFavoriteViewState.RemovedFromFavorite
    }

   fun caughtToViewState(caught: Boolean) = if (caught) {
        PokemonHomeCaughtViewState.AddedToCaught
    } else {
        PokemonHomeCaughtViewState.RemovedFromCaught
    }

}