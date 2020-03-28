package com.hivian.home.pokemon_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hivian.common.base.BaseViewModel
import com.hivian.common.livedata.SingleLiveData
import com.hivian.home.R
import com.hivian.home.domain.GetTopPokemonsUseCase
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
    private val _pokemons = MediatorLiveData<Resource<List<Pokemon>>>()
    val pokemons: LiveData<Resource<List<Pokemon>>> get() = _pokemons

    private var pokemonsSource: LiveData<Resource<List<Pokemon>>> = MutableLiveData()

    // FOR event
    val event = SingleLiveData<PokemonListViewEvent>()

    // FOR state
    private val _state = MutableLiveData<PokemonListViewState>()
    val state: LiveData<PokemonListViewState> get() = _state

    init {
        getPokemons(false)
    }

    // PUBLIC ACTIONS ---

    fun forceRefreshItems() = getPokemons(true)

    // ---

    /**
     * Send interaction event for open character detail view from selected character.
     *
     * @param characterId Character identifier.
     */
    fun openPokemonDetail(name: String) {
        event.postValue(PokemonListViewEvent.OpenPokemonDetailView(name))
    }

    private fun getPokemons(forceRefresh: Boolean) = viewModelScope.launch(dispatchers.main) {
        _pokemons.removeSource(pokemonsSource)
        withContext(dispatchers.io) { pokemonsSource = getTopPokemonsUseCase(forceRefresh = forceRefresh) }
        _pokemons.addSource(pokemonsSource) {
            _pokemons.value = it
             when (it.status) {
                Resource.Status.SUCCESS -> _state.value = PokemonListViewState.Loaded
                Resource.Status.LOADING -> _state.value = PokemonListViewState.Loading
                Resource.Status.HTTP_ERROR -> {
                    snackBarError.value = R.string.pokemon_list_server_error
                    _state.value = PokemonListViewState.Error
                }
                 Resource.Status.NETWORK_ERROR -> {
                     snackBarError.value = R.string.pokemon_list_network_error
                     _state.value = PokemonListViewState.Error
                 }
            }
        }

    }
}
