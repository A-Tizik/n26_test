package com.n26.test.myapplication

import io.reactivex.subjects.BehaviorSubject
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract


/**
 * A generic class that holds a value with its loading status.
 * Convenient wrapper for observing data from UI layer to display
 */
sealed class Resource<out T> {
    abstract val data: T?

    class Success<T>(override val data: T) : Resource<T>()

    class Loading<T>(override val data: T? = null) : Resource<T>()

    class Error<T>(override val data: T?, val message: String) : Resource<T>()

}

/**
 * Returns the encapsulated result of the given [transform] function applied to the encapsulated value
 * if this instance contains the data, and retains the error message if the instance is an Error
 */
@OptIn(ExperimentalContracts::class)
fun <T,S> Resource<T>.map(transform: (T) -> S):Resource<S> {
    contract {
        callsInPlace(transform, InvocationKind.AT_MOST_ONCE)
    }
    return when(this) {
        is Resource.Success -> Resource.Success(transform(data))
        is Resource.Loading -> Resource.Loading(data?.let(transform))
        is Resource.Error -> Resource.Error(data?.let(transform),message)
    }
}

