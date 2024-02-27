
package spring.mvc.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.mvc.service.IDefaultService;
import spring.mvc.service.ITestService;
import spring.mvc.vo.Response;

@RestController
@RequestMapping(value = "/default")
@RequiredArgsConstructor
public class DefaultController {

    private final ITestService iTestService;
    private final IDefaultService iDefaultService;

    @GetMapping("/")
    public Response main() {
        return Response.ok();
    }

    @GetMapping("/test")
    public int test() {
        return iTestService.test();
    }

}