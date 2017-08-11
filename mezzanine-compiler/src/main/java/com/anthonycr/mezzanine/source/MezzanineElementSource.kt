package com.anthonycr.mezzanine.source

import com.anthonycr.mezzanine.FileStream
import javax.annotation.processing.RoundEnvironment

/**
 * An element source that provides a stream of elements annotated with the [FileStream] annotation.
 */
class MezzanineElementSource(private val roundEnvironment: RoundEnvironment) {

    /**
     * A stream of elements.
     *
     * @return a valid set of elements.
     */
    fun createElementStream() = roundEnvironment.getElementsAnnotatedWith(FileStream::class.java).asSequence()

}
