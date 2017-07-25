package com.anthonycr.mezzanine.source

import com.anthonycr.mezzanine.FileStream

import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.Element

import io.reactivex.Flowable

/**
 * An element source that provides a stream
 * of elements annotated with the [FileStream]
 * annotation.
 *
 *
 * Created by anthonycr on 5/22/17.
 */
class MezzanineElementSource(private val roundEnvironment: RoundEnvironment) : ElementSource {

    override fun createElementStream(): Flowable<Element> {
        val elements = roundEnvironment.getElementsAnnotatedWith(FileStream::class.java)

        return Flowable.fromIterable(elements)
    }
}
