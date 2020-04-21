package com.hivian.home.pokemon_list

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import com.github.ajalt.timberkt.d
import com.hivian.common.base.BaseFragment
import com.hivian.common.base.BaseViewEvent
import com.hivian.common.base.BaseViewModel
import com.hivian.common.base.BaseViewState
import com.hivian.common.extension.*
import com.hivian.home.R
import com.hivian.home.databinding.PokemonListFragmentBinding
import com.hivian.home.pokemon_list.views.adapter.PokemonListAdapter
import com.hivian.home.pokemon_list.views.adapter.PokemonListAdapterState
import com.hivian.model.domain.Pokemon
import org.koin.android.viewmodel.ext.android.viewModel


class PokemonListFragment : BaseFragment<PokemonListFragmentBinding, PokemonListViewModel> (
    layoutId = R.layout.pokemon_list_fragment
) {

    private val viewModel: PokemonListViewModel by viewModel()
    private val viewAdapter: PokemonListAdapter by lazy {
        PokemonListAdapter(viewModel)
    }

    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
        viewBinding.includeList.pokemonList.apply {
            adapter = viewAdapter
            gridLayoutManager?.let {
                it.spanSizeLookup = viewAdapter.getSpanSizeLookup()
            }
        }
        setupToolbar()
    }

    override fun getViewModel(): BaseViewModel = viewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.event, ::onViewEvent)
        observe(viewModel.data, ::onViewDataChange)
        observe(viewModel.state, ::onViewStateChange)
    }

    /**
     * Configure app custom support action bar.
     */
    private fun setupToolbar() {
        setHasOptionsMenu(true)
        requireCompatActivity().setSupportActionBar(viewBinding.toolbar)
    }

    /**
     * Observer view data change on [PokemonListViewModel].
     *
     * @param viewData Paged list of characters.
     */
    private fun onViewDataChange(viewData: List<Pokemon>) {
        val data = mutableListOf<Pokemon?>()
        d { "=> onView Data Change - ${viewData.size}:" }
        viewData.forEach {
            data.add(it)
            d { "=> ${it.name}" }
        }
        viewAdapter.submitList(data)
    }

    /**
     * Observer view state change on [PokemonListViewModel].
     *
     * @param viewState State of characters list.
     */
    private fun onViewStateChange(viewState: BaseViewState) {
        when (viewState) {
            is PokemonListViewState.Loaded ->
                viewAdapter.submitState(PokemonListAdapterState.Added)
            is PokemonListViewState.AddLoading ->
                viewAdapter.submitState(PokemonListAdapterState.AddLoading)
            is PokemonListViewState.AddError ->
                viewAdapter.submitState(PokemonListAdapterState.AddError)
            is PokemonListViewState.NoMoreElements ->
                viewAdapter.submitState(PokemonListAdapterState.NoMore)
            is PokemonListViewState.ErrorWithData ->
                showSnackbar(R.string.pokemon_list_error_text)
        }
    }

    /**
     * Observer view event change on [PokemonListViewModel].
     *
     * @param viewEvent Event on characters list.
     */
    private fun onViewEvent(viewEvent: BaseViewEvent) {
        when (viewEvent) {
            is PokemonListViewEvent.OpenPokemonDetailView -> {
                hideKeyboard()
                findNavController().navigate(
                    PokemonListFragmentDirections.actionPokemonListFragmentToPokemonDetailFragment(
                        viewEvent.name
                    )
                )
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu_pokemon_list, menu)

        configureSearchView(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)

        updateItemOptionsRefresh(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_display_all -> {
            item.isChecked = !item.isChecked
            viewModel.loadAllPokemons()
            true
        }
        R.id.action_display_favorites -> {
            item.isChecked = !item.isChecked
            viewModel.loadPokemonFavorites()
            true
        }
        R.id.action_display_caught -> {
            item.isChecked = !item.isChecked
            viewModel.loadPokemonCaught()
            true
        }
        R.id.action_jump_to_top -> {
            viewAdapter.scrollTo(0)
            true
        }
        R.id.action_refresh -> {
            showCustomDialog(
                title = R.string.dialog_title,
                message = R.string.dialog_description) {
                    viewModel.forceRefreshItems()
            }
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun updateItemOptionsRefresh(menu: Menu) {
        when (viewModel.dataFilter.value) {
            is FilterType.All -> menu.findItem(R.id.action_display_all).isChecked = true
            is FilterType.Favorites -> menu.findItem(R.id.action_display_favorites).isChecked = true
            is FilterType.Caught -> menu.findItem(R.id.action_display_caught).isChecked = true
        }
    }

    private fun configureSearchView(menu: Menu) {
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView

        searchView.queryHint = getString(R.string.pokemon_list_search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(query: String?): Boolean {
                query?.let { viewModel.loadPokemonListByPattern(it) }
                return true
            }
        })
    }

}