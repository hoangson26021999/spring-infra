package com.example.springreactive.kafka;


import com.example.springreactive.util.IdGenerator;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventDTO {

    @Builder.Default
    String id = IdGenerator.nextObjectId();
    String eventGroupId;
    String eventType;
    Long callbackTs;
    Map<String, Object> eventInfo;
}
