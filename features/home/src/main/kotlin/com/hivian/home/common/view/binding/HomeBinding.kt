package com.hivian.home.common.view.binding

import android.widget.ImageButton
import androidx.databinding.BindingAdapter
import com.hivian.home.R
import com.hivian.home.common.PokemonHomeFavoriteViewState
import com.hivian.home.common.PokemonHomeCaughtViewState
import org.jetbrains.anko.imageResource

@BindingAdapter("srcState")
fun srcState(view: ImageButton, state: PokemonHomeFavoriteViewState?) {
    state?.let {
        view.imageResource = when (state) {
            PokemonHomeFavoriteViewState.AddedToFavorite -> R.drawable.ic_favorite_active
            PokemonHomeFavoriteViewState.RemovedFromFavorite -> R.drawable.ic_favorite_inactive
        }
    }
}

@BindingAdapter("srcState")
fun srcState(view: ImageButton, state: PokemonHomeCaughtViewState?) {
    state?.let {
        view.imageResource = when (state) {
            PokemonHomeCaughtViewState.AddedToCaught -> R.drawable.ic_caught_active
            PokemonHomeCaughtViewState.RemovedFromCaught -> R.drawable.ic_caught_inactive
        }
    }
}
