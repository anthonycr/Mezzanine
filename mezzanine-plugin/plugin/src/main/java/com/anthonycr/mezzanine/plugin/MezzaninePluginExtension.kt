package com.anthonycr.mezzanine.plugin

import org.gradle.api.Project
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.provider.Property
import javax.inject.Inject

abstract class MezzaninePluginExtension @Inject constructor(project: Project) {
    /**
     * The collection of files that will be referenced by `@FileStream` annotated classes.
     */
    val files: ConfigurableFileCollection = project.objects.fileCollection()

    /**
     * `true` if Mezzanine should generate the `mezzanine` function in this module, `false`
     * otherwise. Note: You can only have one module generate a mezzanine function, the rest of your
     * modules can only generate the file readers. For single module projects, you don't need this,
     * for multi-module projects, set this to true for your top level module.
     */
    val generateMezzanine: Property<Boolean> = project.objects.property(Boolean::class.java).apply {
        convention(false)
    }
}
