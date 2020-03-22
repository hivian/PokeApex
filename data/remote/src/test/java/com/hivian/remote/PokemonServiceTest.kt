package com.hivian.remote

import com.hivian.remote.base.BaseTest
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import retrofit2.HttpException
import java.net.HttpURLConnection

class UserServiceTest: BaseTest() {

    @Test
    fun `search top pokemons by name`() {
        mockHttpResponse(mockServer, "search_pokemons.json", HttpURLConnection.HTTP_OK)
        runBlocking {
            val pokemons = pokemonApiService.fetchTopPokemonsAsync(0, 20)
            Assert.assertEquals(964, pokemons.count)
            Assert.assertEquals(20, pokemons.results.size)
            Assert.assertEquals("bulbasaur", pokemons.results.first().name)
            Assert.assertEquals("raticate", pokemons.results.last().name)
        }
    }

    @Test(expected = HttpException::class)
    fun `search top pokemons by name and fail`() {
        mockHttpResponse(mockServer,"search_pokemons.json", HttpURLConnection.HTTP_FORBIDDEN)
        runBlocking {
            pokemonApiService.fetchTopPokemonsAsync(0, 20)
        }
    }

    // ---

    @Test
    fun `fetch pokemon's detail`() {
        mockHttpResponse(mockServer,"pokemon_detail.json", HttpURLConnection.HTTP_OK)
        runBlocking {
            val pokemon = pokemonApiService.fetchPokemonDetailAsync("bulbasaur")
            Assert.assertEquals(1, pokemon.id)
            Assert.assertEquals("razor-wind", pokemon.moves.first().move.name)
            Assert.assertEquals("hp", pokemon.stats.last().stat.name)
            Assert.assertEquals("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png", pokemon.sprites.frontDefault)
            Assert.assertEquals("bulbasaur", pokemon.forms.first().name)
        }
    }

    @Test(expected = HttpException::class)
    fun `fetch pokemon's detail and fail`() {
        mockHttpResponse(mockServer,"pokemon_detail.json", HttpURLConnection.HTTP_FORBIDDEN)
        runBlocking {
            pokemonApiService.fetchPokemonDetailAsync("bulbasaur")
        }
    }
}