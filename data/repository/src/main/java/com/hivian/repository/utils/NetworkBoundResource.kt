package com.hivian.repository.utils

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import com.github.ajalt.timberkt.i
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

abstract class NetworkBoundResource<Remote, Local, Domain> {

    private val supervisorJob = SupervisorJob()

    suspend fun build(): NetworkWrapper<Domain> {
        return withContext(supervisorJob) {
            val dbResult = loadFromDb()
            if (shouldFetch(dbResult)) {
                try {
                    fetchFromNetwork()
                } catch (throwable: Throwable) {
                    NetworkWrapper.Error(GeneralErrorHandlerImpl.getError(throwable))
                }
            } else {
                NetworkWrapper.Success(processData(dbResult))
            }
        }
    }

    // ---

    private suspend fun fetchFromNetwork() : NetworkWrapper<Domain> {
        val apiResponse = createCallAsync()
        saveCallResult(processResponse(apiResponse))
        return NetworkWrapper.Success(processData(loadFromDb()))
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
}
