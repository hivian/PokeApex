package com.hivian.home.pokemon_list.views.adapter

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationListener(private val gridLayoutManager: GridLayoutManager) : RecyclerView.OnScrollListener() {

    companion object {
        const val PAGE_START = 1
        const val PAGE_SIZE = 20
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = gridLayoutManager.childCount
        val totalItemCount = gridLayoutManager.itemCount
        val firstVisibleItemPosition = gridLayoutManager.findFirstVisibleItemPosition()

        if (!isLoading() && !isLastPage()) {
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount &&
                    firstVisibleItemPosition >= 0 &&
                    totalItemCount >= PAGE_SIZE) {
                loadMoreItems()
            }
        }
    }

    protected abstract fun loadMoreItems()

    abstract fun isLastPage(): Boolean

    abstract fun isLoading(): Boolean

}