package com.legalist.cytpto.util

data class Resource<out T>(val status: String, val data: T?, val message: String?) {

    companion object {

        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS.toString(), data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(Status.ERROR.toString(), data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING.toString(), data, null)
        }

    }

}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}