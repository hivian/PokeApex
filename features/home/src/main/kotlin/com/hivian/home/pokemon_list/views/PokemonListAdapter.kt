package com.hivian.home.pokemon_list.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hivian.common.base.BaseListAdapter
import com.hivian.common.base.BasePagedListAdapter
import com.hivian.home.pokemon_list.PokemonListViewModel
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
    override fun onCreateViewHolder(
        parent: ViewGroup,
        inflater: LayoutInflater,
        viewType: Int
    ): RecyclerView.ViewHolder = PokemonListViewHolder(inflater)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PokemonListViewHolder -> holder.bind(viewModel, getItem(position))
        }
    }

    /**
     * Return the view type of the item at position for the purposes of view recycling.
     *
     * @param position Position to query.
     * @return Integer value identifying the type of the view needed to represent at position.
     * @see BasePagedListAdapter.getItemViewType
     */
    override fun getItemViewType(position: Int) = getItemView(position).type

    /**
     * Return the stable ID for the item at position.
     *
     * @param position Adapter position to query.
     * @return The stable ID of the item at position.
     * @see BasePagedListAdapter.getItemId
     */
    override fun getItemId(position: Int) = (getItem(position)?.pokemonId ?: 0).toLong()


    /**
     * Obtain the type of view by the item position.
     *
     * @param position Current item position.
     * @return ItemView type.
     */
    internal fun getItemView(position: Int) = ItemView.POKEMON

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