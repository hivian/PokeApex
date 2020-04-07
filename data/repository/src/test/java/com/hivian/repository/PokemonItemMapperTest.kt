package com.hivian.repository

import com.hivian.model.dto.database.DbPokemon
import com.hivian.model.dto.network.NetworkPokemonObject
import com.hivian.model.mapper.MapperPokemonDbToDomainImpl
import com.hivian.model.mapper.MapperPokemonRemoteToDbImpl
import com.hivian.repository.utils.FakeData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class PokemonItemMapperTest {

    private val remoteToDbMapper = MapperPokemonRemoteToDbImpl()
    private val dbToDomainMapper = MapperPokemonDbToDomainImpl()

    @Test
    fun `remoteToDbMapper with empty results should return empty list`() {
        val networkPokemonObject = listOf<NetworkPokemonObject>()

        assertTrue(remoteToDbMapper.map(networkPokemonObject).isNullOrEmpty())
    }

    @Test
    fun `remoteToDbMapper with results should return parsed list`() {
        val networkPokemonObject = FakeData.createFakePokemonsNetwork(3)

        remoteToDbMapper.map(networkPokemonObject).first().run {
            assertEquals(0, id)
            assertEquals("Name_0", name)
            assertEquals("Circle", forms.first())
            assertEquals("Fireball", abilities.first())
            assertEquals("https://pokeres.bastionbot.org/images/pokemon/${id}.png", imageUrl)
            assertEquals(0, stats.first().baseStat)
        }
    }

    @Test
    fun `dbToDomainMapper with empty results should return empty list`() {
        val dbPokemonObject = listOf<DbPokemon>()

        assertTrue(dbToDomainMapper.map(dbPokemonObject).isNullOrEmpty())
    }

    @Test
    fun `dbToDomainMapper with results should return parsed list`() {
        val dbPokemonObject = FakeData.createFakePokemonsDb(3)

        dbToDomainMapper.map(dbPokemonObject).first().run {
            assertEquals("Name_0", name)
            assertEquals("Circle", forms.first())
            assertEquals("Fireball", abilities.first())
            assertEquals("https://", imageUrl)
            assertEquals(0, stats.first().baseStat)
        }
    }
}
