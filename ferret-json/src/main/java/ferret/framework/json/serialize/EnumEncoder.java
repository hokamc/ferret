package ferret.framework.json.serialize;

import com.jsoniter.output.JsonStream;
import com.jsoniter.spi.Encoder;
import ferret.framework.api.json.Property;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class EnumEncoder implements Encoder {
    private final Map<String, String> enumMap = new HashMap<String, String>();

    public EnumEncoder(Class<?> clazz) {
        for (Field field : clazz.getDeclaredFields()) {
            Property enumValue = field.getDeclaredAnnotation(Property.class);
            if (enumValue == null) continue;

            String value = enumValue.name();
            if (value.isEmpty()) continue;

            enumMap.put(field.getName(), value);
        }
    }

    @Override
    public void encode(Object obj, JsonStream stream) throws IOException {
        stream.write('"');
        stream.writeRaw(enumMap.get(obj.toString()));
        stream.write('"');
    }
}
