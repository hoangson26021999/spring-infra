package spring.mvc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import spring.mvc.repository.IDefaultRepository;
import spring.mvc.repository.model.ConfigModel;
import spring.mvc.service.IDefaultService;

@Component
@RequiredArgsConstructor
public class IDefaultServiceImpl implements IDefaultService {
    private final IDefaultRepository iDefaultRepository;
    @Override
    public ConfigModel getConfig(Integer id) {
        return iDefaultRepository.findById(id).orElse(null);
    }
}
