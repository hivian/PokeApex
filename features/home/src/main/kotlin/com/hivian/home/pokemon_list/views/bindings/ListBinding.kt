package com.hivian.home.pokemon_list.views.bindings

import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hivian.home.R
import com.hivian.home.pokemon_list.views.adapter.PokemonListAdapterState
import org.jetbrains.anko.textResource

@BindingAdapter("app:imageUrlRounded")
fun loadImageRounded(view: ImageView, url: String?) {
    Glide.with(view.context)
        .load(url)
        .apply(RequestOptions()
            .placeholder(R.drawable.loading_animation)
            .error(R.drawable.ic_broken_image))
        .apply(RequestOptions.circleCropTransform()).into(view)
}

@BindingAdapter("app:imageUrl")
fun loadImage(view: ImageView, url: String?) {
    Glide.with(view.context)
        .load(url)
        .apply(RequestOptions()
            .placeholder(R.drawable.loading_animation)
            .error(R.drawable.ic_broken_image)
            .centerInside())
        .into(view)
}

@BindingAdapter("app:errorMessage")
fun loadImage(view: AppCompatTextView, state: PokemonListAdapterState) {
    view.textResource = if (state.isNoMore()) {
        R.string.pokemon_list_add_no_more_text
    } else {
        R.string.pokemon_list_add_error_text
    }
}
