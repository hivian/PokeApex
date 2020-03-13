package com.hivian.repository

import androidx.lifecycle.LiveData
import com.hivian.model.dto.network.ApiResult
import com.hivian.model.dto.network.NetworkPokemon
import com.hivian.remote.PokemonDatasource
import com.hivian.repository.utils.NetworkBoundResource
import com.hivian.repository.utils.Resource
import kotlinx.coroutines.Deferred

/*interface UserRepository {
    suspend fun getTopPokemonsWithCache(forceRefresh: Boolean = false, , offset: Int = 0, limit: Int = 20): LiveData<Resource<List<User>>>
    suspend fun getPokemonDetailWithCache(forceRefresh: Boolean = false, login: String): LiveData<Resource<User>>
}

class UserRepositoryImpl(private val datasource: PokemonDatasource,
                         private val dao: UserDao): UserRepository {

    /**
     * Suspended function that will get a list of top [User]
     * whether in cache (SQLite) or via network (API).
     * [NetworkBoundResource] is responsible to handle this behavior.
     */
    override suspend fun getTopPokemonsWithCache(forceRefresh: Boolean, offset: Int, limit: Int): LiveData<Resource<List<User>>> {
        return object : NetworkBoundResource<List<User>, ApiResult<User>>() {

            override fun processResponse(response: ApiResult<User>): List<User>
                    = response.items

            override suspend fun saveCallResults(items: List<User>)
                    = dao.save(items)

            override fun shouldFetch(data: List<User>?): Boolean
                    = data == null || data.isEmpty() || forceRefresh

            override suspend fun loadFromDb(): List<User>
                    = dao.getTopUsers()

            override fun createCallAsync(): Deferred<ApiResult<NetworkPokemon>>
                    = datasource.fetchTopPokemonsAsync(offset, limit)

        }.build().asLiveData()
    }

    /**
     * Suspended function that will get details of a [User]
     * whether in cache (SQLite) or via network (API).
     * [NetworkBoundResource] is responsible to handle this behavior.
     */
    override suspend fun getPokemonDetailWithCache(forceRefresh: Boolean, login: String): LiveData<Resource<User>> {
        return object : NetworkBoundResource<User, User>() {

            override fun processResponse(response: User): User
                    = response

            override suspend fun saveCallResults(item: User)
                    = dao.save(item)

            override fun shouldFetch(data: User?): Boolean
                    = data == null
                    || data.haveToRefreshFromNetwork()
                    || data.name.isNullOrEmpty()
                    || forceRefresh

            override suspend fun loadFromDb(): User
                    = dao.getUser(login)

            override fun createCallAsync(): Deferred<User>
                    = datasource.fetchUserDetailsAsync(login)

        }.build().asLiveData()
    }
}*/