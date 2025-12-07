package spring.mvc.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spring.mvc.service.IHttpClientService;

@RestController
@RequiredArgsConstructor
public class HttpClientController {

    private final IHttpClientService iHttpClientService;

    @GetMapping("/get")
    public String getPost(@RequestParam("url") String url) {
        return iHttpClientService.getSync(url);
    }

    @PostMapping("/post")
    public String createPost(@RequestParam("url") String url, @RequestBody String data) {
        return iHttpClientService.postJson(url , data);
    }


}
