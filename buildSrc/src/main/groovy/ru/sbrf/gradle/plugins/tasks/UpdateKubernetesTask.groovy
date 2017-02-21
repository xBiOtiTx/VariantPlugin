package ru.sbrf.gradle.plugins.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

class UpdateKubernetesTask extends DefaultTask {
    @Input
    String deploymentName

    @Input
    String containerName

    @Input
    String imageName

    @TaskAction
    def update() {
        println "$deploymentName - $containerName - $imageName"
    }
}