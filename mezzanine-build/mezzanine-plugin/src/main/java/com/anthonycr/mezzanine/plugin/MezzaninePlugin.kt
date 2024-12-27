package com.anthonycr.mezzanine.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinBaseExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinBasePlugin
import org.jetbrains.kotlin.gradle.tasks.KotlinCompileTool

class MezzaninePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val extension = target.extensions.create("mezzanine", MezzaninePluginExtension::class.java)

        val mezzanineDirectory = target.layout.buildDirectory.dir("mezzanineGenerated")

        val generateMezzanine = target.tasks.register("generateMezzanine", GenerateMezzanineTask::class.java) { task ->
            task.inputFiles.setFrom(extension.files)
            task.relativePath.set(target.layout.projectDirectory.asFile.invariantSeparatorsPath)
            task.outputFiles.set(mezzanineDirectory)
        }

        target.tasks.withType(KotlinCompileTool::class.java) {
            it.dependsOn(generateMezzanine)
//            it.outputs.dir(mezzanineDirectory)
        }

        target.plugins.withType(KotlinBasePlugin::class.java) {
            target.extensions.configure(KotlinBaseExtension::class.java) { kotlinExtension ->
                kotlinExtension.sourceSets.named("main") { sourceSet ->
                    sourceSet.kotlin.srcDir(mezzanineDirectory)
                }
            }
        }
    }
}