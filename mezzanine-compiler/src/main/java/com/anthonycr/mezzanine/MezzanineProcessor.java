package com.anthonycr.mezzanine;

import com.anthonycr.mezzanine.filter.SupportedElementFilter;
import com.anthonycr.mezzanine.generator.MezzanineGenerator;
import com.anthonycr.mezzanine.map.MezzanineMapper;
import com.anthonycr.mezzanine.source.ElementSource;
import com.anthonycr.mezzanine.source.MezzanineElementSource;
import com.anthonycr.mezzanine.utils.MessagerUtils;
import com.google.auto.service.AutoService;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

@AutoService(Processor.class)
@SupportedAnnotationTypes(value = {"com.anthonycr.mezzanine.FileStream"})
public class MezzanineProcessor extends AbstractProcessor {

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

        MessagerUtils.initialize(processingEnv.getMessager());

        ElementSource mezzanineElementSource = new MezzanineElementSource(roundEnvironment);

        MessagerUtils.reportInfo("Starting Mezzanine processing");

        mezzanineElementSource.createElementStream()
            .filter(SupportedElementFilter.filterForSupportedElements())
            .map(MezzanineMapper.elementToTypeAndFilePair())
            .doOnNext(typeElementFileEntry -> MessagerUtils.reportInfo("Processing file: " + typeElementFileEntry.getValue()))
            .map(MezzanineMapper.fileToStringContents())
            .map(MezzanineGenerator.generateGeneratorTypeSpecs())
            .toList()
            .map(MezzanineGenerator.generateMezzanineTypeSpec())
            .map(MezzanineGenerator.generateJavaFile())
            .flatMapCompletable(MezzanineGenerator.writeFileToDisk(processingEnv.getFiler()))
            .subscribe(() -> MessagerUtils.reportInfo("File successfully processed"));

        return true;
    }
}
