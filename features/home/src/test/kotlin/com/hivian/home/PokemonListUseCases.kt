package com.hivian.home

import com.hivian.home.domain.PokemonListUseCase
import com.hivian.repository.PokedexRepository
import com.hivian.repository.utils.ErrorEntity
import com.hivian.repository.utils.NetworkWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class PokemonListUseCases {

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

}