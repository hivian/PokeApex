package com.hivian.repository.utils

import com.google.gson.annotations.SerializedName
import com.hivian.common.generic.fromJson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException

suspend fun <T> safeApiCall(dispatcher: CoroutineDispatcher, apiCall: suspend () -> T): NetworkWrapper<T> {
    return withContext(dispatcher) {
        try {
            NetworkWrapper.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            NetworkWrapper.Error(ErrorHandlerImpl.getError(throwable))
        }
    }
}

fun convertErrorBody(throwable: Throwable): ErrorResponse? {
    if (throwable is HttpException) {
        return try {
            throwable.response()?.errorBody()?.string().toString().run {
                fromJson<ErrorResponse>(this)
            }
        } catch (exception: Exception) {
            null
        }
    } else {
        throwable.message?.run {
            return ErrorResponse(this)
        }
        return null
    }
}

data class ErrorResponse(
    @SerializedName("error_description")
    val errorDescription: String // this is the translated error shown to the user directly from the API
    //val causes: List<String> = emptyList() //this is for errors on specific field on a form
)