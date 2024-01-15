package spring.mvc.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import spring.mvc.repository.model.UserModel;

@Repository
public interface IUserRepository extends MongoRepository<UserModel, String>, IUserCustomRepository {

}
