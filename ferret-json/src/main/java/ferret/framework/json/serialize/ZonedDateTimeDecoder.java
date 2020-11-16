package ferret.framework.json.serialize;

import com.jsoniter.JsonIterator;
import com.jsoniter.spi.Decoder;

import java.io.IOException;
import java.time.ZonedDateTime;

/**
 * @author Kam
 */
public class ZonedDateTimeDecoder implements Decoder {
    @Override
    public Object decode(JsonIterator iter) throws IOException {
        return ZonedDateTime.parse(iter.readString());
    }
}
