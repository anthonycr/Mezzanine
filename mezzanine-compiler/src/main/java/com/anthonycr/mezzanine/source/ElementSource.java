package com.anthonycr.mezzanine.source;

import org.jetbrains.annotations.NotNull;

import javax.lang.model.element.Element;

import io.reactivex.Flowable;

/**
 * Created by anthonycr on 5/22/17.
 */
public interface ElementSource {

    @NotNull
    Flowable<Element> createElementStream();
}
