package com.hivian.repository.utils

import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import com.hivian.common.generic.fromJson
import java.io.IOException

suspend fun <T> safeApiCall(dispatcher: CoroutineDispatcher, apiCall: suspend () -> T): Resource<T> {
    return withContext(dispatcher) {
        try {
            Resource.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> Resource.NetworkError
                is HttpException -> {
                    val code = throwable.code()
                    val errorResponse = convertErrorBody(throwable)
                    Resource.GenericError(code, errorResponse)
                }
                else -> {
                    Resource.GenericError(null, convertErrorBody(throwable))
                }
            }
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