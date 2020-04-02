package com.hivian.repository.utils

sealed class Resource<out T> {
    object Loading : Resource<Nothing>()
    data class Success<out T>(val value: T): Resource<T>()
    data class GenericError(val code: Int? = null, val error: ErrorResponse? = null): Resource<Nothing>()
    object NetworkError: Resource<Nothing>()
}
