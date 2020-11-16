package ferret.framework.json;

import com.jsoniter.spi.Binding;
import com.jsoniter.spi.ClassDescriptor;
import com.jsoniter.spi.Config;
import com.jsoniter.spi.Decoder;
import com.jsoniter.spi.Encoder;
import ferret.framework.api.json.Property;
import ferret.framework.json.serialize.EnumDecoder;
import ferret.framework.json.serialize.EnumEncoder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;

/**
 * @author Kam
 */
public class JsonConfig extends Config {

    public static class Builder extends Config.Builder {
        public JsonConfig build() {
            return (JsonConfig) super.build();
        }

        @Override
        protected Config doBuild(String configName) {
            return new JsonConfig(configName, this);
        }

        @Override
        public String toString() {
            return super.toString() + " => ferret.framework.json.JsonConfig{}";
        }
    }

    private JsonConfig(String configName, JsonConfig.Builder builder) {
        super(configName, builder);
    }

    @Override
    public Decoder createDecoder(String cacheKey, Type type) {
        if (type instanceof Class) {
            Class<?> clazz = (Class<?>) type;
            if (clazz.isEnum()) {
                return new EnumDecoder(clazz);
            }
        }

        return null;
    }

    @Override
    public Encoder createEncoder(String cacheKey, Type type) {
        if (type instanceof Class) {
            Class<?> clazz = (Class<?>) type;
            if (clazz.isEnum()) {
                return new EnumEncoder(clazz);
            }
        }

        return null;
    }

    @Override
    public void updateClassDescriptor(ClassDescriptor desc) {
        /// disable setters and getters
        desc.getters = List.of();
        desc.setters = List.of();

        for (Iterator<Binding> iterator = desc.fields.iterator(); iterator.hasNext(); ) {
            Binding binding = iterator.next();
            
            if (!Modifier.isPublic(binding.field.getModifiers())) {
                iterator.remove();
            } else {
                Property property = getProperty(binding.annotations);
                if (property != null) {
                    binding.fromNames = new String[]{property.name()};
                    binding.toNames = new String[]{property.name()};
                } else {
                    iterator.remove();
                }
            }
        }
    }

    protected Property getProperty(Annotation[] annotations) {
        return getAnnotation(annotations, Property.class);
    }
}
