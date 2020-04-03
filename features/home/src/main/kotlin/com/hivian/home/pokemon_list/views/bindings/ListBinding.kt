package com.hivian.home.pokemon_list.views.bindings

import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.ajalt.timberkt.d
import com.hivian.home.R
import com.hivian.home.pokemon_list.PokemonListViewState
import com.hivian.home.pokemon_list.views.adapter.PokemonListAdapterState
import org.jetbrains.anko.textResource

object ListBinding {

    @BindingAdapter("app:imageUrlRounded")
    @JvmStatic fun loadImageRounded(view: ImageView, url: String?) {
        Glide.with(view.context)
            .load(url)
            .apply(RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image))
            .apply(RequestOptions.circleCropTransform()).into(view)
    }

    @BindingAdapter("app:imageUrl")
    @JvmStatic fun loadImage(view: ImageView, url: String?) {
        Glide.with(view.context)
            .load(url)
            .apply(RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image))
            .into(view)
    }

    @BindingAdapter("app:errorMessage")
    @JvmStatic fun loadImage(view: AppCompatTextView, state: PokemonListAdapterState) {
        view.textResource = if (state.isNoMore()) {
            R.string.pokemon_list_add_no_more_text
        } else {
            R.string.pokemon_list_add_error_text
        }
    }
}