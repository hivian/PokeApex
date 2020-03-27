package com.hivian.repository

import androidx.lifecycle.LiveData
import com.hivian.local.dao.PokedexDao
import com.hivian.model.domain.Pokemon
import com.hivian.model.dto.database.DbPokemon
import com.hivian.model.dto.network.ApiResult
import com.hivian.model.dto.network.NetworkPokemonObject
import com.hivian.model.mapper.MapperPokedexRepository
import com.hivian.remote.PokemonDatasource
import com.hivian.repository.utils.NetworkBoundResource
import com.hivian.repository.utils.Resource

interface PokedexRepository {
    suspend fun getTopPokemonsWithCache(
        forceRefresh: Boolean = false,
        offset: Int = 0,
        limit: Int = 20
    ): LiveData<Resource<List<Pokemon>>>
    suspend fun getPokemonDetailWithCache(
        forceRefresh: Boolean = false,
        name: String
    ):LiveData<Resource<Pokemon>>
}

class PokedexRepositoryImpl(
    private val datasource: PokemonDatasource,
    private val dao: PokedexDao,
    private val mapper: MapperPokedexRepository
): PokedexRepository {

    /**
     * Suspended function that will get a list of top [Pokemon]
     * whether in cache (SQLite) or via network (API).
     * [NetworkBoundResource] is responsible to handle this behavior.
     */
    override suspend fun getTopPokemonsWithCache(forceRefresh: Boolean, offset: Int, limit: Int): LiveData<Resource<List<Pokemon>>> {
        return object : NetworkBoundResource<ApiResult<NetworkPokemonObject>, List<DbPokemon>, List<Pokemon>>() {

            override fun processResponse(response: ApiResult<NetworkPokemonObject>): List<DbPokemon> =
                mapper.remoteToDbMapper.map(response.results)

            override suspend fun saveCallResult(result: List<DbPokemon>) =
                    dao.save(result)

            override fun shouldFetch(data: List<DbPokemon>?): Boolean =
                    data == null || data.isEmpty() || forceRefresh

            override suspend fun loadFromDb(): List<DbPokemon> =
                    dao.getTopPokemons()

            override suspend fun processData(data: List<DbPokemon>): List<Pokemon> =
                    mapper.dbToDomainMapper.map(data)

            override suspend fun createCallAsync(): ApiResult<NetworkPokemonObject> =
                    datasource.fetchTopPokemonsAsync(offset, limit)
        }.build().asLiveData()
    }

    /**
     * Suspended function that will get details of a [Pokemon]
     * whether in cache (SQLite) or via network (API).
     * [NetworkBoundResource] is responsible to handle this behavior.
     */
    override suspend fun getPokemonDetailWithCache(forceRefresh: Boolean, name: String): LiveData<Resource<Pokemon>> {
        return object : NetworkBoundResource<NetworkPokemonObject, DbPokemon, Pokemon>() {

            override fun processResponse(response: NetworkPokemonObject): DbPokemon =
                    mapper.remoteToDbMapper.map(response)

            override suspend fun saveCallResult(result: DbPokemon) =
                    dao.save(result)

            override fun shouldFetch(data: DbPokemon?): Boolean =
                    data == null ||
                    data.haveToRefreshFromNetwork() ||
                    data.name.isEmpty() ||
                    forceRefresh

            override suspend fun loadFromDb(): DbPokemon =
                    dao.getPokemon(name)

            override suspend fun processData(data: DbPokemon): Pokemon =
                    mapper.dbToDomainMapper.map(data)

            override suspend fun createCallAsync(): NetworkPokemonObject =
                    datasource.fetchPokemonDetailAsync(name)
        }.build().asLiveData()
    }
}
