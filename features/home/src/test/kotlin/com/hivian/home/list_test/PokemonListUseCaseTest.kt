package com.hivian.home.list_test

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.hivian.common.extension.toLiveData
import com.hivian.common_test.datasets.FakeData
import com.hivian.home.domain.PokemonListUseCase
import com.hivian.model.domain.Pokemon
import com.hivian.repository.PokedexRepository
import com.hivian.repository.utils.ErrorEntity
import com.hivian.repository.utils.NetworkWrapper
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class PokemonListUseCaseTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var repository: PokedexRepository

    lateinit var pokemonListUseCase: PokemonListUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        pokemonListUseCase = PokemonListUseCase(repository)
    }

    @Test
    fun `allPokemonApiCall returns Network error`() {
        val networkError = NetworkWrapper.Error(ErrorEntity.Network)
        coEvery { repository.allPokemonApiCall() } returns networkError

        runBlocking {
            assertEquals(networkError, pokemonListUseCase.allPokemonApiCall())
        }
    }

    @Test
    fun `allPokemonApiCall returns NotFound error`() {
        val notFoundError = NetworkWrapper.Error(ErrorEntity.NotFound)
        coEvery { repository.allPokemonApiCall() } returns notFoundError

        runBlocking {
            assertEquals(notFoundError, pokemonListUseCase.allPokemonApiCall())
        }
    }

    @Test
    fun `allPokemonApiCall returns AccessDenied error`() {
        val accessDeniedError = NetworkWrapper.Error(ErrorEntity.AccessDenied)
        coEvery { repository.allPokemonApiCall() } returns accessDeniedError

        runBlocking {
            assertEquals(accessDeniedError, pokemonListUseCase.allPokemonApiCall())
        }
    }

    @Test
    fun `allPokemonApiCall returns ServiceUnavailable error`() {
        val serviceUnavailableError = NetworkWrapper.Error(ErrorEntity.ServiceUnavailable)
        coEvery { repository.allPokemonApiCall() } returns serviceUnavailableError

        runBlocking {
            assertEquals(serviceUnavailableError, pokemonListUseCase.allPokemonApiCall())
        }
    }

    @Test
    fun `allPokemonApiCall returns Unknown error`() {
        val unknownError = NetworkWrapper.Error(ErrorEntity.Unknown)
        coEvery { repository.allPokemonApiCall() } returns unknownError

        runBlocking {
            assertEquals(unknownError, pokemonListUseCase.allPokemonApiCall())
        }
    }

    @Test
    fun `getAllPokemonFilter returns all data`() {
        val fakeDataSet = FakeData.createFakePokemonsDomain(3)
        val observer = mockk<Observer<List<Pokemon>>>(relaxed = true)
        every { repository.getAllPokemonByPatternLocal("") } returns fakeDataSet.toLiveData()
        pokemonListUseCase.getAllPokemonFilter("").observeForever(observer)

        verify {
            observer.onChanged(fakeDataSet)
        }
        confirmVerified(observer)
    }

    @Test
    fun `getAllPokemonFavoritesFilter returns all data`() {
        val fakeDataSet = FakeData.createFakePokemonsDomain(3)
        val observer = mockk<Observer<List<Pokemon>>>(relaxed = true)
        every { repository.getAllPokemonFavoritesByPatternLocal() } returns fakeDataSet.toLiveData()
        pokemonListUseCase.getAllPokemonFavoritesFilter().observeForever(observer)

        verify {
            observer.onChanged(fakeDataSet)
        }
        confirmVerified(observer)
    }

    @Test
    fun `getAllPokemonCaughtFilter returns all data`() {
        val fakeDataSet = FakeData.createFakePokemonsDomain(3)
        val observer = mockk<Observer<List<Pokemon>>>(relaxed = true)
        every { repository.getAllPokemonCaughtByPatternLocal() } returns fakeDataSet.toLiveData()
        pokemonListUseCase.getAllPokemonCaughtFilter().observeForever(observer)

        verify {
            observer.onChanged(fakeDataSet)
        }
        confirmVerified(observer)
    }

}