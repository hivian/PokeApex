package com.hivian.home.pokemon_detail

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.navigation.fragment.navArgs
import com.hivian.common.base.BaseFragment
import com.hivian.common.base.BaseViewModel

import com.hivian.home.R
import com.hivian.home.databinding.PokemonDetailFragmentBinding
import org.koin.android.viewmodel.ext.android.viewModel

class PokemonDetailFragment : BaseFragment<PokemonDetailFragmentBinding, PokemonDetailViewModel>(
    layoutId = R.layout.pokemon_detail_fragment
) {

    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
    }

    override fun getViewModel(): BaseViewModel = viewModel

    private val viewModel: PokemonDetailViewModel by viewModel()
    private val args: PokemonDetailFragmentArgs by navArgs()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.loadDataWhenActivityStarts(args.pokemonName)
    }

}
