package com.hivian.home.detail_test

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.hivian.common.extension.toLiveData
import com.hivian.common_test.datasets.FakeData
import com.hivian.home.domain.PokemonDetailUseCase
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
class PokemonDetailUseCaseTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @MockK(relaxed = true)
    lateinit var repository: PokedexRepository

    lateinit var pokemonDetailUseCase: PokemonDetailUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        pokemonDetailUseCase = PokemonDetailUseCase(repository)
    }

    @Test
    fun `pokemonDetailApiCall returns Network error`() {
        val networkError = NetworkWrapper.Error(ErrorEntity.Network)
        coEvery { repository.pokemonDetailApiCall("") } returns networkError

        runBlocking {
            assertEquals(networkError, pokemonDetailUseCase.pokemonDetailApiCall(""))
        }
    }

    @Test
    fun `pokemonDetailApiCall returns NotFound error`() {
        val notFoundError = NetworkWrapper.Error(ErrorEntity.NotFound)
        coEvery { repository.pokemonDetailApiCall("") } returns notFoundError

        runBlocking {
            assertEquals(notFoundError, pokemonDetailUseCase.pokemonDetailApiCall(""))
        }
    }

    @Test
    fun `pokemonDetailApiCall returns AccessDenied error`() {
        val accessDeniedError = NetworkWrapper.Error(ErrorEntity.AccessDenied)
        coEvery { repository.pokemonDetailApiCall("") } returns accessDeniedError

        runBlocking {
            assertEquals(accessDeniedError, pokemonDetailUseCase.pokemonDetailApiCall(""))
        }
    }

    @Test
    fun `pokemonDetailApiCall returns ServiceUnavailable error`() {
        val serviceUnavailableError = NetworkWrapper.Error(ErrorEntity.ServiceUnavailable)
        coEvery { repository.pokemonDetailApiCall("") } returns serviceUnavailableError

        runBlocking {
            assertEquals(serviceUnavailableError, pokemonDetailUseCase.pokemonDetailApiCall(""))
        }
    }

    @Test
    fun `pokemonDetailApiCall returns Unknown error`() {
        val unknownError = NetworkWrapper.Error(ErrorEntity.Unknown)
        coEvery { repository.pokemonDetailApiCall("") } returns unknownError

        runBlocking {
            assertEquals(unknownError, pokemonDetailUseCase.pokemonDetailApiCall(""))
        }
    }

    @Test
    fun `getAllPokemonFilter returns all data`() {
        val fakeData = FakeData.createFakePokemonDomain()
        val observer = mockk<Observer<Pokemon>>(relaxed = true)
        coEvery { repository.getPokemonDetailLocalLive("") } returns fakeData.toLiveData()
        pokemonDetailUseCase.getPokemonDetailLive("").observeForever(observer)

        verify {
            observer.onChanged(fakeData)
        }
        confirmVerified(observer)
    }

    @Test
    fun `updateFavoriteStatus calls repository updateFavoriteStatus`() {
        val id = 1
        val favorite = true
        runBlocking {
            pokemonDetailUseCase.updateFavoriteStatus(id, favorite)
        }

        coVerify {
            repository.updateFavoriteStatusLocal(id, favorite)
        }
    }

    @Test
    fun `updateCaughtStatus calls repository updateCaughtStatus`() {
        val id = 1
        val caught = true
        runBlocking {
            pokemonDetailUseCase.updateCaughtStatus(id, caught)
        }

        coVerify {
            repository.updateCaughtStatusLocal(id, caught)
        }
    }

}