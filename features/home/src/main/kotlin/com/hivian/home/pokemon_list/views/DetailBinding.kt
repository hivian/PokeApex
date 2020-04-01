package com.hivian.home.pokemon_list.views

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.ajalt.timberkt.d
import com.hivian.home.pokemon_list.PokemonListViewState

object DetailBinding {

    @BindingAdapter("app:imageUrlRounded")
    @JvmStatic fun loadImageRounded(view: ImageView, url: String?) {
        Glide.with(view.context).load(url).apply(RequestOptions.circleCropTransform()).into(view)
    }

    @BindingAdapter("app:imageUrl")
    @JvmStatic fun loadImage(view: ImageView, url: String?) {
        Glide.with(view.context).load(url).into(view)
    }
}