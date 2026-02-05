package com.anthonycr.mezzanine.plugin

import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.api.AndroidBasePlugin
import com.anthonycr.mezzanine.plugin.task.GenerateMezzanineTask
import com.google.devtools.ksp.gradle.KspAATask
import com.google.devtools.ksp.gradle.KspExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.SourceSet
import org.jetbrains.kotlin.gradle.dsl.KotlinBaseExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinBasePlugin
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.tasks.KotlinCompileTool

class MezzaninePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val extension = target.extensions.create("mezzanine", MezzaninePluginExtension::class.java)

        val mezzanineDirectory = target.layout.buildDirectory.dir("mezzanineGenerated")

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

        target.pluginManager.withPlugin("com.google.devtools.ksp") {
            val kspExtension = target.extensions.findByType(KspExtension::class.java)!!

            kspExtension.arg(
                "com.anthonycr.mezzanine.generate_mezzanine",
                extension.generateMezzanine.map { it.toString() }
            )
        }

        target.plugins.withType(AndroidBasePlugin::class.java) {
            target.extensions.configure(CommonExtension::class.java) { androidExtension ->
                androidExtension.sourceSets.named(SourceSet.MAIN_SOURCE_SET_NAME) { sourceSet ->
                    sourceSet.kotlin { androidSourceDirectorySet ->
                        androidSourceDirectorySet.directories.add(
                            mezzanineDirectory.get().toString()
                        )
                    }
                }
            }
        }
        target.plugins.withType(KotlinBasePlugin::class.java) {
            target.extensions.findByType(KotlinBaseExtension::class.java)?.let { kotlinExtension ->
                kotlinExtension.sourceSets.findByName(SourceSet.MAIN_SOURCE_SET_NAME)
                    ?.kotlin?.srcDir(mezzanineDirectory)

                kotlinExtension.sourceSets.findByName(KotlinSourceSet.COMMON_MAIN_SOURCE_SET_NAME)
                    ?.kotlin?.srcDir(mezzanineDirectory)
            }
        }
    }
}
