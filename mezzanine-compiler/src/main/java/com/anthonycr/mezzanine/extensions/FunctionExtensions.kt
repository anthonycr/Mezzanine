package com.anthonycr.mezzanine.extensions

/**
 * Allows for arbitrary execution in the middle of a stream based on a value.
 */
inline fun <T> Sequence<T>.doOnNext(predicate: (T) -> Unit): Sequence<T> {
    this.forEach { predicate.invoke(it) }
    return this
}