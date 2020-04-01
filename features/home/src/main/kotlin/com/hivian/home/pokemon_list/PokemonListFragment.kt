package com.hivian.home.pokemon_list

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.github.ajalt.timberkt.d
import com.hivian.common.base.BaseFragment
import com.hivian.common.base.BaseViewEvent
import com.hivian.common.base.BaseViewModel
import com.hivian.common.extension.gridLayoutManager
import com.hivian.common.extension.observe
import com.hivian.home.R
import com.hivian.home.databinding.PokemonListFragmentBinding
import com.hivian.home.pokemon_list.views.adapter.PaginationListener
import com.hivian.home.pokemon_list.views.adapter.PokemonListAdapter
import com.hivian.home.pokemon_list.views.adapter.PokemonListAdapterState
import com.hivian.model.domain.Pokemon
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel



class PokemonListFragment : BaseFragment<PokemonListFragmentBinding, PokemonListViewModel>(
    layoutId = R.layout.pokemon_list_fragment
) {

    private val viewModel: PokemonListViewModel by viewModel()
    private val viewAdapter : PokemonListAdapter by inject()

    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
        viewBinding.includeList.pokemonList.apply {
            adapter = viewAdapter
            gridLayoutManager?.let {
                it.spanSizeLookup = viewAdapter.getSpanSizeLookup()
                addOnScrollListener(object : PaginationListener(it) {
                    override fun loadMoreItems() {
                        viewModel.loadMoreItem()
                    }

                    override fun isLastPage(): Boolean = false

                    override fun isLoading(): Boolean = viewModel.state.value!!.isAddLoading()

                })
            }
        }
    }

    override fun getViewModel(): BaseViewModel = viewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.event, ::onViewEvent)
        observe(viewModel.data, ::onViewDataChange)
        observe(viewModel.state, ::onViewStateChange)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        d { "TIMBER IS WORKING" }
    }

    /**
     * Observer view data change on [PokemonListViewModel].
     *
     * @param viewData Paged list of characters.
     */
    private fun onViewDataChange(viewData: List<Pokemon>) {
        d { "=> onView Data Change - ${viewData.size}:" }
        viewData.forEach {
            d { "=> ${it.name}" }
        }
        viewAdapter.submitList(viewData)
    }

    /**
     * Observer view state change on [PokemonListViewModel].
     *
     * @param viewState State of characters list.
     */
    private fun onViewStateChange(viewState: PokemonListViewState) {
        when (viewState) {
            is PokemonListViewState.Loaded ->
                viewAdapter.submitState(PokemonListAdapterState.Added)
            is PokemonListViewState.AddLoading ->
                viewAdapter.submitState(PokemonListAdapterState.AddLoading)
            is PokemonListViewState.AddError ->
                viewAdapter.submitState(PokemonListAdapterState.AddError)
            is PokemonListViewState.NoMoreElements ->
                viewAdapter.submitState(PokemonListAdapterState.NoMore)
        }
    }

    /**
     * Observer view event change on [PokemonListViewModel].
     *
     * @param viewEvent Event on characters list.
     */
    private fun onViewEvent(viewEvent: BaseViewEvent) {
        when (viewEvent) {
            is PokemonListViewEvent.OpenPokemonDetailView ->
                findNavController().navigate(
                    PokemonListFragmentDirections.actionPokemonListFragmentToPokemonDetailFragment(viewEvent.name))
        }
    }

}
