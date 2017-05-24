package com.anthonycr.mezzanine.utils;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

import javax.lang.model.element.TypeElement;

/**
 * Created by anthonycr on 5/23/17.
 */
public final class TypeEntry<T> implements Map.Entry<TypeElement, T> {

    @NotNull private TypeElement key;
    @NotNull private T value;

    public TypeEntry(@NotNull TypeElement key, @NotNull T value) {
        this.key = key;
        this.value = value;
    }

    @NotNull
    @Override
    public TypeElement getKey() {
        return key;
    }

    @NotNull
    @Override
    public T getValue() {
        return value;
    }

    @Override
    public T setValue(@NotNull T value) {
        return this.value = value;
    }
}
