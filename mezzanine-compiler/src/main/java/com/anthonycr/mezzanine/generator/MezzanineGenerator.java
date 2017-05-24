package com.anthonycr.mezzanine.generator;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

import io.reactivex.functions.Function;

/**
 * Created by anthonycr on 5/22/17.
 */
public class MezzanineGenerator {

    private static String PREFIX = "Generator_";

    private MezzanineGenerator() {}

    @NotNull
    public static Function<Map.Entry<TypeElement, String>, TypeSpec> generateMezzanineTypeSpec() {
        return typeElementStringEntry -> {

            TypeElement typeElement = typeElementStringEntry.getKey();
            String fileContents = StringEscapeUtils.escapeJava(typeElementStringEntry.getValue());

            ExecutableElement singleMethod = (ExecutableElement) typeElement.getEnclosedElements().get(0);

            MethodSpec methodSpec = MethodSpec.methodBuilder(singleMethod.getSimpleName().toString())
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(String.class)
                .addCode("return \"" + fileContents + "\";\n")
                .build();

            return TypeSpec.classBuilder(PREFIX + typeElement.getSimpleName())
                .addModifiers(Modifier.PUBLIC)
                .addModifiers(Modifier.STATIC)
                .addSuperinterface(ClassName.get(typeElement))
                .addMethod(methodSpec)
                .build();
        };
    }

}
