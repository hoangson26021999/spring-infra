package spring.mvc.service.impl;

import org.springframework.stereotype.Service;
import spring.mvc.service.IHttpClientService;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Service
public class IHttpClientSericeImpl implements IHttpClientService {

    private final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public String getSync(String url) {
        try {
            var req = HttpRequest.newBuilder(URI.create(url)).GET().build();
            var resp = client.send(req, HttpResponse.BodyHandlers.ofString());
            return resp.body();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public String postJson(String url, String json)  {
        try {
            var req = HttpRequest.newBuilder(URI.create(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            var resp = client.send(req, HttpResponse.BodyHandlers.ofString());
            return resp.body();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
