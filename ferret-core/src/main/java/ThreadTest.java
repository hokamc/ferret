import ferret.framework.json.JSON;
import io.javalin.Javalin;
import io.javalin.plugin.json.JavalinJson;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kam
 */
public class ThreadTest {

    public static void main(String[] args) {
        ThreadLocal<List<Integer>> random = new ThreadLocal<>() {
            @Override
            protected List<Integer> initialValue() {
                return new ArrayList<>();
            }
        };
        Javalin app = Javalin.create().start(7000);

        JavalinJson.setFromJsonMapper(JSON::fromJSON);
        JavalinJson.setToJsonMapper(JSON::toJSON);

        app.get("/", ctx -> {
            random.get().add((int) Thread.currentThread().getId());

            ctx.bodyAsClass(Integer.class);

            ctx.result(random.get().toString());
        });

        app.exception(Exception.class, ((exception, ctx) -> {
            System.out.println(exception.getMessage());
        }));
    }
}
