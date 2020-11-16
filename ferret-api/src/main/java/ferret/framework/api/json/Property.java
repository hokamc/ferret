package ferret.framework.api.json;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Kam
 */

@Target(FIELD)
@Retention(RUNTIME)
public @interface Property {
    String name();
}
