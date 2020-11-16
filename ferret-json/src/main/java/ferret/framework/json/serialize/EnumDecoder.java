package ferret.framework.json.serialize;

import com.jsoniter.JsonIterator;
import com.jsoniter.spi.Decoder;
import com.jsoniter.spi.Slice;
import ferret.framework.api.json.Property;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Kam
 */

public class EnumDecoder implements Decoder {
    private final Map<Slice, Object> enumMap = new HashMap<Slice, Object>();
    private final Class<?> clazz;

    public EnumDecoder(Class<?> clazz) {
        this.clazz = clazz;
        for (Object e : clazz.getEnumConstants()) {
            Property enumValue = getProperty(e);
            if (enumValue == null) continue;

            String value = enumValue.name();
            if (value.isEmpty()) continue;

            enumMap.put(Slice.make(value), e);
        }
    }

    @Override
    public Object decode(JsonIterator iter) throws IOException {
        if (iter.readNull()) {
            return null;
        }
        Slice slice = iter.readStringAsSlice();
        Object e = enumMap.get(slice);
        if (e == null) {
            throw iter.reportError("ferret.framework.json.serialize.EnumDecoder", slice + " is not valid enum for " + clazz);
        }
        return e;
    }

    private Property getProperty(Object e) {
        try {
            Field field = e.getClass().getField(e.toString());
            return field.getDeclaredAnnotation(Property.class);
        } catch (NoSuchFieldException ignored) {
            return null;
        }
    }

}
