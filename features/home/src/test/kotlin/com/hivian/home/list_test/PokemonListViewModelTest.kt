package com.hivian.home.list_test

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.filters.SmallTest
import com.hivian.common.extension.toLiveData
import com.hivian.common_test.datasets.FakeData
import com.hivian.common_test.extensions.blockingObserve
import com.hivian.home.domain.PokemonListUseCase
import com.hivian.home.pokemon_list.FilterType
import com.hivian.home.pokemon_list.PokemonListViewEvent
import com.hivian.home.pokemon_list.PokemonListViewModel
import com.hivian.home.pokemon_list.PokemonListViewState
import com.hivian.model.domain.Pokemon
import com.hivian.repository.AppDispatchers
import com.hivian.repository.utils.ErrorEntity
import com.hivian.repository.utils.NetworkWrapper
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
@SmallTest
class PokemonListViewModelTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var pokemonListUseCase: PokemonListUseCase
    private lateinit var pokemonListViewModel: PokemonListViewModel
    private val appDispatchers = AppDispatchers(Dispatchers.Unconfined, Dispatchers.Unconfined)

    @Before
    fun setUp() {
        pokemonListUseCase = mockk()
    }

    @Test
    fun `Pokemons requested when ViewModel is created`() {
        val fakeDataSet = FakeData.createFakePokemonsDomain(3)
        val dataObserver = mockk<Observer<List<Pokemon>>>(relaxed = true)
        val stateObserver = mockk<Observer<PokemonListViewState>>(relaxed = true)
        val filterObserver = mockk<Observer<FilterType>>(relaxed = true)
        val result = NetworkWrapper.Success(false)
        coEvery { pokemonListUseCase.allPokemonApiCall(false) } returns result
        coEvery { pokemonListUseCase.getAllPokemonFilter("") } returns fakeDataSet.toLiveData()

        pokemonListViewModel = PokemonListViewModel(pokemonListUseCase, appDispatchers)
        pokemonListViewModel.dataFilter.observeForever(filterObserver)
        pokemonListViewModel.state.observeForever(stateObserver)
        pokemonListViewModel.data.observeForever(dataObserver)
        pokemonListViewModel.loadPokemonsRemote()

        verify {
            filterObserver.onChanged(FilterType.All())
            dataObserver.onChanged(fakeDataSet)
            stateObserver.onChanged(PokemonListViewState.Loading)
            stateObserver.onChanged(PokemonListViewState.Loaded)
        }
    }

    @Test
    fun `Pokemons requested but failed when ViewModel is created`() {
        val filterObserver = mockk<Observer<FilterType>>(relaxed = true)
        val observerState = mockk<Observer<PokemonListViewState>>(relaxed = true)
        val result = NetworkWrapper.Error(ErrorEntity.Unknown)
        coEvery { pokemonListUseCase.allPokemonApiCall(any()) } returns result

        pokemonListViewModel = PokemonListViewModel(pokemonListUseCase, appDispatchers)
        pokemonListViewModel.dataFilter.observeForever(filterObserver)
        pokemonListViewModel.state.observeForever(observerState)
        pokemonListViewModel.loadPokemonsRemote()

        verify {
            filterObserver.onChanged(FilterType.All(""))
            observerState.onChanged(PokemonListViewState.Loading)
            observerState.onChanged(PokemonListViewState.Error(ErrorEntity.Unknown))
        }
        confirmVerified(filterObserver, observerState)
    }

    @Test
    fun `Pokemon clicks on item on RecyclerView`() {
        val fakeDataSet = FakeData.createFakePokemonsDomain(3)
        val event = PokemonListViewEvent.OpenPokemonDetailView(fakeDataSet.first().name)
        coEvery { pokemonListUseCase.allPokemonApiCall(false) } returns NetworkWrapper.Success(false)

        pokemonListViewModel = PokemonListViewModel(pokemonListUseCase, appDispatchers)
        pokemonListViewModel.openPokemonDetail(fakeDataSet.first().name)

        Assert.assertEquals(event.name,
            (pokemonListViewModel.event.blockingObserve()!! as PokemonListViewEvent.OpenPokemonDetailView).name)
    }
}