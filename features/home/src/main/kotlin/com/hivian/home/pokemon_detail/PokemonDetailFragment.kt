package com.hivian.home.pokemon_detail

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.hivian.common.base.BaseFragment
import com.hivian.common.base.BaseViewEvent
import com.hivian.common.base.BaseViewModel
import com.hivian.common.base.BaseViewState
import com.hivian.common.extension.observe
import com.hivian.common.extension.toast
import com.hivian.common.ui.views.ProgressBarDialog
import com.hivian.home.R
import com.hivian.home.databinding.FragmentPokemonDetailBinding
import com.hivian.repository.utils.ErrorEntity
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PokemonDetailFragment : BaseFragment<FragmentPokemonDetailBinding, PokemonDetailViewModel>(
    layoutId = R.layout.fragment_pokemon_detail
) {

    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
        setupToolbar()
    }

    override fun getViewModel(): BaseViewModel = viewModel

    private val viewModel: PokemonDetailViewModel by viewModel {
        parametersOf(args.pokemonName)
    }
    private val args: PokemonDetailFragmentArgs by navArgs()
    private val progressDialog: ProgressBarDialog by lazy {
        ProgressBarDialog(requireContext())
    }

    /**
     * Configure app custom support action bar.
     */
    private fun setupToolbar() {
        setHasOptionsMenu(true)
        requireCompatActivity().setSupportActionBar(viewBinding.toolbar)
        requireCompatActivity().supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //val typeface = ResourcesCompat.getFont(context!!, R.font.londrina_shadow)
        //viewBinding.collapsed.setExpandedTitleTypeface(typeface)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observe(viewModel.networkState, ::onViewStateChange)
        observe(viewModel.event, ::onViewEvent)
        viewModel.loadPokemonDetail()
    }

    /**
     * Observer view state change on [PokemonDetailNetworkViewState].
     *
     * @param viewState State of pokemon detail.
     */
    private fun onViewStateChange(viewState: BaseViewState) {
        when (viewState) {
            is PokemonDetailNetworkViewState.Loaded, PokemonDetailNetworkViewState.ErrorWithData ->
                progressDialog.dismiss()
            is PokemonDetailNetworkViewState.Loading ->
                progressDialog.show(R.string.pokemon_detail_dialog_loading_text)
            is PokemonDetailNetworkViewState.Error -> {
                progressDialog.dismissWithErrorMessage(
                    when (viewState.error) {
                        ErrorEntity.Network -> R.string.network_error_text
                        ErrorEntity.NotFound -> R.string.not_found_error_text
                        ErrorEntity.AccessDenied -> R.string.denied_error_text
                        ErrorEntity.ServiceUnavailable -> R.string.unavailable_error_text
                        ErrorEntity.Unknown -> R.string.unknown_error_text
                    }
                )
            }
        }
    }

    /**
     * Observer view event change on [PokemonDetailViewModel].
     *
     * @param viewEvent Event on characters list.
     */
    private fun onViewEvent(viewEvent: BaseViewEvent) {
        when (viewEvent) {
            is PokemonDetailViewEvent.DismissPokemonDetailView -> findNavController().navigateUp()
            is PokemonDetailViewEvent.AddedToFavorites -> requireContext().toast(R.string.toast_added_to_favorites)
            is PokemonDetailViewEvent.AddedToCaught -> requireContext().toast(R.string.toast_added_to_caught)
        }
    }

}
