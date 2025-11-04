package spring.mvc.service;

import spring.mvc.repository.model.ConfigModel;

public interface IDefaultService {
    ConfigModel getConfig(Integer id);
}
