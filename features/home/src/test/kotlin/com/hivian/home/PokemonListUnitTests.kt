package com.hivian.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.filters.SmallTest
import com.hivian.common_test.datasets.FakeData
import com.hivian.common_test.extensions.blockingObserve
import com.hivian.home.domain.PokemonListUseCase
import com.hivian.home.pokemon_list.PokemonListViewEvent
import com.hivian.home.pokemon_list.PokemonListViewModel
import com.hivian.home.pokemon_list.PokemonListViewState
import com.hivian.model.domain.Pokemon
import com.hivian.repository.AppDispatchers
import com.hivian.repository.utils.ResultWrapper
import io.mockk.coEvery
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.verify
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
class PokemonListUnitTests {
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
        val observer = mockk<Observer<List<Pokemon>>>(relaxed = true)
        val result = ResultWrapper.Success(FakeData.createFakePokemonsDomain(3))
        coEvery { pokemonListUseCase.getPokemonList(false) } returns result

        pokemonListViewModel = PokemonListViewModel(pokemonListUseCase, appDispatchers)
        pokemonListViewModel.data.observeForever(observer)

        verify {
            observer.onChanged(result.value)
        }

        confirmVerified(observer)
    }

    @Test
    fun `Pokemons requested but failed when ViewModel is created`() {
        val observer = mockk<Observer<List<Pokemon>>>(relaxed = true)
        val observerState = mockk<Observer<PokemonListViewState>>(relaxed = true)
        val result = ResultWrapper.NetworkError(emptyList<Pokemon>())
        coEvery { pokemonListUseCase.getPokemonList(any()) } returns result

        pokemonListViewModel = PokemonListViewModel(pokemonListUseCase, appDispatchers)
        pokemonListViewModel.data.observeForever(observer)
        pokemonListViewModel.state.observeForever(observerState)

        verify {
            observer.onChanged(result.value)
            observerState.onChanged(PokemonListViewState.Error)
        }

        confirmVerified(observer)
    }

    @Test
    fun `Pokemon clicks on item on RecyclerView`() {
        val fakeDataSet = FakeData.createFakePokemonsDomain(3)
        val event = PokemonListViewEvent.OpenPokemonDetailView(fakeDataSet.first().name)
        coEvery { pokemonListUseCase.getPokemonList(false) } returns ResultWrapper.Success(fakeDataSet)

        pokemonListViewModel = PokemonListViewModel(pokemonListUseCase, appDispatchers)
        pokemonListViewModel.openPokemonDetail(fakeDataSet.first().name)

        Assert.assertEquals(event.name,
            (pokemonListViewModel.event.blockingObserve()!! as PokemonListViewEvent.OpenPokemonDetailView).name)
    }

    @Test
    fun `Pokemon refreshes list with swipe to refresh`() {
        val observer = mockk<Observer<List<Pokemon>>>(relaxed = true)
        val result = ResultWrapper.Success(FakeData.createFakePokemonsDomain(3))
        coEvery { pokemonListUseCase.getPokemonList(any()) } returns result

        pokemonListViewModel = PokemonListViewModel(pokemonListUseCase, appDispatchers)
        pokemonListViewModel.data.observeForever(observer)
        pokemonListViewModel.forceRefreshItems()

        verify(exactly = 2) {
            observer.onChanged(result.value) // When VM is created
            observer.onChanged(result.value) // When user actually refreshes
        }

        confirmVerified(observer)
    }
}