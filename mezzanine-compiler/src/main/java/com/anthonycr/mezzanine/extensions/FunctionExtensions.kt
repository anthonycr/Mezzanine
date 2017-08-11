package com.anthonycr.mezzanine.extensions

/**
 * Allows for arbitrary execution in the middle of a stream based on a value.
 */
inline fun <T> List<T>.doOnNext(predicate: (T) -> Unit): List<T> {
    this.forEach { predicate.invoke(it) }
    return this
}