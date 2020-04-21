package com.hivian.home.pokemon_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.hivian.common.base.BaseViewModel
import com.hivian.common.livedata.SingleLiveData
import com.hivian.home.domain.PokemonDetailUseCase
import com.hivian.model.domain.Pokemon
import com.hivian.repository.AppDispatchers
import com.hivian.repository.utils.NetworkWrapper
import kotlinx.coroutines.launch

class PokemonDetailViewModel(private val name: String,
                            private val pokemonDetailUseCase: PokemonDetailUseCase,
                            private val dispatchers: AppDispatchers) : BaseViewModel() {

    val data: LiveData<Pokemon> = pokemonDetailUseCase.getPokemonDetailLive(name)

    // FOR event
    val event = SingleLiveData<PokemonDetailViewEvent>()

    // FOR state
    private val _networkState = MutableLiveData<PokemonNetworkViewState>()
    val networkState: LiveData<PokemonNetworkViewState> get() = _networkState

    val favoriteState: LiveData<PokemonFavoriteViewState> = Transformations.map(data) {
        favoriteToViewState(it.favorite)
    }

    val caughtState: LiveData<PokemonCaughtViewState> get() = Transformations.map(data) {
        caughtToViewState(it.caught)
    }


    // ============================================================================================
    //  Public methods
    // ============================================================================================

    /**
     * Fetch selected pokemon detail info.
     *
     */
    fun loadPokemonDetail() {
        _networkState.postValue(PokemonNetworkViewState.Loading)
        viewModelScope.launch(dispatchers.main) {
            when (val result = pokemonDetailUseCase.getDetailWithCache(name)) {
                is NetworkWrapper.Success -> {
                    _networkState.value = PokemonNetworkViewState.Loaded
                }
                is NetworkWrapper.GenericError -> {
                    _networkState.value = PokemonNetworkViewState.Error
                }
                is NetworkWrapper.NetworkError -> {
                    _networkState.value = PokemonNetworkViewState.Error
                }
            }
        }
    }

    /**
     * Send interaction event for dismiss Pokemon detail view.
     */
    fun dismissPokemonDetail() {
        event.value = PokemonDetailViewEvent.DismissPokemonDetailView
    }

    /**
     * Toggle [Pokemon.favorite] status.
     */
    fun toggleFavoriteStatus() {
        data.value?.run {
            viewModelScope.launch {
                val newFavorite = !favorite
                pokemonDetailUseCase.updateFavoriteStatus(pokemonId, newFavorite)
                event.value = if (newFavorite) {
                    PokemonDetailViewEvent.AddedToFavorites
                } else {
                    PokemonDetailViewEvent.RemovedFromFavorites
                }
            }
        }

    }

    /**
     * Toggle [Pokemon.caught] status.
     */
    fun toggleCaughtStatus() {
        data.value?.run {
            viewModelScope.launch {
                val newCaught = !caught
                pokemonDetailUseCase.updateCaughtStatus(pokemonId, newCaught)
                event.value = if (newCaught) {
                    PokemonDetailViewEvent.AddedToCaught
                } else {
                    PokemonDetailViewEvent.RemovedFromCaught
                }
            }
        }
    }

    private fun favoriteToViewState(favorite: Boolean) = if (favorite) {
        PokemonFavoriteViewState.AddedToFavorite
    } else {
        PokemonFavoriteViewState.RemovedFromFavorite
    }

    private fun caughtToViewState(caught: Boolean) = if (caught) {
        PokemonCaughtViewState.AddedToCaught
    } else {
        PokemonCaughtViewState.RemovedFromCaught
    }
}
