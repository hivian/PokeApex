package com.hivian.repository.utils

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T, var emptyResponse: Boolean = false): ResultWrapper<T>()
    data class GenericError<out T>(val value: T, val code: Int? = null, val error: ErrorResponse? = null): ResultWrapper<T>()
    data class NetworkError<out T>(val value: T): ResultWrapper<T>()
}

sealed class NetworkWrapper<out T> {
    data class Success<out T>(val value: T): NetworkWrapper<T>()
    data class GenericError(val code: Int? = null, val error: ErrorResponse? = null): NetworkWrapper<Nothing>()
    object NetworkError: NetworkWrapper<Nothing>()
}
