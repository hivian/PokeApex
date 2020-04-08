package com.hivian.repository.utils

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import com.github.ajalt.timberkt.e
import com.github.ajalt.timberkt.i
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

abstract class NetworkBoundResource<Remote, Local, Domain> {

    private val supervisorJob = SupervisorJob()

    suspend fun build(): ResultWrapper<Domain> {
        return withContext(supervisorJob) {
            val dbResult = loadFromDb()
            if (shouldFetch(dbResult)) {
                try {
                    fetchFromNetwork(dbResult)
                } catch (e: Exception) {
                    val value = when (e) {
                        is IOException -> ResultWrapper.NetworkError(processData(dbResult))
                        is HttpException -> {
                            val code = e.code()
                            ResultWrapper.GenericError(processData(dbResult), code, convertErrorBody(e))
                        }
                        else -> ResultWrapper.GenericError(processData(dbResult),null, convertErrorBody(e))
                    }
                    e { "An error happened: $e" }
                    value
                }
            } else {
                i { "Return data from local database" }
                ResultWrapper.Success(processData(dbResult))
            }
        }
    }

    // ---

    private suspend fun fetchFromNetwork(dbResult: Local) : ResultWrapper<Domain> {
        i { "Return data from local database" }
        val apiResponse = createCallAsync()
        i { "Data fetched from network" }
        saveCallResult(processResponse(apiResponse))
        return ResultWrapper.Success(processData(loadFromDb()), isEmptyResult(apiResponse))
    }

    @WorkerThread
    protected abstract fun processResponse(response: Remote): Local

    @WorkerThread
    protected abstract suspend fun saveCallResult(result: Local)

    @MainThread
    protected abstract fun shouldFetch(data: Local?): Boolean

    @MainThread
    protected abstract suspend fun loadFromDb(): Local

    @MainThread
    protected abstract suspend fun processData(data: Local) : Domain

    @MainThread
    protected abstract suspend fun createCallAsync(): Remote

    @MainThread
    protected abstract suspend fun isEmptyResult(response: Remote): Boolean
}
