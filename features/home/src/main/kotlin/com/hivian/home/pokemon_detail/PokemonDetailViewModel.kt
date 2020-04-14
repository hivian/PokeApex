package com.hivian.home.pokemon_detail

import androidx.lifecycle.*
import com.hivian.common.base.BaseViewModel
import com.hivian.common.extension.capitalize
import com.hivian.common.livedata.SingleLiveData
import com.hivian.home.domain.PokemonDetailUseCase
import com.hivian.model.domain.Pokemon
import com.hivian.model.domain.PokemonStats
import com.hivian.repository.AppDispatchers
import com.hivian.repository.utils.ResultWrapper
import kotlinx.coroutines.launch

class PokemonDetailViewModel(private val pokemonDetailUseCase: PokemonDetailUseCase,
                             private val dispatchers: AppDispatchers) : BaseViewModel() {

    private val _data = MediatorLiveData<Pokemon>()
    val data: LiveData<Pokemon> = Transformations.map(_data) { pokemon ->
        pokemon.apply { name = name.capitalize() }
    }

    // FOR event
    val event = SingleLiveData<PokemonDetailViewEvent>()

    // FOR state
    private val _state = MutableLiveData<PokemonDetailViewState>()
    val state: LiveData<PokemonDetailViewState> get() = _state


    // ============================================================================================
    //  Public methods
    // ============================================================================================

    /**
     * Fetch selected character detail info.
     *
     * @param pokemonName Pokemon name.
     */
    fun loadPokemonDetail(pokemonName: String) {
        _state.postValue(PokemonDetailViewState.Loading)
        viewModelScope.launch(dispatchers.main) {
            when (val result = pokemonDetailUseCase(pokemonName)) {
                is ResultWrapper.Success -> {
                    _data.value = result.value
                    _state.value = PokemonDetailViewState.Loaded
                }
                is ResultWrapper.GenericError -> {
                    _data.value = result.value
                    _state.value = PokemonDetailViewState.Error
                }
                is ResultWrapper.NetworkError -> {
                    _data.value = result.value
                    _state.value = PokemonDetailViewState.Error
                }
            }
        }
    }

    /**
     * Send interaction event for dismiss character detail view.
     */
    fun dismissCharacterDetail() {
        event.value = PokemonDetailViewEvent.DismissPokemonDetailView
    }

    fun statToPercentage(stat: Int): Int {
        return 100 * stat / 255
    }
}
