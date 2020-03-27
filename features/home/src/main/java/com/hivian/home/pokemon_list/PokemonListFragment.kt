package com.hivian.home.pokemon_list

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.github.ajalt.timberkt.d
import com.hivian.common.base.BaseFragment
import com.hivian.common.base.BaseViewModel
import com.hivian.common.extension.observe
import com.hivian.home.R
import com.hivian.home.databinding.PokemonListFragmentBinding
import org.koin.android.viewmodel.ext.android.viewModel


class PokemonListFragment : BaseFragment<PokemonListFragmentBinding, PokemonListViewModel>(
    layoutId = R.layout.pokemon_list_fragment
) {

    private val viewModel: PokemonListViewModel by viewModel()

    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
    }

    override fun getViewModel(): BaseViewModel = viewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.event, ::onViewEvent)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        d { "TIMBER IS WORKING" }
    }

    /**
     * Observer view event change on [PokemonListViewModel].
     *
     * @param viewEvent Event on characters list.
     */
    private fun onViewEvent(viewEvent: PokemonListEvent) {
        when (viewEvent) {
            is PokemonListEvent.OpenPokemonDetail ->
                findNavController().navigate(
                    PokemonListFragmentDirections.actionPokemonListFragmentToPokemonDetailFragment(viewEvent.id))
        }
    }

}
