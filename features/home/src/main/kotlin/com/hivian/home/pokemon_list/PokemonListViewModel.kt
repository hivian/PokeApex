package com.hivian.home.pokemon_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.ajalt.timberkt.d
import com.hivian.common.base.BaseViewModel
import com.hivian.common.livedata.SingleLiveData
import com.hivian.home.Constants
import com.hivian.home.domain.GetTopPokemonsUseCase
import com.hivian.model.domain.Pokemon
import com.hivian.repository.AppDispatchers
import com.hivian.repository.utils.ResultWrapper
import kotlinx.coroutines.launch

/**
 * A simple [BaseViewModel] that provide the data and handle logic to communicate with the model
 * for [PokemonListFragment].
 */
class PokemonListViewModel(private val getTopPokemonsUseCase: GetTopPokemonsUseCase,
                           private val dispatchers: AppDispatchers)
    : BaseViewModel() {

    // FOR data
    private val _data = MutableLiveData<List<Pokemon>>()
    val data : LiveData<List<Pokemon>> get() = _data

    // FOR event
    val event = SingleLiveData<PokemonListViewEvent>()

    // FOR state
    private val _state = MutableLiveData<PokemonListViewState>()
    val state: LiveData<PokemonListViewState> get() = _state

    // FOR paging
    private var currentOffset = 0

    init {
        getPokemons(false)
    }

    // PUBLIC ACTIONS ---

    fun forceRefreshItems() = getPokemons(true)

    fun loadMoreItem() {
        getPokemons(forceRefresh = true,
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

    private fun getPokemons(forceRefresh: Boolean, offset : Int = 0, limit : Int = com.hivian.common.Constants.POKEMON_LIST_SIZE) = viewModelScope.launch(dispatchers.main) {
        d { "= offset: $offset, limit: $limit, currentOffset: $currentOffset" }
        val isAdditional = offset > 0

        _state.value = if (isAdditional) {
            PokemonListViewState.AddLoading
        } else {
            PokemonListViewState.Loading
        }
        when (val pokemonsAsync =  getTopPokemonsUseCase(forceRefresh, offset, limit)) {
            is ResultWrapper.Success -> {
                currentOffset = pokemonsAsync.value.size + Constants.PAGE_SIZE
                _data.value = pokemonsAsync.value
                _state.value = when {
                    isAdditional && pokemonsAsync.emptyResponse -> PokemonListViewState.NoMoreElements
                    pokemonsAsync.value.isEmpty() -> PokemonListViewState.Empty
                    else -> PokemonListViewState.Loaded
                }
            }
            is ResultWrapper.GenericError -> {
                _data.value = pokemonsAsync.value
                _state.value = when {
                    isAdditional -> PokemonListViewState.AddError
                    pokemonsAsync.value.isNotEmpty() -> PokemonListViewState.ErrorWithData
                    else -> PokemonListViewState.Error
                }
            }
            is ResultWrapper.NetworkError -> {
                _data.value = pokemonsAsync.value
                _state.value = when {
                    isAdditional -> PokemonListViewState.AddError
                    pokemonsAsync.value.isNotEmpty() -> PokemonListViewState.ErrorWithData
                    else -> PokemonListViewState.Error
                }
            }
        }

    }

    private fun setNewPagingOffset(offset: Int) {
        currentOffset = offset
    }

}
