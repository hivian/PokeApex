package com.hivian.home.pokemon_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.hivian.common.base.BaseViewModel
import com.hivian.common.livedata.SingleLiveData
import com.hivian.home.domain.PokemonListUseCase
import com.hivian.model.domain.Pokemon
import com.hivian.repository.AppDispatchers
import com.hivian.repository.utils.NetworkWrapper
import kotlinx.coroutines.launch

/**
 * A simple [BaseViewModel] that provide the data and handle logic to communicate with the model
 * for [PokemonListFragment].
 */
sealed class FilterType {
    data class All(val pattern: String = ""): FilterType()
    data class Favorites(val pattern: String = ""): FilterType()
    data class Caught(val pattern: String =  ""): FilterType()
}

class PokemonListViewModel(private val pokemonListUseCase: PokemonListUseCase,
                           private val dispatchers: AppDispatchers)
    : BaseViewModel() {

    // FOR data
    var dataFilter = MutableLiveData<FilterType>(FilterType.All())
    val data : LiveData<List<Pokemon>> = Transformations.switchMap(dataFilter) {
        when (it) {
            is FilterType.All -> pokemonListUseCase.getAllPokemonFilter(it.pattern)
            is FilterType.Favorites -> pokemonListUseCase.getAllPokemonFavoritesFilter(it.pattern)
            is FilterType.Caught -> pokemonListUseCase.getAllPokemonCaughtFilter(it.pattern)
        }
    }

    private var currentSearchPattern = ""

    // FOR event
    val event = SingleLiveData<PokemonListViewEvent>()

    // FOR state
    private val _state = MutableLiveData<PokemonListViewState>()
    val state: LiveData<PokemonListViewState> get() = _state

    // PUBLIC ACTIONS ---

    fun forceRefreshItems() = loadPokemonsRemote(true)

    // ---

    /**
     * Send interaction event for open character detail view from selected character.
     *
     * @param name Pokemon identifier.
     */
    fun openPokemonDetail(name: String) {
        event.value = PokemonListViewEvent.OpenPokemonDetailView(name)
    }

    fun loadPokemonsRemote(forceRefresh: Boolean = false, offset : Int = 0, limit : Int = com.hivian.common.Constants.POKEMON_LIST_SIZE) {
        _state.value = PokemonListViewState.Loading
        viewModelScope.launch(dispatchers.main) {
            when (val result =
                pokemonListUseCase.allPokemonApiCall(forceRefresh, offset, limit)) {
                is NetworkWrapper.Success -> {
                    _state.value = when {
                        result.isEmpty -> PokemonListViewState.Empty
                        else -> PokemonListViewState.Loaded
                    }
                }
                is NetworkWrapper.Error -> {
                    _state.value = when {
                        data.value?.isNotEmpty() == true -> PokemonListViewState.ErrorWithData
                        else -> PokemonListViewState.Error(result.error)
                    }
                }
            }
        }
    }

    fun loadPokemonListByPattern(pattern: String) = viewModelScope.launch(dispatchers.main) {
        dataFilter.value?.run {
            dataFilter.value = when (this) {
                is FilterType.All -> FilterType.All(pattern)
                is FilterType.Favorites -> FilterType.Favorites(pattern)
                is FilterType.Caught -> FilterType.Caught(pattern)
            }
            currentSearchPattern = pattern
        }
    }

    fun loadAllPokemons() = viewModelScope.launch(dispatchers.main) {
        dataFilter.value = FilterType.All(currentSearchPattern)
    }

    fun loadPokemonFavorites() = viewModelScope.launch(dispatchers.main) {
        dataFilter.value = FilterType.Favorites(currentSearchPattern)
    }

    fun loadPokemonCaught() = viewModelScope.launch(dispatchers.main) {
        dataFilter.value = FilterType.Caught(currentSearchPattern)
    }

}
