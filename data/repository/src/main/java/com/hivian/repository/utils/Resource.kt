package com.hivian.repository.utils

data class Resource<out T>(val status: Status, val data: T?, val code: Int? = null, val error: Exception?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(
                Status.SUCCESS,
                data,
                null,
                null
            )
        }

        fun <T> httpError(code: Int? = null, error: Exception?, data: T?): Resource<T> {
            return Resource(
                Status.HTTP_ERROR,
                data,
                code,
                error
            )
        }

        fun <T> networkError(error: Exception?, data: T?): Resource<T> {
            return Resource(
                Status.NETWORK_ERROR,
                data,
                null,
                error
            )
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(
                Status.LOADING,
                data,
                null,
                null
            )
        }
    }

    enum class Status {
        SUCCESS,
        HTTP_ERROR,
        NETWORK_ERROR,
        LOADING
    }
}
