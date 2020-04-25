package com.hivian.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.hivian.local.dao.PokedexDao
import com.hivian.model.domain.Pokemon
import com.hivian.model.dto.database.DbPokemon
import com.hivian.model.dto.network.NetworkPokemonObject
import com.hivian.model.mapper.MapperPokedexRepository
import com.hivian.remote.PokemonDatasource
import com.hivian.repository.utils.NetworkResource
import com.hivian.repository.utils.NetworkWrapper


interface PokedexRepository {
    suspend fun allPokemonApiCall(
        forceRefresh: Boolean = false,
        offset: Int,
        limit: Int
    ): NetworkWrapper
    suspend fun pokemonDetailApiCall(
        name: String
    ): NetworkWrapper
    suspend fun updateFavoriteStatusLocal(pokemonId: Int, favorite: Boolean)
    suspend fun updateCaughtStatusLocal(pokemonId: Int, caught: Boolean)
    fun getAllPokemonByPatternLocal(pattern: String): LiveData<List<Pokemon>>
    fun getAllPokemonFavoritesByPatternLocal(pattern: String): LiveData<List<Pokemon>>
    fun getAllPokemonCaughtByPatternLocal(pattern: String): LiveData<List<Pokemon>>
    fun getAllPokemonLocalLive(): LiveData<List<Pokemon>>
    fun getPokemonDetailLocalLive(name: String): LiveData<Pokemon>
    fun getPokemonFavoritesLocalLive(): LiveData<List<Pokemon>>
    fun getPokemonCaughtLocalLive(): LiveData<List<Pokemon>>
}

class PokedexRepositoryImpl(
    private val datasource: PokemonDatasource,
    private val dao: PokedexDao,
    private val mapper: MapperPokedexRepository
): PokedexRepository {

    val data: MutableLiveData<List<Pokemon>> = MutableLiveData()

    override suspend fun allPokemonApiCall(forceRefresh: Boolean, offset: Int, limit: Int): NetworkWrapper {
        return object : NetworkResource<List<NetworkPokemonObject>, List<DbPokemon>>() {

            override fun processResponse(response: List<NetworkPokemonObject>): List<DbPokemon> =
                mapper.remoteToDbMapper.map(response)

            override suspend fun saveCallResult(result: List<DbPokemon>) {
                dao.savePokemonPreview(result)
            }

            override fun shouldFetch(data: List<DbPokemon>?): Boolean =
                    data == null ||
                    data.isEmpty() ||
                    data.first().haveToRefreshFromNetwork() ||
                    forceRefresh

            override suspend fun loadFromDb(): List<DbPokemon> =
                    dao.getAllPokemon()

            override suspend fun processData(data: List<DbPokemon>): Boolean =
                    data.isEmpty()

            override suspend fun createCallAsync(): List<NetworkPokemonObject> {
                val results = datasource.fetchTopPokemonsAsync(offset, limit).results
                return mapper.networkToObjectImpl.map(results)
            }

        }.build()
    }

    /**
     * Suspended function that will get details of a [Pokemon]
     * whether in cache (SQLite) or via network (API).
     * [NetworkResource] is responsible to handle this behavior.
     */
    override suspend fun pokemonDetailApiCall(name: String): NetworkWrapper {
        return object : NetworkResource<NetworkPokemonObject, DbPokemon>() {

            override fun processResponse(response: NetworkPokemonObject): DbPokemon =
                    mapper.remoteToDbMapper.map(response)

            override suspend fun saveCallResult(result: DbPokemon) =
                    dao.savePokemonDetail(result)

            override fun shouldFetch(data: DbPokemon?): Boolean =
                    data == null ||
                    data.stats.isEmpty() ||
                    data.haveToRefreshFromNetwork()

            override suspend fun loadFromDb(): DbPokemon =
                    dao.getPokemonByName(name)

            override suspend fun processData(data: DbPokemon): Boolean =
                    false

            override suspend fun createCallAsync(): NetworkPokemonObject =
                    datasource.fetchPokemonDetailAsync(name)

        }.build()
    }

    override suspend fun updateFavoriteStatusLocal(pokemonId: Int, favorite: Boolean) {
        dao.updatePokemonFavoriteStatus(pokemonId, favorite)
    }

    override suspend fun updateCaughtStatusLocal(pokemonId: Int, caught: Boolean) {
        dao.updatePokemonCaughtStatus(pokemonId, caught)
    }

    override fun getAllPokemonByPatternLocal(pattern: String): LiveData<List<Pokemon>> =
        Transformations.switchMap(dao.getAllPokemonPatternLive(pattern)) {
            mapDbToDomainLiveData(it)
        }

    override fun getAllPokemonFavoritesByPatternLocal(pattern: String): LiveData<List<Pokemon>> =
        Transformations.switchMap(dao.getAllPokemonFavoritesPatternLive(pattern)) {
            mapDbToDomainLiveData(it)
        }

    override fun getAllPokemonCaughtByPatternLocal(pattern: String): LiveData<List<Pokemon>> =
        Transformations.switchMap(dao.getAllPokemonCaughtPatternLive(pattern)) {
            mapDbToDomainLiveData(it)
        }

    override fun getAllPokemonLocalLive(): LiveData<List<Pokemon>> =
        Transformations.switchMap(dao.getAllPokemonLive()) {
            mapDbToDomainLiveData(it)
        }

    override fun getPokemonDetailLocalLive(name: String): LiveData<Pokemon> =
        Transformations.switchMap(dao.getPokemonDetailLive(name)) {
            mapDbToDomainLiveData(it)
        }

    override fun getPokemonFavoritesLocalLive(): LiveData<List<Pokemon>> =
        Transformations.switchMap(dao.getPokemonFavoritesLive()) {
            mapDbToDomainLiveData(it)
        }

    override fun getPokemonCaughtLocalLive(): LiveData<List<Pokemon>> =
        Transformations.switchMap(dao.getPokemonCaughtLive()) {
            mapDbToDomainLiveData(it)
        }

    private fun mapDbToDomainLiveData(input: List<DbPokemon>): LiveData<List<Pokemon>> {
        val pokemonLiveData : MutableLiveData<List<Pokemon>> = MutableLiveData()
        val pokemons = mapper.dbToDomainMapper.map(input)
        pokemonLiveData.value = pokemons
        return pokemonLiveData
    }

    private fun mapDbToDomainLiveData(input: DbPokemon): LiveData<Pokemon> {
        val pokemonLiveData : MutableLiveData<Pokemon> = MutableLiveData()
        val pokemon = mapper.dbToDomainMapper.map(input)
        pokemonLiveData.value = pokemon
        return pokemonLiveData
    }
}
