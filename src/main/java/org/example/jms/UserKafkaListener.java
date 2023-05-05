package org.example.jms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entity.Things;
import org.example.service.ThingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserKafkaListener {

    KafkaTemplate<String, UserKafkaDTO> kafkaTemplate;

    @Autowired
    public void setKafkaTemplate(KafkaTemplate<String, UserKafkaDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    ThingsService thingsService;

    @Autowired
    public void setThingsRepository(ThingsService thingsService) {
        this.thingsService = thingsService;
    }

    private final ObjectMapper mapper = new ObjectMapper();

    @KafkaListener(topics = "removeUserList", groupId = "2")
    private void listenRemove(String raw) throws JsonProcessingException {
        UserKafkaDTO dto = mapper.readValue(raw, UserKafkaDTO.class);

        if (dto.getOperation().equalsIgnoreCase("REMOVE")) {
            List<Things> thingsList = new ArrayList<>(thingsService.findByUser(dto.getId()));
            if (!thingsList.isEmpty()) {
                thingsList.forEach((Things things) ->
                        thingsService.delete(things)
                );
            }
        }
    }

}
