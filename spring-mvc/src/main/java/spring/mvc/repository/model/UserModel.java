package spring.mvc.repository.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;


@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document("user")
@Builder
public class UserModel {

    private String id;

    private String password;
    private String name;
    private int age;
}
