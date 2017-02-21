package ru.sbrf.gradle.plugins

import org.gradle.api.Named

interface Variant extends Named  {
    String getClassesTaskName()
}
