package com.hivian.home.pokemon_detail

import androidx.lifecycle.*
import com.hivian.common.base.BaseViewModel
import com.hivian.common.livedata.SingleLiveData
import com.hivian.home.domain.PokemonDetailUseCase
import com.hivian.model.domain.Pokemon
import com.hivian.repository.AppDispatchers
import com.hivian.repository.utils.ResultWrapper
import kotlinx.coroutines.launch

class PokemonDetailViewModel(private val pokemonDetailUseCase: PokemonDetailUseCase,
                             private val dispatchers: AppDispatchers) : BaseViewModel() {

    private val _data = MediatorLiveData<Pokemon>()
    val data: LiveData<Pokemon> get() = _data

    // FOR event
    val event = SingleLiveData<PokemonDetailViewEvent>()

    // FOR state
    private val _networkState = MutableLiveData<PokemonNetworkViewState>()
    val networkState: LiveData<PokemonNetworkViewState> get() = _networkState

    private val _favoriteState = MutableLiveData<PokemonFavoriteViewState>()
    val favoriteState: LiveData<PokemonFavoriteViewState> get() = _favoriteState

    private val _caughtState = MutableLiveData<PokemonCaughtViewState>()
    val caughtState: LiveData<PokemonCaughtViewState> get() = _caughtState


    // ============================================================================================
    //  Public methods
    // ============================================================================================

    /**
     * Fetch selected character detail info.
     *
     * @param pokemonName Pokemon name.
     */
    fun loadPokemonDetail(pokemonName: String) {
        _networkState.postValue(PokemonNetworkViewState.Loading)
        viewModelScope.launch(dispatchers.main) {
            when (val result = pokemonDetailUseCase.getDetailWithCache(pokemonName)) {
                is ResultWrapper.Success -> {
                    _data.value = result.value
                    _networkState.value = PokemonNetworkViewState.Loaded
                    _favoriteState.value = updateFavoriteViewState(result.value.favorite)
                    _caughtState.value = updateCaughtViewState(result.value.caught)
                }
                is ResultWrapper.GenericError -> {
                    _data.value = result.value
                    _networkState.value = if (result.value.moves.isNotEmpty()) {
                        PokemonNetworkViewState.ErrorWithData
                    } else {
                        PokemonNetworkViewState.Error
                    }
                    _favoriteState.value = updateFavoriteViewState(result.value.favorite)
                    _caughtState.value = updateCaughtViewState(result.value.caught)
                }
                is ResultWrapper.NetworkError -> {
                    _data.value = result.value
                    _networkState.value = if (result.value.moves.isNotEmpty()) {
                        PokemonNetworkViewState.ErrorWithData
                    } else {
                        PokemonNetworkViewState.Error
                    }
                    _favoriteState.value = updateFavoriteViewState(result.value.favorite)
                    _caughtState.value = updateCaughtViewState(result.value.caught)
                }
            }
        }
    }

    /**
     * Send interaction event for dismiss character detail view.
     */
    fun dismissPokemonDetail() {
        event.value = PokemonDetailViewEvent.DismissPokemonDetailView
    }

    /**
     * Toggle [Pokemon.favorite] status.
     */
    fun toggleFavoriteStatus() {
        _data.value?.run {
            viewModelScope.launch {
                val newFavorite = !favorite
                pokemonDetailUseCase.updateFavoriteStatus(pokemonId, newFavorite)
                _data.value = _data.value.apply {
                    favorite = newFavorite
                }
                _favoriteState.value = updateFavoriteViewState(newFavorite)
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
        _data.value?.run {
            viewModelScope.launch {
                val newCaught = !caught
                pokemonDetailUseCase.updateCaughtStatus(pokemonId, newCaught)
                _data.value = _data.value.apply {
                    caught = newCaught
                }
                _caughtState.value = updateCaughtViewState(caught)
                event.value = if (newCaught) {
                    PokemonDetailViewEvent.AddedToCaught
                } else {
                    PokemonDetailViewEvent.RemovedFromCaught
                }
            }
        }
    }

    private fun updateFavoriteViewState(favorite: Boolean) = if (favorite) {
        PokemonFavoriteViewState.AddedToFavorite
    } else {
        PokemonFavoriteViewState.RemovedFromFavorite
    }

    private fun updateCaughtViewState(caught: Boolean) = if (caught) {
        PokemonCaughtViewState.AddedToCaught
    } else {
        PokemonCaughtViewState.RemovedFromCaught
    }
}
