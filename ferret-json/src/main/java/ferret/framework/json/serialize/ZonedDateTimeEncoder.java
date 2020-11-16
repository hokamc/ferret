package ferret.framework.json.serialize;

import com.jsoniter.output.JsonStream;
import com.jsoniter.spi.Encoder;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Kam
 */
public class ZonedDateTimeEncoder implements Encoder {
    @Override
    public void encode(Object obj, JsonStream stream) throws IOException {
        stream.write('"');
        stream.writeRaw(((ZonedDateTime) obj).format(DateTimeFormatter.ISO_INSTANT));
        stream.write('"');
    }
}
