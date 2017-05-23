package com.anthonycr.mezzanine.source;

import com.anthonycr.mezzanine.utils.MessagerUtils;

import org.jetbrains.annotations.NotNull;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;

import io.reactivex.Flowable;
import io.reactivex.functions.Predicate;

/**
 * Created by anthonycr on 5/22/17.
 */
public class SupportedElementSource implements ElementSource {

    @NotNull private final ElementSource elementSource;

    public SupportedElementSource(@NotNull ElementSource elementSource) {
        this.elementSource = elementSource;
    }

    @NotNull
    @Override
    public Flowable<Element> createElementStream() {
        return elementSource.createElementStream()
            .filter(filterForSupportedElements());
    }

    @NotNull
    private static String className(@NotNull Class clazz) {
        return clazz.getName().replace('$', '.');
    }

    @NotNull
    private static Predicate<Element> filterForSupportedElements() {
        return methodElement -> {

            Element classElement = methodElement.getEnclosingElement();

            if (classElement.getKind() != ElementKind.INTERFACE) {
                MessagerUtils.reportError(classElement, "Only interfaces are supported");
                return false;
            }

            if (classElement.getEnclosedElements().size() != 1) {
                MessagerUtils.reportError(classElement, "Only interfaces with 1 method are supported");
                return false;
            }

            if (!(methodElement instanceof ExecutableElement)) {
                MessagerUtils.reportError(methodElement, "Only method annotations are supported");
                return false;
            }

            ExecutableElement creatorMethodElement = (ExecutableElement) methodElement;

            if (!creatorMethodElement.getReturnType().toString().equals(className(String.class))) {
                MessagerUtils.reportError(creatorMethodElement, "Interface must have a String return type");
                return false;
            }

            return true;
        };
    }
}
