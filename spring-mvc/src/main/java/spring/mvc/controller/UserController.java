package spring.mvc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.mvc.service.IUserService;
import spring.mvc.vo.Response;


@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class UserController {

    private final IUserService iUserService;

    @GetMapping("/")
    public Response getAllUserByPaging(){
        return Response.ok();
    }

}
