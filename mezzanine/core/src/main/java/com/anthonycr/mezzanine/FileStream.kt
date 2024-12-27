package com.anthonycr.mezzanine

/**
 * Annotate the single method in your interface with this annotation. Your method should have a
 * return type of [String]. The value you pass to this annotation should be the relative path from
 * the root of your project to the file that should be read in. Note that the file will be read in
 * using UTF-8 encoding.
 *
 * @param value The relative path to the file from the root of your project. Must not be null.
 */
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CLASS)
annotation class FileStream(
    val value: String
)
