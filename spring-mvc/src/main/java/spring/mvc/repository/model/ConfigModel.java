package spring.mvc.repository.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfigModel {
    @Id
    String id;

    String config;

    @Field("service_config_name")
    String serviceConfigName;
}
