package com.hivian.repository.utils

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.withContext

abstract class NetworkResource<Remote, Local> {

    private val supervisorJob = SupervisorJob()

    suspend fun build(): NetworkWrapper {
        return withContext(supervisorJob) {
            val dbResult = loadFromDb()
            if (shouldFetch(dbResult)) {
                try {
                    fetchFromNetwork()
                } catch (throwable: Throwable) {
                    NetworkWrapper.Error(ErrorHandlerImpl.getError(throwable))
                }
            } else {
                NetworkWrapper.Success(processData(dbResult))
            }
        }
    }

    // ---

    private suspend fun fetchFromNetwork() : NetworkWrapper {
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
    protected abstract suspend fun processData(data: Local) : Boolean

    @MainThread
    protected abstract suspend fun createCallAsync(): Remote
}
