package ru.sbrf.gradle.plugins

class DefaultVariant implements Variant {
    final String name

    DefaultVariant(String name) {
        this.name = name
    }

    @Override
    String getClassesTaskName() {
        return ''
    }

    @Override
    String getName() {
        return name
    }
}
