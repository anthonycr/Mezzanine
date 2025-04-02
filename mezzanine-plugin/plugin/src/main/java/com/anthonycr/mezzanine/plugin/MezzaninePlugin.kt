package com.anthonycr.mezzanine.plugin

import com.anthonycr.mezzanine.plugin.task.GenerateMezzanineTask
import com.google.devtools.ksp.gradle.KspAATask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinBaseExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinBasePlugin
import org.jetbrains.kotlin.gradle.tasks.KotlinCompileTool

class MezzaninePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val extension = target.extensions.create("mezzanine", MezzaninePluginExtension::class.java)

        val mezzanineDirectory = target.layout.buildDirectory.dir("mezzanineGenerated")

        val localCoreDependency = target.findProject(":mezzanine:core")
        val localProcessorDependency = target.findProject(":mezzanine:processor")

        target.afterEvaluate {
            target.dependencies.apply {
                add(
                    "implementation",
                    localCoreDependency ?: "$CORE_COORDINATES:$MINIMUM_REQUIRED_VERSION"
                )
                add(
                    "ksp",
                    localProcessorDependency ?: "$PROCESSOR_COORDINATES:$MINIMUM_REQUIRED_VERSION"
                )
            }
        }

        val generateMezzanine =
            target.tasks.register("generateMezzanine", GenerateMezzanineTask::class.java) { task ->
                task.inputFiles.setFrom(extension.files)
                task.relativePath.set(target.layout.projectDirectory.asFile.invariantSeparatorsPath)
                task.outputFiles.set(mezzanineDirectory)
            }

        target.tasks.withType(KotlinCompileTool::class.java) {
            it.dependsOn(generateMezzanine)
        }

        target.tasks.withType(KspAATask::class.java) {
            it.dependsOn(generateMezzanine)
        }

        target.plugins.withType(KotlinBasePlugin::class.java) {
            target.extensions.configure(KotlinBaseExtension::class.java) { kotlinExtension ->
                kotlinExtension.sourceSets.named("main") { sourceSet ->
                    sourceSet.kotlin.srcDir(mezzanineDirectory)
                }
            }
        }
    }

    companion object {
        private const val CORE_COORDINATES = "com.anthonycr.mezzanine:core"
        private const val PROCESSOR_COORDINATES = "com.anthonycr.mezzanine:processor"
        private const val MINIMUM_REQUIRED_VERSION = "2.0.1"
    }
}
