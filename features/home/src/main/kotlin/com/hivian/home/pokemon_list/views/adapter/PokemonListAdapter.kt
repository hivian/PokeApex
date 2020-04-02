package com.hivian.home.pokemon_list.views.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.ajalt.timberkt.d
import com.hivian.common.base.BaseListAdapter
import com.hivian.home.pokemon_list.PokemonListViewModel
import com.hivian.home.pokemon_list.views.adapter.holders.ErrorViewHolder
import com.hivian.home.pokemon_list.views.adapter.holders.LoadingViewHolder
import com.hivian.home.pokemon_list.views.adapter.holders.PokemonListViewHolder
import com.hivian.model.domain.Pokemon

/**
 * Enum class containing the different type of cell view, with the configuration.
 */
internal enum class ItemView(val type: Int, val span: Int) {
    POKEMON(type = 0, span = 1),
    LOADING(type = 1, span = 2),
    ERROR(type = 2, span = 2);

    companion object {
        fun valueOf(type: Int): ItemView? = values().first { it.type == type }
    }
}

class PokemonListAdapter(val viewModel: PokemonListViewModel) : BaseListAdapter<Pokemon>(
    itemsSame = { old, new -> old.pokemonId == new.pokemonId },
    contentsSame = { old, new -> old == new }
)  {
    private var state: PokemonListAdapterState = PokemonListAdapterState.Added

    override fun onCreateViewHolder(
        parent: ViewGroup,
        inflater: LayoutInflater,
        viewType: Int
    ): RecyclerView.ViewHolder = when (ItemView.valueOf(viewType)) {
        ItemView.POKEMON -> { PokemonListViewHolder(inflater) }
        ItemView.LOADING -> { LoadingViewHolder(inflater) }
        else -> ErrorViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PokemonListViewHolder -> holder.bind(viewModel, getItem(position))
            is ErrorViewHolder -> holder.bind(viewModel)
            is LoadingViewHolder -> {}
        }
    }

    /**
     * Update current adapter state with the new one, applying visual changes.
     *
     * @param newState State of list adapter to update.
     */
    fun submitState(newState: PokemonListAdapterState) {
        val oldState = state
        state = newState
        if (newState.hasExtraRow && oldState != newState) {
           notifyItemChanged(itemCount - 1)
        }
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     * @see BasePagedListAdapter.getItemCount
     */
    override fun getItemCount() =
        if (state.hasExtraRow) {
            super.getItemCount() + 1
        } else {
            super.getItemCount()
        }

    /**
     * Return the view type of the item at position for the purposes of view recycling.
     *
     * @param position Position to query.
     * @return Integer value identifying the type of the view needed to represent at position.
     * @see BaseListAdapter.getItemViewType
     */
    override fun getItemViewType(position: Int) = getItemView(position).type

    /**
     * Return the stable ID for the item at position.
     *
     * @param position Adapter position to query.
     * @return The stable ID of the item at position.
     * @see BaseListAdapter.getItemId
     */
    override fun getItemId(position: Int) =
        when (getItemView(position)) {
            ItemView.POKEMON -> (getItem(position)?.pokemonId ?: -1).toLong()
            ItemView.LOADING -> 0L
            ItemView.ERROR -> 1L
        }

    /**
     * Obtain the type of view by the item position.
     *
     * @param position Current item position.
     * @return ItemView type.
     */
    internal fun getItemView(position: Int) : ItemView =
        if (state.hasExtraRow && position == itemCount - 1) {
            if (state.isAddError()) {
                ItemView.ERROR
            } else {
                ItemView.LOADING
            }
        } else {
            ItemView.POKEMON
        }

    /**
     * Obtain helper class to provide the number of spans each item occupies.
     *
     * @return The helper class.
     */
    fun getSpanSizeLookup(): GridLayoutManager.SpanSizeLookup =
        object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return getItemView(position).span
            }
        }

}