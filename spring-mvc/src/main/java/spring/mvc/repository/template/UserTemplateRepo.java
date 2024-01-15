package spring.mvc.repository.template;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import spring.mvc.repository.IUserCustomRepository;
import spring.mvc.repository.model.UserModel;

@RequiredArgsConstructor
public class UserTemplateRepo implements IUserCustomRepository {
    private final MongoTemplate mongoTemplate;

    public UserModel createUser() {
        UserModel n = UserModel.builder().name("guess").age(18).password("guess1").build();
        return mongoTemplate.save(n);
    }
}
