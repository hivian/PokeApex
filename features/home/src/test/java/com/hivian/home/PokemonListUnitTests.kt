package com.hivian.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.test.filters.SmallTest
import com.hivian.home.domain.GetTopPokemonsUseCase
import com.hivian.home.pokemon_list.PokemonListViewModel
import com.hivian.model.domain.Pokemon
import com.hivian.repository.AppDispatchers
import com.hivian.repository.utils.Resource
import com.hivian.common_test.datasets.UserDataset.FAKE_POKEMONS
import com.hivian.common_test.extensions.blockingObserve
import com.hivian.home.pokemon_list.PokemonListViewEvent
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
class PokemonListUnitTests {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var getTopPokemonsUseCase: GetTopPokemonsUseCase
    private lateinit var pokemonListViewModel: PokemonListViewModel
    private val appDispatchers = AppDispatchers(Dispatchers.Unconfined, Dispatchers.Unconfined)

    @Before
    fun setUp() {
        getTopPokemonsUseCase = mockk()
    }

    @Test
    fun `Pokemons requested when ViewModel is created`() {
        val observer = mockk<Observer<Resource<List<Pokemon>>>>(relaxed = true)
        val result = Resource.success(FAKE_POKEMONS)
        coEvery { getTopPokemonsUseCase(false) } returns MutableLiveData<Resource<List<Pokemon>>>().apply { value = result }

        pokemonListViewModel = PokemonListViewModel(getTopPokemonsUseCase, appDispatchers)
        pokemonListViewModel.pokemons.observeForever(observer)

        verify {
            observer.onChanged(result)
        }

        confirmVerified(observer)
    }

    @Test
    fun `Pokemons requested but failed when ViewModel is created`() {
        val observer = mockk<Observer<Resource<List<Pokemon>>>>(relaxed = true)
        val observerSnackbar = mockk<Observer<Int>>(relaxed = true)
        val result = Resource.networkError(Exception("fail"), null)
        coEvery { getTopPokemonsUseCase(any()) } returns  MutableLiveData<Resource<List<Pokemon>>>().apply { value = result }

        pokemonListViewModel = PokemonListViewModel(getTopPokemonsUseCase, appDispatchers)
        pokemonListViewModel.pokemons.observeForever(observer)
        pokemonListViewModel.snackBarError.observeForever(observerSnackbar)

        verify {
            observer.onChanged(result)
            observerSnackbar.onChanged(pokemonListViewModel.snackBarError.value)
        }

        confirmVerified(observer)
    }

    @Test
    fun `Pokemon clicks on item on RecyclerView`() {
        val event = PokemonListViewEvent.OpenPokemonDetailView(FAKE_POKEMONS.first().name)
        coEvery { getTopPokemonsUseCase(false) } returns MutableLiveData<Resource<List<Pokemon>>>().apply {
            value = Resource.success(FAKE_POKEMONS)
        }

        pokemonListViewModel = PokemonListViewModel(getTopPokemonsUseCase, appDispatchers)
        pokemonListViewModel.openPokemonDetail(FAKE_POKEMONS.first().name)

        Assert.assertEquals(event.name,
            (pokemonListViewModel.event.blockingObserve()!! as PokemonListViewEvent.OpenPokemonDetailView).name)
    }

    @Test
    fun `Pokemon refreshes list with swipe to refresh`() {
        val observer = mockk<Observer<Resource<List<Pokemon>>>>(relaxed = true)
        val result = Resource.success(FAKE_POKEMONS)
        coEvery { getTopPokemonsUseCase(any()) } returns MutableLiveData<Resource<List<Pokemon>>>().apply { value = result }

        pokemonListViewModel = PokemonListViewModel(getTopPokemonsUseCase, appDispatchers)
        pokemonListViewModel.pokemons.observeForever(observer)
        pokemonListViewModel.forceRefreshItems()

        verify(exactly = 2) {
            observer.onChanged(result) // When VM is created
            observer.onChanged(result) // When user actually refreshes
        }

        confirmVerified(observer)
    }
}