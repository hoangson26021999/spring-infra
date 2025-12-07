package spring.mvc.service;

public interface IHttpClientService {
   String getSync(String url);
   String postJson(String url, String json);
}
