package spring.mvc.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import spring.mvc.repository.model.UserModel;


public interface IUserRepository extends MongoRepository<UserModel, String>, IUserCustomRepository {

}
