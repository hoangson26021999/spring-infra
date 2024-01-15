package spring.mvc.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import spring.mvc.repository.model.ConfigModel;

@Repository
public interface IDefaultRepository extends MongoRepository<ConfigModel, String> {

}
