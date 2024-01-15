package spring.mvc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spring.mvc.service.IUserService;
import spring.mvc.vo.Response;


@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class UserController {

    private final IUserService iUserService;

    @GetMapping("/")
    public Response getAllUserByPaging(){
        return iUserService.getAllUser();
    }

    @PostMapping("/")
    public Response createUser(){
        return iUserService.createUser();
    }

    @GetMapping("/{id}")
    public Response getUserById(@PathVariable String id){
        return iUserService.getUserById(id);
    }

}
