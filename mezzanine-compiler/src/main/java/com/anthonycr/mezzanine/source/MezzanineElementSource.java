package com.anthonycr.mezzanine.source;

import com.anthonycr.mezzanine.FileStream;

import org.jetbrains.annotations.NotNull;

import java.util.Set;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;

import io.reactivex.Flowable;

/**
 * Created by anthonycr on 5/22/17.
 */
public class MezzanineElementSource implements ElementSource {

    @NotNull private RoundEnvironment roundEnvironment;

    public MezzanineElementSource(@NotNull RoundEnvironment roundEnvironment) {
        this.roundEnvironment = roundEnvironment;
    }

    @NotNull
    @Override
    public Flowable<Element> createElementStream() {
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(FileStream.class);

        return Flowable.fromIterable(elements);
    }
}
