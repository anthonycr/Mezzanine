package com.anthonycr.mezzanine

/**
 * Annotate the single method in your interface
 * with this annotation. Your method should have
 * the return type of [String]. The value
 * you pass to this annotation should be the
 * relative path from the root of your project
 * to the file that should be read in. Note that
 * the file will be read in using UTF-8 encoding.
 *
 * Created by anthonycr on 5/22/17.
 */
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CLASS)
annotation class FileStream(
        /**
         * The relative path to the file from
         * the root of your project. Must not
         * be null.

         * @return the path to the file.
         */
        val value: String
)
