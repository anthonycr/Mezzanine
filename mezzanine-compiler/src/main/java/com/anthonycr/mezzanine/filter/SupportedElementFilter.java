package com.anthonycr.mezzanine.filter;

import com.anthonycr.mezzanine.utils.MessagerUtils;

import org.jetbrains.annotations.NotNull;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;

import io.reactivex.functions.Predicate;

/**
 * Filters used by the Mezzanine processor.
 * <p>
 * Created by anthonycr on 5/22/17.
 */
public final class SupportedElementFilter {

    private SupportedElementFilter() {}

    /**
     * Filters a list of elements for only
     * the elements supported by Mezzanine.
     * If unsupported elements are in the stream,
     * it will report a message to the
     * {@link MessagerUtils} so that the consumer
     * knows what they have done wrong.
     *
     * @return a valid filter.
     */
    @NotNull
    public static Predicate<Element> filterForSupportedElements() {
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

            if (!creatorMethodElement.getReturnType().toString().equals(String.class.getName().replace('$', '.'))) {
                MessagerUtils.reportError(creatorMethodElement, "Interface must have a String return type");
                return false;
            }

            return true;
        };
    }
}
