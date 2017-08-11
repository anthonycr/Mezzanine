package com.anthonycr.mezzanine.extensions

/**
 * Generic map function that allows mapping from any type to another type.
 */
fun <T, R> T.with(predicate: (T) -> R): R {
    return predicate.invoke(this)
}

/**
 * Allows for arbitrary execution in the middle of a stream based on a value.
 */
fun <T> List<T>.doOnNext(predicate: (T) -> Unit): List<T> {
    this.forEach { predicate.invoke(it) }
    return this
}