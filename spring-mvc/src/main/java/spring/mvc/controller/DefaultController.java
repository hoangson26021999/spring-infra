
package spring.mvc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.mvc.vo.Response;

@RestController
@RequestMapping(value = "/default")
public class DefaultController {
    @GetMapping("/")
    public Response main() {
        return Response.ok();
    }

}