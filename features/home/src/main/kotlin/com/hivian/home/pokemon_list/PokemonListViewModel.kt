package com.hivian.home.pokemon_list

import androidx.lifecycle.*
import com.hivian.common.Constants
import com.hivian.common.base.BaseViewModel
import com.hivian.common.livedata.SingleLiveData
import com.hivian.common.utils.percent
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
    object Favorite: FilterType()
    object Caught: FilterType()

    fun isAll() = this is All
    fun isFavorite() = this is Favorite
    fun isCaught() = this is Caught
}

class PokemonListViewModel(private val pokemonListUseCase: PokemonListUseCase,
                           private val dispatchers: AppDispatchers)
    : BaseViewModel() {

    // FOR data
    var dataFilter = MutableLiveData<FilterType>(FilterType.All())
    val data: LiveData<List<Pokemon>> = Transformations.switchMap(dataFilter) {
        when (it) {
            is FilterType.All -> pokemonListUseCase.getAllPokemonFilter(it.pattern)
            is FilterType.Favorite -> pokemonListUseCase.getAllPokemonFavoritesFilter()
            is FilterType.Caught -> pokemonListUseCase.getAllPokemonCaughtFilter()
        }
    }
    val caughtStat: LiveData<Float> = Transformations.map(data) {
        if (dataFilter.value == FilterType.Caught) {
            percent(it.size, Constants.POKEMON_LIST_SIZE)
        } else {
            null
        }
    }
    val caughtRatio: LiveData<Int> = Transformations.map(data) {
        if (dataFilter.value == FilterType.Caught) {
            it.size
        } else {
            null
        }
    }

    // FOR event
    val event = SingleLiveData<PokemonListViewEvent>()

    // FOR state
    private val _regularState = MutableLiveData<PokemonListViewState>()
    private val _actionFilterState = Transformations.map(data) { data ->
        if (filterAction) {
            filterAction = false
            if (data != null && data.isEmpty()) {
                when (dataFilter.value) {
                    is FilterType.Favorite -> PokemonListViewState.EmptyFavorite
                    is FilterType.Caught -> PokemonListViewState.EmptyCaught
                    is FilterType.All -> PokemonListViewState.Empty
                    else -> null
                }
            } else {
                PokemonListViewState.Loaded
            }
        } else {
            null
        }
    }
    private val _state = MediatorLiveData<PokemonListViewState>()
    val state: LiveData<PokemonListViewState> get() = _state

    // FOR user action
    private var filterAction: Boolean = false

    init {
        _state.addSource(_regularState) {
            _state.value = it
        }
        _state.addSource(_actionFilterState) {
            it?.run { _state.value = this }
        }
    }

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
                is FilterType.Favorite -> FilterType.Favorite
                is FilterType.Caught -> FilterType.Caught
            }
        }
    }

    fun loadAllPokemons() = viewModelScope.launch(dispatchers.main) {
        filterAction = true
        dataFilter.value = FilterType.All("")
    }

    fun loadPokemonFavorites() = viewModelScope.launch(dispatchers.main) {
        filterAction = true
        dataFilter.value = FilterType.Favorite
    }

    fun loadPokemonCaught() = viewModelScope.launch(dispatchers.main) {
        filterAction = true
        dataFilter.value = FilterType.Caught
    }

    /**
     * Toggle [Pokemon.favorite].
     */
    fun toggleFavorite(pokemon: Pokemon) {
        viewModelScope.launch(dispatchers.main) {
            val newFavorite = !pokemon.favorite
            pokemonListUseCase.updateFavoriteStatus(pokemon.pokemonId, newFavorite)
        }
    }

    /**
     * Toggle [Pokemon.caught].
     */
    fun toggleCaught(pokemon: Pokemon) {
        viewModelScope.launch(dispatchers.main) {
            val newCaught = !pokemon.caught
            pokemonListUseCase.updateCaughtStatus(pokemon.pokemonId, newCaught)
        }
    }
}
