package simple.memory.cache;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;

@Controller
public class Tester {

    @Get
    public String index() {
        return "hello world!";
    }
}
