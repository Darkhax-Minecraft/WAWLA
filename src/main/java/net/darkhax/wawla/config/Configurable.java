package net.darkhax.wawla.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Configurable {

    /**
     * The name for the entry. This defaults to an empty string, which will use the field name.
     *
     * @return The name for the entry.
     */
    String name() default "";

    /**
     * The category for the entry. By default this is general.
     *
     * @return The category for the entry.
     */
    String category();

    /**
     * The description for the configuration entry.
     *
     * @return The description for the entry.
     */
    String description();
}
