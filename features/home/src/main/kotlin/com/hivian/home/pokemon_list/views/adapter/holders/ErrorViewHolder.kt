package com.hivian.home.pokemon_list.views.adapter.holders

import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.hivian.common.base.BaseViewHolder
import com.hivian.home.databinding.ListItemErrorBinding
import com.hivian.home.pokemon_list.PokemonListViewModel

/**
 * Class describes characters error view and metadata about its place within the [RecyclerView].
 *
 * @see BaseViewHolder
 */
class ErrorViewHolder(
    inflater: LayoutInflater
) : BaseViewHolder<ListItemErrorBinding>(
    binding = ListItemErrorBinding.inflate(inflater)
) {

    /**
     * Bind view data binding variables.
     *
     * @param viewModel character list view model.
     */
    fun bind(viewModel: PokemonListViewModel) {
        binding.viewModel = viewModel
        binding.executePendingBindings()
    }
}