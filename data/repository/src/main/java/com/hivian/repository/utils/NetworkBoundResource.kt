package com.hivian.repository.utils

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.ajalt.timberkt.d
import com.github.ajalt.timberkt.e
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.io.IOException
import kotlin.coroutines.coroutineContext

abstract class NetworkBoundResource<Remote, Local, Domain> {

    private val result = MutableLiveData<Resource<Domain>>()
    private val supervisorJob = SupervisorJob()

    suspend fun build(): NetworkBoundResource<Remote, Local, Domain> {
        withContext(Dispatchers.Main) { result.value =
            Resource.loading(null)
        }
        CoroutineScope(coroutineContext).launch(supervisorJob) {
            val dbResult = loadFromDb()
            if (shouldFetch(dbResult)) {
                try {
                    fetchFromNetwork(dbResult)
                } catch (e: Exception) {
                    val value = when (e) {
                        is IOException -> Resource.networkError(e, processData(loadFromDb()))
                        is HttpException -> {
                            val code = e.code()
                            Resource.httpError(code, e, processData(loadFromDb()))
                        }
                        else -> Resource.httpError(null, e, processData(loadFromDb()))
                    }
                    e { "An error happened: $e" }
                    setValue(value)
                }
            } else {
                d { "Return data from local database" }
                setValue(Resource.success(processData(dbResult)))
            }
        }
        return this
    }

    fun asLiveData() = result as LiveData<Resource<Domain>>

    // ---

    private suspend fun fetchFromNetwork(dbResult: Local) {
        d { "Return data from local database" }
        setValue(Resource.loading(processData(dbResult))) // Dispatch latest value quickly (UX purpose)
        val apiResponse = createCallAsync()
        d { "Data fetched from network" }
        saveCallResult(processResponse(apiResponse))
        setValue(Resource.success(processData(loadFromDb())))
    }

    @MainThread
    private fun setValue(newValue: Resource<Domain>) {
        d { "Resource: $newValue" }
        if (result.value != newValue) result.postValue(newValue)
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
