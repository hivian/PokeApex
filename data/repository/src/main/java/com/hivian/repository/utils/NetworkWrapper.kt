package com.hivian.repository.utils


sealed class NetworkWrapper<out T> {
    data class Success<out T>(val value: T): NetworkWrapper<T>()
    data class GenericError(val code: Int? = null, val error: ErrorResponse? = null): NetworkWrapper<Nothing>()
    object NetworkError: NetworkWrapper<Nothing>()
}
