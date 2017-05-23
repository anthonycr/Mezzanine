package com.anthonycr.mezzanine;

import com.anthonycr.mezzanine.source.MezzanineElementSource;
import com.anthonycr.mezzanine.source.SupportedElementSource;
import com.anthonycr.mezzanine.utils.FileGenUtils;
import com.anthonycr.mezzanine.utils.MessagerUtils;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

@AutoService(Processor.class)
@SupportedAnnotationTypes(value = {"com.anthonycr.mezzanine.FileStream"})
public class MezzanineProcessor extends AbstractProcessor {

    private static final String PACKAGE_NAME = "com.anthonycr.mezzanine";

    private boolean isProcessed;

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        if (isProcessed) {
            return true;
        }

        isProcessed = true;

        MessagerUtils.intitialize(processingEnv.getMessager());

        MezzanineElementSource mezzanineElementSource = new MezzanineElementSource(roundEnvironment);
        SupportedElementSource supportedElements = new SupportedElementSource(mezzanineElementSource);

        MessagerUtils.reportInfo("Starting Mezzanine");
        supportedElements.createElementStream()
            .map(MezzanineMapper.mapElementToFile())
            .doOnNext(typeElementFileEntry -> MessagerUtils.reportInfo("Processing for file: " + typeElementFileEntry.getValue()))
            .map(MezzanineMapper.mapFileToString())
            .map(MezzanineGenerator.generateMezzanineTypeSpec())
            .toList()
            .subscribe(typeSpecs -> {
                TypeSpec.Builder mezzanineTypeSpecBuilder = TypeSpec.classBuilder("Mezzanine")
                    .addModifiers(Modifier.PUBLIC);

                typeSpecs.forEach(mezzanineTypeSpecBuilder::addType);

                JavaFile javaFile = JavaFile.builder(PACKAGE_NAME, mezzanineTypeSpecBuilder.build()).build();

                Filer filer = processingEnv.getFiler();

                FileGenUtils.writeToFile(javaFile, filer);
            });

        return true;
    }
}
