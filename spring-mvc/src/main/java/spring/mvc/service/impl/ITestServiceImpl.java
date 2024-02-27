package spring.mvc.service.impl;

import org.springframework.stereotype.Component;

import spring.mvc.service.ITestService;


@Component
public class ITestServiceImpl implements ITestService {
    public int serviceId = 0 ;

    public ITestServiceImpl(){
        this.serviceId = 5;
    }

    @Override
    public int test() {
        return serviceId;
    }
}
