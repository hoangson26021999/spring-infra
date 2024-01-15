package spring.mvc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import spring.mvc.repository.IUserRepository;
import spring.mvc.service.IUserService;
import spring.mvc.vo.Response;

@Component
@RequiredArgsConstructor
public class IUserServiceImpl implements IUserService {

    private final IUserRepository iUserRepository;

    @Override
    public Response getAllUser() {
        return Response.ok(iUserRepository.findAll());
    }

    @Override
    public Response getUserById(String id) {
        return Response.ok(iUserRepository.findById(id));
    }

    public Response createUser() {
        return Response.ok(iUserRepository.createUser());
    }
}
