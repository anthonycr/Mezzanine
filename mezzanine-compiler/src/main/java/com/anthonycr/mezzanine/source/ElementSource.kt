package com.anthonycr.mezzanine.source

import io.reactivex.Flowable
import javax.lang.model.element.Element

/**
 * An interface representing a source of elements.
 *
 *
 * Created by anthonycr on 5/22/17.
 */
interface ElementSource {

    /**
     * A stream of elements.

     * @return a valid observable.
     */
    fun createElementStream(): Flowable<Element>
}
