package org.example.jms;

import lombok.Data;

@Data
public class ThingsKafkaDTO {
    private String id;
    private String thingId;
    private String name;
    private String description;
    private String location;
    private String category;
    private Integer quantity;
    private String dateEnd;
    private String operation;
}
