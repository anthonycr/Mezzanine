package com.anthonycr.mezzanine.plugin

import org.gradle.api.Project
import org.gradle.api.file.ConfigurableFileCollection
import javax.inject.Inject

abstract class MezzaninePluginExtension @Inject constructor(project: Project) {
    /**
     * The collection of files that will be referenced by `@FileStream` annotated classes.
     */
    val files: ConfigurableFileCollection = project.objects.fileCollection()
}
