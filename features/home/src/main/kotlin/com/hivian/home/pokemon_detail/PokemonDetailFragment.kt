package com.hivian.home.pokemon_detail

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.hivian.common.base.BaseFragment
import com.hivian.common.base.BaseViewEvent
import com.hivian.common.base.BaseViewModel
import com.hivian.common.base.BaseViewState
import com.hivian.common.extension.loadAnimator
import com.hivian.common.extension.observe
import com.hivian.common.extension.toast
import com.hivian.common.ui.views.ProgressBarDialog

import com.hivian.home.R
import com.hivian.home.databinding.PokemonDetailFragmentBinding
import org.koin.android.viewmodel.ext.android.viewModel

class PokemonDetailFragment : BaseFragment<PokemonDetailFragmentBinding, PokemonDetailViewModel>(
    layoutId = R.layout.pokemon_detail_fragment
) {

    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
        setupToolbar()
    }

    override fun getViewModel(): BaseViewModel = viewModel

    private val viewModel: PokemonDetailViewModel by viewModel()
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
        viewModel.loadPokemonDetail(args.pokemonName)
    }

    /**
     * Observer view state change on [PokemonNetworkViewState].
     *
     * @param viewState State of pokemon detail.
     */
    private fun onViewStateChange(viewState: BaseViewState) {
        when (viewState) {
            is PokemonNetworkViewState.Loaded -> {
                viewBinding.imageViewDetail.loadAnimator(R.animator.animator_pokemon_image)
                progressDialog.dismiss()
            }
            is PokemonNetworkViewState.Loading ->
                progressDialog.show(R.string.pokemon_detail_dialog_loading_text)
            is PokemonNetworkViewState.ErrorWithData ->
                viewBinding.imageViewDetail.loadAnimator(R.animator.animator_pokemon_image)
            is PokemonNetworkViewState.Error ->
                progressDialog.dismissWithErrorMessage(R.string.pokemon_detail_dialog_error_text)
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
            is PokemonDetailViewEvent.RemovedFromFavorites -> requireContext().toast(R.string.toast_removed_from_favorites)
            is PokemonDetailViewEvent.AddedToCaught -> requireContext().toast(R.string.toast_added_to_caught)
            is PokemonDetailViewEvent.RemovedFromCaught -> requireContext().toast(R.string.toast_removed_from_caught)
        }
    }

}
