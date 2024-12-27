package com.anthonycr.mezzanine

/**
 * Return the mezzanine for type [T].
 */
inline fun <reified T> mezzanine(): T = mezzanine(T::class.java)

/**
 * Return the mezzanine of type [clazz].
 */
fun <T> mezzanine(clazz: Class<T>): T = mezzanineInternal(clazz)