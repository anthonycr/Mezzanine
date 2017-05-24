package com.anthonycr.mezzanine.source;

import org.jetbrains.annotations.NotNull;

import javax.lang.model.element.Element;

import io.reactivex.Flowable;

/**
 * An interface representing a source of elements.
 * <p>
 * Created by anthonycr on 5/22/17.
 */
public interface ElementSource {

    /**
     * A stream of elements.
     *
     * @return a valid observable.
     */
    @NotNull
    Flowable<Element> createElementStream();
}
