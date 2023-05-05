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
public class ThingKafkaListener {

    KafkaTemplate<String, ThingsKafkaDTO> kafkaTemplate;

    @Autowired
    private void setKafkaTemplate(KafkaTemplate<String, ThingsKafkaDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    ThingsService thingsService;

    @Autowired
    public void setThingsRepository(ThingsService thingsService) {
        this.thingsService = thingsService;
    }

    private final ObjectMapper mapper = new ObjectMapper();

    @KafkaListener(topics = "thingChange", groupId = "1")
    public void listenUpdate(String raw) throws JsonProcessingException {

        ThingsKafkaDTO dto = mapper.readValue(raw, ThingsKafkaDTO.class);

        if (dto.getOperation().equalsIgnoreCase("UPDATE")) {
            List<Things> thingsList = new ArrayList<>(thingsService.findByThingId(dto.getId()));
            if (!thingsList.isEmpty()) {
                thingsList.forEach((Things things) -> {
                    things.setName(dto.getName());
                    things.setDescription(dto.getDescription());
                    things.setLocation(dto.getLocation());
                    things.setCategory(dto.getCategory());
                    things.setQuantity(dto.getQuantity());
                    things.setDateEnd(dto.getDateEnd());
                    thingsService.save(things);
                });
            }
        }
    }
}
