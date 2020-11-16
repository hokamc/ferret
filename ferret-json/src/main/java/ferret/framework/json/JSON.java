package ferret.framework.json;

import com.jsoniter.JsonIterator;
import com.jsoniter.output.JsonStream;
import com.jsoniter.spi.JsoniterSpi;
import ferret.framework.json.serialize.ZonedDateTimeDecoder;
import ferret.framework.json.serialize.ZonedDateTimeEncoder;

import java.time.ZonedDateTime;

/**
 * @author Kam
 */

/// only serialize public fields with @property
public class JSON {
    static {
        JsoniterSpi.setDefaultConfig(new JsonConfig.Builder().build());
        JsoniterSpi.registerTypeEncoder(ZonedDateTime.class, new ZonedDateTimeEncoder());
        JsoniterSpi.registerTypeDecoder(ZonedDateTime.class, new ZonedDateTimeDecoder());
    }

    public static <T> T fromJSON(String json, Class<T> instanceClass) {
        return JsonIterator.deserialize(json, instanceClass);
    }

    public static String toJSON(Object instance) {
        return JsonStream.serialize(instance);
    }
}
