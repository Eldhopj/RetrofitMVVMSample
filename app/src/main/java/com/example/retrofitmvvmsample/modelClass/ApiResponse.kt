package com.example.retrofitmvvmsample.modelClass


sealed class State<out T> {

    object Loading : State<Nothing>()
    data class Error(val errorMessage: String?, val error: Throwable? = null) : State<Nothing>()
    data class Success<T>(var data: T) : State<T>()

    companion object {
        fun <T> loading(): State<T> = Loading

        fun <T> error(errorMessage: String, error: Throwable): State<T> = Error(errorMessage, error)

        fun <T> success(data: T): State<T> = Success(data)
    }
}