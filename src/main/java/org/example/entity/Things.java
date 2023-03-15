package org.example.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "things")
public class Things {
    @Id
    private String id;
    @Field(name = "thing_id")
    private String thingId;
    private String name;
    private String description;
    //private String location;
    private String category;
    //    private Integer quantity;
//    private String dateStart;
//    private String dateEnd;
    @Indexed
    private String user;

    public Things() {
    }

    public Things(String id, String thingId, String name, String description, String category, String user) {
        this.id = id;
        this.thingId = thingId;
        this.name = name;
        this.description = description;
        this.category = category;
        this.user = user;
    }

//    public Things(Long id, String name, String description, String location, Integer category, Integer quantity, String dateStart, String dateEnd) {
//        this.id = id;
//        this.name = name;
//        this.description = description;
//        this.location = location;
//        this.category = category;
//        this.quantity = quantity;
//        this.dateStart = dateStart;
//        this.dateEnd = dateEnd;
//    }

    public String getThingId() {
        return thingId;
    }

    public void setThingId(String thingId) {
        this.thingId = thingId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //    public String getLocation() {
//        return location;
//    }
//
//    public void setLocation(String location) {
//        this.location = location;
//    }
//
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    //
//    public Integer getQuantity() {
//        return quantity;
//    }
//
//    public void setQuantity(Integer quantity) {
//        this.quantity = quantity;
//    }
//
//    public String getDateStart() {
//        return dateStart;
//    }
//
//    public void setDateStart(String dateStart) {
//        this.dateStart = dateStart;
//    }
//
//    public String getDateEnd() {
//        return dateEnd;
//    }
//
//    public void setDateEnd(String dateEnd) {
//        this.dateEnd = dateEnd;
//    }
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
