package com.anthonycr.mezzanine.generator;

import com.anthonycr.mezzanine.utils.FileGenUtils;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

import javax.annotation.processing.Filer;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

import io.reactivex.Completable;
import io.reactivex.functions.Function;

/**
 * The Java generator.
 * <p>
 * Created by anthonycr on 5/22/17.
 */
public class MezzanineGenerator {

    private static final String PREFIX = "Generator_";

    private static final String PACKAGE_NAME = "com.anthonycr.mezzanine";
    private static final String CLASS_NAME = "Mezzanine";

    private MezzanineGenerator() {}

    /**
     * A mapping function that generates the {@link TypeSpec}
     * for the interface represented by the {@link TypeElement}
     * which returns the {@link String}.
     *
     * @return a valid mapping function.
     */
    @NotNull
    public static Function<Map.Entry<TypeElement, String>, TypeSpec> generateGeneratorTypeSpecs() {
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

    /**
     * Generates the Mezzanine {@link TypeSpec}
     * from a list of {@link TypeSpec}.
     *
     * @return a valid mapping function.
     */
    @NotNull
    public static Function<List<TypeSpec>, TypeSpec> generateMezzanineTypeSpec() {
        return typeSpecs -> {
            TypeSpec.Builder mezzanineTypeSpecBuilder = TypeSpec.classBuilder(CLASS_NAME)
                .addModifiers(Modifier.PUBLIC);

            typeSpecs.forEach(mezzanineTypeSpecBuilder::addType);

            return mezzanineTypeSpecBuilder.build();
        };
    }

    /**
     * Generates a {@link JavaFile} from
     * a {@link TypeSpec}, {@link #PACKAGE_NAME}
     * will be used as the package name.
     *
     * @return a valid mapping function.
     */
    @NotNull
    public static Function<TypeSpec, JavaFile> generateJavaFile() {
        return typeSpec -> JavaFile.builder(PACKAGE_NAME, typeSpec).build();
    }

    /**
     * Mapping function that writes a
     * {@link JavaFile} to disk and emits
     * a {@link Completable} that completes
     * when the file has been written to disk.
     *
     * @param filer the filer necessary to write to disk.
     * @return a valid mapping function.
     */
    @NotNull
    public static Function<JavaFile, Completable> writeFileToDisk(@NotNull Filer filer) {
        return javaFile -> {
            FileGenUtils.writeToFile(javaFile, filer);
            return Completable.complete();
        };
    }

}
