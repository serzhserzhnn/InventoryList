package org.example.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Document(collection = "things")
public class Things {
    @Id
    private String id;
    @Field(name = "thing_id")
    private String thingId;
    private String name;
    private String description;
    private String location;
    private String category;
    private Integer quantity;
    private String dateEnd;
    @Indexed
    private String user;
}
