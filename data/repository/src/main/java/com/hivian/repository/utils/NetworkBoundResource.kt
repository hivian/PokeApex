package com.hivian.repository.utils

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.ajalt.timberkt.d
import com.github.ajalt.timberkt.e
import kotlinx.coroutines.*
import java.lang.Exception
import kotlin.coroutines.coroutineContext

abstract class NetworkBoundResource<ResultType, RequestType> {

    private val result = MutableLiveData<Resource<ResultType>>()
    private val supervisorJob = SupervisorJob()

    suspend fun build(): NetworkBoundResource<ResultType, RequestType> {
        withContext(Dispatchers.Main) { result.value =
            Resource.loading(null)
        }
        CoroutineScope(coroutineContext).launch(supervisorJob) {
            val dbResult = loadFromDb()
            if (shouldFetch(dbResult)) {
                try {
                    fetchFromNetwork(dbResult)
                } catch (e: Exception) {
                    e { "An error happened: $e" }
                    setValue(Resource.error(e, loadFromDb()))
                }
            } else {
                d { "Return data from local database" }
                setValue(Resource.success(dbResult))
            }
        }
        return this
    }

    fun asLiveData() = result as LiveData<Resource<ResultType>>

    // ---

    private suspend fun fetchFromNetwork(dbResult: ResultType) {
        d { "Return data from local database" }
        setValue(Resource.loading(dbResult)) // Dispatch latest value quickly (UX purpose)
        val apiResponse = createCallAsync().await()
        d { "Data fetched from network" }
        saveCallResults(processResponse(apiResponse))
        setValue(Resource.success(loadFromDb()))
    }

    @MainThread
    private fun setValue(newValue: Resource<ResultType>) {
        d { "Resource: $newValue" }
        if (result.value != newValue) result.postValue(newValue)
    }

    @WorkerThread
    protected abstract fun processResponse(response: RequestType): ResultType

    @WorkerThread
    protected abstract suspend fun saveCallResults(items: ResultType)

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract suspend fun loadFromDb(): ResultType

    @MainThread
    protected abstract fun createCallAsync(): Deferred<RequestType>
}