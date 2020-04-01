package com.hivian.home.pokemon_list.views.adapter.holders

import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.hivian.common.base.BaseViewHolder
import com.hivian.home.databinding.ListItemLoadingBinding

/**
 * Class describes characters loading view and metadata about its place within the [RecyclerView].
 *
 * @see BaseViewHolder
 */
class LoadingViewHolder(
    inflater: LayoutInflater
) : BaseViewHolder<ListItemLoadingBinding>(
    binding = ListItemLoadingBinding.inflate(inflater)
) {
    fun bind() {
        binding.executePendingBindings()
    }
}