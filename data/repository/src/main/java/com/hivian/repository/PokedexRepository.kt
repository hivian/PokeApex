package com.hivian.repository

import androidx.lifecycle.MutableLiveData
import com.hivian.local.dao.PokedexDao
import com.hivian.model.domain.Pokemon
import com.hivian.model.dto.database.DbPokemon
import com.hivian.model.dto.network.NetworkPokemonObject
import com.hivian.model.mapper.MapperPokedexRepository
import com.hivian.remote.PokemonDatasource
import com.hivian.repository.utils.NetworkBoundResource
import com.hivian.repository.utils.ResultWrapper

interface PokedexRepository {
    suspend fun getPokemonListWithCache(
        forceRefresh: Boolean = false,
        offset: Int,
        limit: Int
    ): ResultWrapper<List<Pokemon>>
    suspend fun getPokemonDetailWithCache(
        forceRefresh: Boolean = false,
        name: String
    ): ResultWrapper<Pokemon>
    suspend fun getPokemonListByPatternLocal(pattern: String): List<Pokemon>
}

class PokedexRepositoryImpl(
    private val datasource: PokemonDatasource,
    private val dao: PokedexDao,
    private val mapper: MapperPokedexRepository
): PokedexRepository {

    val data: MutableLiveData<List<Pokemon>> = MutableLiveData()

    override suspend fun getPokemonListWithCache(forceRefresh: Boolean, offset: Int, limit: Int): ResultWrapper<List<Pokemon>> {
        return object : NetworkBoundResource<List<NetworkPokemonObject>, List<DbPokemon>, List<Pokemon>>() {

            override fun processResponse(response: List<NetworkPokemonObject>): List<DbPokemon> =
                    mapper.remoteToDbMapper.map(response)

            override suspend fun saveCallResult(result: List<DbPokemon>) {
                dao.save(result)
            }

            override fun shouldFetch(data: List<DbPokemon>?): Boolean =
                    data == null ||
                    data.isEmpty() ||
                    data.first().haveToRefreshFromNetwork() ||
                    forceRefresh

            override suspend fun loadFromDb(): List<DbPokemon> =
                    dao.getTopPokemons()

            override suspend fun processData(data: List<DbPokemon>): List<Pokemon> =
                    mapper.dbToDomainMapper.map(data)

            override suspend fun createCallAsync(): List<NetworkPokemonObject> {
                val results = datasource.fetchTopPokemonsAsync(offset, limit).results
                return mapper.networkToObjectImpl.map(results)
            }

            override suspend fun isEmptyResult(response: List<NetworkPokemonObject>): Boolean =
                response.isEmpty()

        }.build()
    }

    /**
     * Suspended function that will get details of a [Pokemon]
     * whether in cache (SQLite) or via network (API).
     * [NetworkBoundResource] is responsible to handle this behavior.
     */
    override suspend fun getPokemonDetailWithCache(forceRefresh: Boolean, name: String): ResultWrapper<Pokemon> {
        return object : NetworkBoundResource<NetworkPokemonObject, DbPokemon, Pokemon>() {

            override fun processResponse(response: NetworkPokemonObject): DbPokemon =
                    mapper.remoteToDbMapper.map(response)

            override suspend fun saveCallResult(result: DbPokemon) =
                    dao.save(result)

            override fun shouldFetch(data: DbPokemon?): Boolean =
                    data == null ||
                    data.name.isEmpty() ||
                    data.haveToRefreshFromNetwork() ||
                    forceRefresh

            override suspend fun loadFromDb(): DbPokemon =
                    dao.getPokemonByName(name)

            override suspend fun processData(data: DbPokemon): Pokemon =
                    mapper.dbToDomainMapper.map(data)

            override suspend fun createCallAsync(): NetworkPokemonObject =
                    datasource.fetchPokemonDetailAsync(name)

            override suspend fun isEmptyResult(response: NetworkPokemonObject): Boolean = false
        }.build()
    }

    override suspend fun getPokemonListByPatternLocal(pattern: String): List<Pokemon> =
        mapper.dbToDomainMapper.map(dao.getPokemonListByPattern(pattern))


}
