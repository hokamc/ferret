import com.fasterxml.jackson.annotation.JsonProperty;
import io.javalin.Javalin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Kam
 */
public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        Javalin app = Javalin.create();

        app.config.enableDevLogging();

        app.get("/", (ctx) -> {
            ctx.json(MapTest.generate());
        });

        app.post("/", (ctx) -> {
            logger.error("asdasdas");
            ctx.json(ctx.bodyValidator(MapTest.class).get());
        });

        app.start();
    }

    public static class MapTest {
        public static MapTest generate() {
            var result = new MapTest();
            result.integer = 1;
            result.string = "asd";
            result.bigDecimal = BigDecimal.ONE;
            result.aDouble = 1.0;
            result.character = 'A';
            result.zonedDateTime = LocalDateTime.now();
            result.aBoolean = Boolean.FALSE;
            result.integerSet = Set.of(1, 2, 3);
            result.stringIntegerMap = Map.of("a", 1, "b", 2);
            result.stringList = List.of("a", "b");
            return result;
        }

        @JsonProperty("asddsa")
        public Integer integer;
        public String string;
        public BigDecimal bigDecimal;
        public Character character;
        public LocalDateTime zonedDateTime;
        public Boolean aBoolean;
        public Set<Integer> integerSet;
        public Map<String, Integer> stringIntegerMap;
        public List<String> stringList;
        private Double aDouble;
    }
}
