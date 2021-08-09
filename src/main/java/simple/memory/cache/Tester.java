package simple.memory.cache;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.HttpRequest;

import javax.inject.Inject;

@Controller
public class Tester {

    @Inject
    @Client
    HttpClient client;

    @Get
    public String index() {
        return client.toBlocking()
                .retrieve(
                        HttpRequest.GET("https://api.pexels.com/v1/search?query=nature")
                        .header("Authorization", "<<your auth key>>")
                        );
    }
}
