package com.hivian.home.pokemon_detail

import androidx.lifecycle.*
import com.hivian.common.base.BaseViewModel
import com.hivian.common.base.BaseViewState
import com.hivian.common.livedata.SingleLiveData
import com.hivian.home.domain.GetPokemonByNameUseCase
import com.hivian.home.pokemon_list.PokemonListViewEvent
import com.hivian.model.domain.Pokemon
import com.hivian.repository.AppDispatchers
import com.hivian.repository.utils.ResultWrapper
import kotlinx.coroutines.launch

class PokemonDetailViewModel(private val getPokemonByNameUseCase: GetPokemonByNameUseCase,
    private val dispatchers: AppDispatchers) : BaseViewModel() {

    // PRIVATE data
    private lateinit var argsPokemonName: String
    private var pokemonSource: LiveData<ResultWrapper<Pokemon>> = MutableLiveData()

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

    }
}
