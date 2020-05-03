package com.hivian.repository

import com.hivian.local.dao.PokedexDao
import com.hivian.model.mapper.MapperPokedexRepository
import com.hivian.remote.PokemonDatasource
import com.hivian.repository.utils.ErrorEntity
import com.hivian.repository.utils.NetworkWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.IOException

@RunWith(JUnit4::class)
class RepositoryTest {

    @MockK
    private lateinit var datasource: PokemonDatasource

    @MockK
    private lateinit var dao: PokedexDao

    @MockK
    private lateinit var mapper: MapperPokedexRepository

    private lateinit var repository: PokedexRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = PokedexRepositoryImpl(datasource, dao, mapper)
    }

    @Test
    fun `getAllPokemon live returns Network Error`() {
        coEvery { dao.getAllPokemon() } returns emptyList()
        coEvery { datasource.fetchTopPokemonsAsync() } throws IOException()

        runBlocking {
            assertEquals(NetworkWrapper.Error(ErrorEntity.Network), repository.allPokemonApiCall())
        }
    }

}