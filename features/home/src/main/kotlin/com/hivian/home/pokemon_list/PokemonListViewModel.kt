package com.hivian.home.pokemon_list

import androidx.lifecycle.*
import com.hivian.common.base.BaseViewModel
import com.hivian.common.livedata.SingleLiveData
import com.hivian.home.R
import com.hivian.home.domain.GetTopPokemonsUseCase
import com.hivian.home.pokemon_list.views.adapter.PaginationListener
import com.hivian.model.domain.Pokemon
import com.hivian.repository.AppDispatchers
import com.hivian.repository.utils.Resource
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
    var isLastPage: Boolean = false

    init {
        getPokemons(false)
    }

    // PUBLIC ACTIONS ---

    fun forceRefreshItems() = getPokemons(true)

    fun loadMoreItem() {
        currentOffset += PaginationListener.PAGE_SIZE
        getPokemons(forceRefresh = true,
            offset = currentOffset,
            limit = PaginationListener.PAGE_SIZE)
    }

    // ---

    /**
     * Send interaction event for open character detail view from selected character.
     *
     * @param characterId Character identifier.
     */
    fun openPokemonDetail(name: String) {
        event.postValue(PokemonListViewEvent.OpenPokemonDetailView(name))
    }

    private fun getPokemons(forceRefresh: Boolean, offset : Int = 0, limit : Int = PaginationListener.PAGE_SIZE) = viewModelScope.launch(dispatchers.main) {
        val isAdditional = offset > 0

        _state.value = if (isAdditional) {
            PokemonListViewState.AddLoading
        } else {
            PokemonListViewState.Loading
        }
        val pokemonsAsync =  getTopPokemonsUseCase(offset, limit)
        when (pokemonsAsync) {
            is Resource.Success -> {
                _data.value = pokemonsAsync.value
                if (isAdditional && pokemonsAsync.value.isEmpty()) {
                    _state.value = PokemonListViewState.NoMoreElements
                } else if (pokemonsAsync.value.isEmpty()) {
                    _state.value = PokemonListViewState.Empty
                } else {
                    _state.value = PokemonListViewState.Loaded
                }
            }
            is Resource.GenericError -> {
                snackBarError.value = R.string.pokemon_list_server_error
                if (isAdditional) {
                    PokemonListViewState.AddError
                } else {
                    PokemonListViewState.Error
                }
            }
            is Resource.NetworkError -> {
                snackBarError.value = R.string.pokemon_list_network_error
                if (isAdditional) {
                    PokemonListViewState.AddError
                } else {
                    PokemonListViewState.Error
                }
            }
        }

    }

}
