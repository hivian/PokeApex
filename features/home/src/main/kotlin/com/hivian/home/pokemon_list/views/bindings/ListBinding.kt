package com.hivian.home.pokemon_list.views.bindings

import android.view.Menu
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hivian.common.extension.hideKeyboard
import com.hivian.home.R
import com.hivian.home.pokemon_list.FilterType
import com.hivian.home.pokemon_list.PokemonListViewState
import com.hivian.home.pokemon_list.views.adapter.PokemonListAdapterState
import com.hivian.repository.utils.ErrorEntity
import org.jetbrains.anko.textResource

@BindingAdapter("imageUrlRounded")
fun loadImageRounded(view: ImageView, url: String?) {
    Glide.with(view.context)
        .load(url)
        .apply(RequestOptions()
            .placeholder(R.drawable.loading_animation)
            .error(R.drawable.ic_broken_image))
        .apply(RequestOptions.circleCropTransform()).into(view)
}

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {
    Glide.with(view.context)
        .load(url)
        .apply(RequestOptions()
            .placeholder(R.drawable.loading_animation)
            .error(R.drawable.ic_broken_image)
            .centerInside())
        .into(view)
}

@BindingAdapter("errorMessage")
fun loadImage(view: AppCompatTextView, state: PokemonListAdapterState) {
    view.textResource = if (state.isNoMore()) {
        R.string.pokemon_list_add_no_more_text
    } else {
        R.string.pokemon_list_add_error_text
    }
}

@BindingAdapter("textError")
fun textError(view: TextView, state: PokemonListViewState) {
    if (state is PokemonListViewState.Error) {
        view.textResource = when (state.error) {
            ErrorEntity.Network -> R.string.network_error_text
            ErrorEntity.NotFound -> R.string.not_found_error_text
            ErrorEntity.AccessDenied -> R.string.denied_error_text
            ErrorEntity.ServiceUnavailable -> R.string.unavailable_error_text
            ErrorEntity.Unknown -> R.string.unknown_error_text
        }
    }
}

@BindingAdapter("listRefreshEnabled")
fun listRefreshEnabled(view: SwipeRefreshLayout, filter: FilterType) {
    when (filter) {
        is FilterType.All -> view.isEnabled = true
        is FilterType.Favorite, FilterType.Caught -> view.isEnabled = false
    }
}

@BindingAdapter("actionViewVisibility")
fun actionViewVisibility(view: Toolbar, filter: FilterType) {
    val searchItem = view.menu.findItem(R.id.action_search)
    when (filter) {
        is FilterType.All -> searchItem?.isVisible = true
        is FilterType.Favorite, FilterType.Caught -> {
            searchItem?.isVisible = false
            searchItem?.collapseActionView()
        }
    }
}
