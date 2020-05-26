package com.hivian.repository

import com.hivian.local.dao.PokedexDao
import com.hivian.model.mapper.MapperPokedexRepository
import com.hivian.remote.PokemonDatasource
import com.hivian.repository.utils.ErrorEntity
import com.hivian.repository.utils.NetworkWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockkClass
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.HttpURLConnection

@RunWith(JUnit4::class)
class ApiExceptionTest {

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
    fun `FetchAllPokemon IOException returns Network Error`() {
        coEvery { dao.getAllPokemon() } returns emptyList()
        coEvery { datasource.fetchAllPokemonAsync() } throws IOException()

        runBlocking {
            assertEquals(NetworkWrapper.Error(ErrorEntity.Network), repository.allPokemonApiCall())
        }
    }

    @Test
    fun `FetchAllPokemon Not Found Exception returns Not Found Error`() {
        val notFoundResponse = Response.error<Any>(
            HttpURLConnection.HTTP_NOT_FOUND,
            mockkClass(ResponseBody::class, relaxed = true))
        val notFoundHttpException = HttpException(notFoundResponse)

        coEvery { dao.getAllPokemon() } returns emptyList()
        coEvery { datasource.fetchAllPokemonAsync() } throws notFoundHttpException

        runBlocking {
            assertEquals(NetworkWrapper.Error(ErrorEntity.NotFound), repository.allPokemonApiCall())
        }
    }

    @Test
    fun `FetchAllPokemon Forbidden Exception returns Access Denied Error`() {
        val forbiddenResponse = Response.error<Any>(
            HttpURLConnection.HTTP_FORBIDDEN,
            mockkClass(ResponseBody::class, relaxed = true))
        val forbiddenHttpException = HttpException(forbiddenResponse)

        coEvery { dao.getAllPokemon() } returns emptyList()
        coEvery { datasource.fetchAllPokemonAsync() } throws forbiddenHttpException

        runBlocking {
            assertEquals(NetworkWrapper.Error(ErrorEntity.AccessDenied), repository.allPokemonApiCall())
        }
    }

    @Test
    fun `FetchAllPokemon Unavailable Exception returns Service Unavailable Error`() {
        val unavailableResponse = Response.error<Any>(
            HttpURLConnection.HTTP_UNAVAILABLE,
            mockkClass(ResponseBody::class, relaxed = true))
        val unavailableHttpException = HttpException(unavailableResponse)

        coEvery { dao.getAllPokemon() } returns emptyList()
        coEvery { datasource.fetchAllPokemonAsync() } throws unavailableHttpException

        runBlocking {
            assertEquals(NetworkWrapper.Error(ErrorEntity.ServiceUnavailable), repository.allPokemonApiCall())
        }
    }

    @Test
    fun `FetchAllPokemon Other HttpException returns Unknown Error`() {
        val badGatewayResponse = Response.error<Any>(
            HttpURLConnection.HTTP_BAD_GATEWAY,
            mockkClass(ResponseBody::class, relaxed = true))
        val badGatewayHttpException = HttpException(badGatewayResponse)

        coEvery { dao.getAllPokemon() } returns emptyList()
        coEvery { datasource.fetchAllPokemonAsync() } throws badGatewayHttpException

        runBlocking {
            assertEquals(NetworkWrapper.Error(ErrorEntity.Unknown), repository.allPokemonApiCall())
        }
    }

    @Test
    fun `FetchAllPokemon Other Exception returns Unknown Error`() {
        val illegalStateHttpException = IllegalStateException("IllegalStateException()")

        coEvery { dao.getAllPokemon() } returns emptyList()
        coEvery { datasource.fetchAllPokemonAsync() } throws illegalStateHttpException

        runBlocking {
            assertEquals(NetworkWrapper.Error(ErrorEntity.Unknown), repository.allPokemonApiCall())
        }
    }

}