package com.hivian.home.pokemon_detail

import androidx.lifecycle.*
import com.hivian.common.base.BaseViewModel
import com.hivian.home.R
import com.hivian.home.domain.GetPokemonByNameUseCase
import com.hivian.model.domain.Pokemon
import com.hivian.repository.AppDispatchers
import com.hivian.repository.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PokemonDetailViewModel(private val getPokemonByNameUseCase: GetPokemonByNameUseCase,
    private val dispatchers: AppDispatchers) : BaseViewModel() {

    // PRIVATE DATA
    private lateinit var argsPokemonName: String
    private var pokemonSource: LiveData<Resource<Pokemon>> = MutableLiveData()

    private val _pokemon = MediatorLiveData<Pokemon>()
    val pokemon: LiveData<Pokemon> get() = _pokemon

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
            if (it.status == Resource.Status.HTTP_ERROR) snackBarError.value = R.string.pokemon_list_server_error
            if (it.status == Resource.Status.NETWORK_ERROR) snackBarError.value = R.string.pokemon_list_network_error
        }
    }
}
