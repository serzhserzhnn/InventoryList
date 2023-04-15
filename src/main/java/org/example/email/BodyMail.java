package org.example.email;

import org.example.entity.Things;
import org.example.repository.ThingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BodyMail {
    String listThings;

    ThingsRepository thingsRepository;

    @Autowired
    public void setThingsRepository(ThingsRepository thingsRepository) {
        this.thingsRepository = thingsRepository;
    }

    public String sendList(String userId) {

        List<Things> things = new ArrayList<>(thingsRepository.findByUser(userId));

        if (things.isEmpty()) {
            return "List of User is empty";
        }

        listThings = "<h3>List Things</h3> \n" +
                "<table bordercolor=\"grey\" border=\"2\" width=\"100%\">" +
                "<thead>" +
                "<tr>" +
                "<th scope=\"col\">id</th>" +
                "<th scope=\"col\">thingId</th>" +
                "<th scope=\"col\">name</th>" +
                "<th scope=\"col\">description</th>" +
                "<th scope=\"col\">category</th>" +
                "<th scope=\"col\">user</th>" +
                "</tr>" +
                "</thead>" +
                "<tbody>";

        things.forEach(thing -> listThings += "<tr>" +
                "<td>" + thing.getId() + "</td>" +
                "<td>" + thing.getThingId() + "</td>" +
                "<td>" + thing.getName() + "</td>" +
                "<td>" + thing.getDescription() + "</td>" +
                "<td>" + thing.getCategory() + "</td>" +
                "<td>" + thing.getUser() + "</td>" +
                "</tr>"
        );

        listThings += "</tbody>" +
                "</table>";

        return listThings;
    }
}
