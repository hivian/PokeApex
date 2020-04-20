package com.hivian.home.pokemon_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.hivian.common.base.BaseViewModel
import com.hivian.common.livedata.SingleLiveData
import com.hivian.home.Constants
import com.hivian.home.domain.PokemonListUseCase
import com.hivian.model.domain.Pokemon
import com.hivian.repository.AppDispatchers
import com.hivian.repository.utils.ResultWrapper
import kotlinx.coroutines.launch

/**
 * A simple [BaseViewModel] that provide the data and handle logic to communicate with the model
 * for [PokemonListFragment].
 */
enum class FilterType {
    ALL,
    FAVORITES,
    CAUGHT
}

class PokemonListViewModel(private val pokemonListUseCase: PokemonListUseCase,
                           private val dispatchers: AppDispatchers)
    : BaseViewModel() {

    // FOR data
    var dataFilter = MutableLiveData(FilterType.ALL)
    val data : LiveData<List<Pokemon>> = Transformations.switchMap(dataFilter) { filterType ->
        filterType?.run {
            when (this) {
                FilterType.ALL -> pokemonListUseCase.getAllPokemonFilter()
                FilterType.FAVORITES -> pokemonListUseCase.getPokemonFavoritesFilter()
                FilterType.CAUGHT -> pokemonListUseCase.getPokemonCaughtFilter()
            }
        }
    }


    // FOR event
    val event = SingleLiveData<PokemonListViewEvent>()

    // FOR state
    private val _state = MutableLiveData<PokemonListViewState>()
    val state: LiveData<PokemonListViewState> get() = _state

    // FOR paging
    private var currentOffset = 0

    init {
        loadPokemonsRemote(false)
    }

    // PUBLIC ACTIONS ---

    fun forceRefreshItems() = loadPokemonsRemote(true)

    fun loadMoreItem() {
        loadPokemonsRemote(forceRefresh = true,
            offset = currentOffset + Constants.PAGE_SIZE,
            limit = Constants.PAGE_SIZE)
    }

    // ---

    /**
     * Send interaction event for open character detail view from selected character.
     *
     * @param name Pokemon identifier.
     */
    fun openPokemonDetail(name: String) {
        event.value = PokemonListViewEvent.OpenPokemonDetailView(name)
    }

    private fun loadPokemonsRemote(forceRefresh: Boolean, offset : Int = 0, limit : Int = com.hivian.common.Constants.POKEMON_LIST_SIZE) {
        val isAdditional = offset > 0

        _state.value = if (isAdditional) {
            PokemonListViewState.AddLoading
        } else {
            PokemonListViewState.Loading
        }
        viewModelScope.launch(dispatchers.main) {
            when (val pokemonsAsync =
                pokemonListUseCase.getAllPokemonRemote(forceRefresh, offset, limit)) {
                is ResultWrapper.Success -> {
                    currentOffset = pokemonsAsync.value.size + Constants.PAGE_SIZE
                    //_data.value = pokemonsAsync.value
                    _state.value = when {
                        isAdditional && pokemonsAsync.emptyResponse -> PokemonListViewState.NoMoreElements
                        pokemonsAsync.value.isEmpty() -> PokemonListViewState.Empty
                        else -> PokemonListViewState.Loaded
                    }
                }
                is ResultWrapper.GenericError -> {
//                    _data.value = pokemonsAsync.value
                    _state.value = when {
                        isAdditional -> PokemonListViewState.AddError
                        pokemonsAsync.value.isNotEmpty() -> PokemonListViewState.ErrorWithData
                        else -> PokemonListViewState.Error
                    }
                }
                is ResultWrapper.NetworkError -> {
//                    _data.value = pokemonsAsync.value
                    _state.value = when {
                        isAdditional -> PokemonListViewState.AddError
                        pokemonsAsync.value.isNotEmpty() -> PokemonListViewState.ErrorWithData
                        else -> PokemonListViewState.Error
                    }
                }
            }
        }
    }

    fun loadPokemonListByPattern(pattern: String) = viewModelScope.launch(dispatchers.main) {
        pokemonListUseCase.getPokemonListByPatternFilter(pattern)
    }

    fun loadAllPokemons() = viewModelScope.launch(dispatchers.main) {
        dataFilter.value = FilterType.ALL
    }

    fun loadPokemonFavorites() = viewModelScope.launch(dispatchers.main) {
        dataFilter.value = FilterType.FAVORITES
    }

    fun loadPokemonCaught() = viewModelScope.launch(dispatchers.main) {
        dataFilter.value = FilterType.CAUGHT
    }

    private fun setNewPagingOffset(offset: Int) {
        currentOffset = offset
    }

}
