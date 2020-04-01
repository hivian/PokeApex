package com.hivian.home.pokemon_list.views.adapter.holders

import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.hivian.common.base.BaseViewHolder
import com.hivian.home.databinding.ListItemPokemonBinding
import com.hivian.home.pokemon_list.PokemonListViewModel
import com.hivian.model.domain.Pokemon


/**
 * Class describes character view and metadata about its place within the [RecyclerView].
 *
 * @see BaseViewHolder
 */
class PokemonListViewHolder(
    inflater: LayoutInflater
) : BaseViewHolder<ListItemPokemonBinding>(
    binding = ListItemPokemonBinding.inflate(inflater)
) {

    /**
     * Bind view data binding variables.
     *
     * @param viewModel Character list view model.
     * @param item Character list item.
     */
    fun bind(viewModel: PokemonListViewModel, item: Pokemon) {
        binding.viewModel = viewModel
        binding.pokemon = item
        binding.executePendingBindings()
    }
}
