package com.hivian.home.detail_test

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.test.filters.SmallTest
import com.hivian.common.extension.toLiveData
import com.hivian.common_test.datasets.FakeData
import com.hivian.common_test.extensions.blockingObserve
import com.hivian.home.domain.PokemonDetailUseCase
import com.hivian.home.domain.PokemonListUseCase
import com.hivian.home.pokemon_detail.*
import com.hivian.model.domain.Pokemon
import com.hivian.repository.AppDispatchers
import com.hivian.repository.utils.ErrorEntity
import com.hivian.repository.utils.NetworkWrapper
import com.nhaarman.mockitokotlin2.any
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
@SmallTest
class PokemonDetailViewModelTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var pokemonDetailUseCase: PokemonDetailUseCase

    private lateinit var pokemonDetailViewModel: PokemonDetailViewModel
    private val appDispatchers = AppDispatchers(Dispatchers.Unconfined, Dispatchers.Unconfined)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `Pokemon requested when ViewModel is created`() {
        val fakeData = FakeData.createFakePokemonDomain().apply {
            favorite = true
            caught = false
        }
        val dataObserver = mockk<Observer<Pokemon>>(relaxed = true)
        val networkStateObserver = mockk<Observer<PokemonDetailNetworkViewState>>(relaxed = true)
        val favoriteStateObserver = mockk<Observer<PokemonDetailFavoriteViewState>>(relaxed = true)
        val caughtStateObserver = mockk<Observer<PokemonDetailCaughtViewState>>(relaxed = true)
        val result = NetworkWrapper.Success(false)
        coEvery { pokemonDetailUseCase.pokemonDetailApiCall(fakeData.name) } returns result
        coEvery { pokemonDetailUseCase.getPokemonDetailLive(fakeData.name) } returns fakeData.toLiveData()

        pokemonDetailViewModel = PokemonDetailViewModel(fakeData.name, pokemonDetailUseCase, appDispatchers)
        pokemonDetailViewModel.data.observeForever(dataObserver)
        pokemonDetailViewModel.networkState.observeForever(networkStateObserver)
        pokemonDetailViewModel.favoriteState.observeForever(favoriteStateObserver)
        pokemonDetailViewModel.caughtState.observeForever(caughtStateObserver)
        pokemonDetailViewModel.loadPokemonDetail()

        verify {
            dataObserver.onChanged(fakeData)
            networkStateObserver.onChanged(PokemonDetailNetworkViewState.Loading)
            networkStateObserver.onChanged(PokemonDetailNetworkViewState.Loaded)
            favoriteStateObserver.onChanged(PokemonDetailFavoriteViewState.AddedToFavorite)
            caughtStateObserver.onChanged(PokemonDetailCaughtViewState.RemovedFromCaught)
        }
    }

    @Test
    fun `Pokemon requested but failed when ViewModel is created`() {
        val networkObserverState = mockk<Observer<PokemonDetailNetworkViewState>>(relaxed = true)
        val result = NetworkWrapper.Error(ErrorEntity.Unknown)
        coEvery { pokemonDetailUseCase.pokemonDetailApiCall("") } returns result
        coEvery { pokemonDetailUseCase.getPokemonDetailLive("") } returns MutableLiveData()

        pokemonDetailViewModel = PokemonDetailViewModel("", pokemonDetailUseCase, appDispatchers)
        pokemonDetailViewModel.networkState.observeForever(networkObserverState)
        pokemonDetailViewModel.loadPokemonDetail()

        verify {
            networkObserverState.onChanged(PokemonDetailNetworkViewState.Loading)
            networkObserverState.onChanged(PokemonDetailNetworkViewState.Error(ErrorEntity.Unknown))
        }
        confirmVerified(networkObserverState)
    }

    @Test
    fun `Dismiss event requested`() {
        val eventObserver = mockk<Observer<PokemonDetailViewEvent>>(relaxed = true)
        coEvery { pokemonDetailUseCase.getPokemonDetailLive("") } returns MutableLiveData()

        pokemonDetailViewModel = PokemonDetailViewModel("", pokemonDetailUseCase, appDispatchers)
        pokemonDetailViewModel.event.observeForever(eventObserver)
        pokemonDetailViewModel.dismissPokemonDetail()

        verify {
            eventObserver.onChanged(PokemonDetailViewEvent.DismissPokemonDetailView)
        }
        confirmVerified(eventObserver)
    }

    @Test
    fun `Toggle favorite event`() {
        val fakeData = FakeData.createFakePokemonDomain().apply {
            favorite = false
        }
        val eventObserver = mockk<Observer<PokemonDetailViewEvent>>(relaxed = true)
        coEvery {
            pokemonDetailUseCase.updateFavoriteStatus(fakeData.pokemonId, true)
            pokemonDetailUseCase.updateFavoriteStatus(fakeData.pokemonId, false)
        } returns Unit
        coEvery { pokemonDetailUseCase.getPokemonDetailLive(fakeData.name) } returns fakeData.toLiveData()

        pokemonDetailViewModel = PokemonDetailViewModel(fakeData.name, pokemonDetailUseCase, appDispatchers)
        pokemonDetailViewModel.event.observeForever(eventObserver)
        pokemonDetailViewModel.toggleFavoriteStatus()
        fakeData.favorite = true
        pokemonDetailViewModel.toggleFavoriteStatus()

        verify {
            eventObserver.onChanged(PokemonDetailViewEvent.AddedToFavorites)
            eventObserver.onChanged(PokemonDetailViewEvent.RemovedFromFavorites)
        }
        confirmVerified(eventObserver)
    }

    @Test
    fun `Toggle caught event`() {
        val fakeData = FakeData.createFakePokemonDomain().apply {
            caught = false
        }
        val eventObserver = mockk<Observer<PokemonDetailViewEvent>>(relaxed = true)
        coEvery {
            pokemonDetailUseCase.updateCaughtStatus(fakeData.pokemonId, true)
            pokemonDetailUseCase.updateCaughtStatus(fakeData.pokemonId, false)
        } returns Unit
        coEvery { pokemonDetailUseCase.getPokemonDetailLive(fakeData.name) } returns fakeData.toLiveData()

        pokemonDetailViewModel = PokemonDetailViewModel(fakeData.name, pokemonDetailUseCase, appDispatchers)
        pokemonDetailViewModel.event.observeForever(eventObserver)
        pokemonDetailViewModel.toggleCaughtStatus()
        fakeData.caught = true
        pokemonDetailViewModel.toggleCaughtStatus()

        verify {
            eventObserver.onChanged(PokemonDetailViewEvent.AddedToCaught)
            eventObserver.onChanged(PokemonDetailViewEvent.RemovedFromCaught)
        }
        confirmVerified(eventObserver)
    }
}