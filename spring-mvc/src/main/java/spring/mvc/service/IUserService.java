package spring.mvc.service;

import spring.mvc.vo.Response;

public interface IUserService {

    Response getAllUser();

    Response getUserById(String id);

    Response createUser();

}
