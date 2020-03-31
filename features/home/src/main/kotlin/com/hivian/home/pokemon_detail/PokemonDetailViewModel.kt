package com.hivian.home.pokemon_detail

import androidx.lifecycle.*
import com.hivian.common.base.BaseViewModel
import com.hivian.common.base.BaseViewState
import com.hivian.common.livedata.SingleLiveData
import com.hivian.home.R
import com.hivian.home.domain.GetPokemonByNameUseCase
import com.hivian.home.pokemon_list.PokemonListViewEvent
import com.hivian.model.domain.Pokemon
import com.hivian.repository.AppDispatchers
import com.hivian.repository.utils.Resource
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PokemonDetailViewModel(private val getPokemonByNameUseCase: GetPokemonByNameUseCase,
    private val dispatchers: AppDispatchers) : BaseViewModel() {

    // PRIVATE data
    private lateinit var argsPokemonName: String
    private var pokemonSource: LiveData<Resource<Pokemon>> = MutableLiveData()

    private val _pokemon = MediatorLiveData<Pokemon>()
    val pokemon: LiveData<Pokemon> get() = _pokemon

    // FOR event
    val event = SingleLiveData<PokemonListViewEvent>()

    // FOR state
    private val _state = MutableLiveData<BaseViewState>()
    val state: LiveData<BaseViewState> get() = _state

    // PUBLIC ACTIONS ---
    fun loadDataWhenActivityStarts(name: String) {
        argsPokemonName = name
        getPokemonDetail(false)
    }

    fun forceRefreshItems() = getPokemonDetail(true)

    private fun getPokemonDetail(forceRefresh: Boolean) = viewModelScope.launch(dispatchers.main) {
        _pokemon.removeSource(pokemonSource)
        withContext(dispatchers.io) { pokemonSource = getPokemonByNameUseCase(forceRefresh = forceRefresh, name = argsPokemonName) }
        _pokemon.addSource(pokemonSource) {
            _pokemon.value = it.data
            when (it.status) {
                Resource.Status.SUCCESS -> _state.value = PokemonDetailViewState.Loaded
                Resource.Status.LOADING -> _state.value = PokemonDetailViewState.Loading
                Resource.Status.HTTP_ERROR -> {
                    snackBarError.value = R.string.pokemon_list_server_error
                    _state.value = PokemonDetailViewState.Error
                }
                Resource.Status.NETWORK_ERROR -> {
                    snackBarError.value = R.string.pokemon_list_network_error
                    _state.value = PokemonDetailViewState.Error
                }
            }
        }
    }
}
