package ru.sbrf.gradle.plugins

import org.gradle.api.Project
import org.gradle.api.Plugin
import org.gradle.api.distribution.Distribution
import org.gradle.api.distribution.DistributionContainer
import org.gradle.api.internal.jvm.ClassDirectoryBinaryNamingScheme
import org.gradle.api.plugins.ApplicationPlugin
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.plugins.JavaPluginConvention
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.internal.reflect.Instantiator
import org.gradle.jvm.tasks.Jar
import org.gradle.language.base.plugins.LifecycleBasePlugin

import javax.inject.Inject

class VariantPlugin implements Plugin<Project> {
    private Project mProject
    private Instantiator mInstantiator
    private ClassDirectoryBinaryNamingScheme mNamingScheme

    public static final String TEST_COMPILE_CLASSPATH_CONFIGURATION_NAME = "testCompileClasspath";
    public static final String TEST_RUNTIME_CLASSPATH_CONFIGURATION_NAME = "testRuntimeClasspath";

    @Inject
    public VariantPlugin(Instantiator instantiator) {
        mInstantiator = instantiator;
        mNamingScheme = new ClassDirectoryBinaryNamingScheme("variant")
    }

    public String getTaskName(String verb, String target) {
        return mNamingScheme.getTaskName(verb, target);
    }

    void apply(Project project) {
        mProject = project;
        mProject.getPluginManager().apply(JavaPlugin.class);
        // mProject.getPluginManager().apply(DistributionPlugin.class);
        mProject.getPluginManager().apply(ApplicationPlugin.class);

        final JavaPluginConvention java = (JavaPluginConvention) project.getConvention().getPlugins().get("java");
        final SourceSetContainer sourceSets = java.getSourceSets()
        final DistributionContainer distributions = (DistributionContainer) project.getExtensions().getByName("distributions");
        final VariantContainer variants = project.getExtensions().create("variants", DefaultVariantContainer.class, Variant.class, mInstantiator);

        final SourceSet main = sourceSets.getByName('main')
        variants.all {
            final SourceSet sourceSet = sourceSets.create(it.name)
            addJarTask(sourceSet)

            final SourceSet sourceSetTest = sourceSets.create(it.name + 'Test')
            // test.setCompileClasspath(project.files(main.getOutput(), project.getConfigurations().getByName(TEST_COMPILE_CLASSPATH_CONFIGURATION_NAME)));
            // test.setRuntimeClasspath(project.files(test.getOutput(), main.getOutput(), project.getConfigurations().getByName(TEST_RUNTIME_CLASSPATH_CONFIGURATION_NAME)));

            final Distribution distribution = distributions.create(it.name)
        }

//        distributions.all {
//            println "distribution: " + it.name
//        }
//        sourceSets.all {
//            println "sourceSet: " + it.name
//        }
//        sourceSets.create("super")
//        distributions.create("super")
//        println "variant jar 1: " + getTaskName(null, "jar")
//        println "variant jar 2: " + getTaskName("jar", null)
//        println "variant jar 3: " + getTaskName("jar", "jar")
//        mProject.getPluginManager().apply(BasePlugin.class);
//        mProject.getPluginManager().apply(ReportingBasePlugin.class);
//        mProject.getPluginManager().apply(LanguageBasePlugin.class);
//        mProject.getPluginManager().apply(BinaryBasePlugin.class);
//        mProject.getPluginManager().apply(JavaBasePlugin.class);
        // JavaPluginConvention javaConvention = project.getConvention().getPlugin(JavaPluginConvention.class);
        // SourceSet main = javaConvention.getSourceSets().create("supermain");
//        def variants = project.container(Variant)
//        project.extensions.variants = variants
//        println "***"
//        variants.each {
//            println it
//        }
//        println "***"
    }

    private void addJarTask(SourceSet sourceSet) {
        Jar jar = mProject.getTasks().create(sourceSet.getJarTaskName(), Jar.class)
        jar.setGroup(LifecycleBasePlugin.BUILD_GROUP)
    }
}

