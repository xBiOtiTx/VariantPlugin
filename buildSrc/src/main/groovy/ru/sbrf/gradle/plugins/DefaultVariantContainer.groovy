package ru.sbrf.gradle.plugins

import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.internal.AbstractNamedDomainObjectContainer
import org.gradle.internal.reflect.Instantiator

class DefaultVariantContainer extends AbstractNamedDomainObjectContainer<Variant> implements VariantContainer {
    public DefaultVariantContainer(Class<Variant> type, Instantiator instantiator) {
        super(type, instantiator);
    }

    protected Variant doCreate(String name) {
        println "varinant <$name> created!"
        return getInstantiator().newInstance(DefaultVariant.class, name);
//        return new DefaultVariant(name);
    }
}