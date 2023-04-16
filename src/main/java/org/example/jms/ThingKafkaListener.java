package org.example.jms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entity.Things;
import org.example.repository.ThingsRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ThingKafkaListener {

    KafkaTemplate<String, ThingsDTO> kafkaTemplate;

    public void setKafkaTemplate(KafkaTemplate<String, ThingsDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    private ThingsRepository thingsRepository;

    public void setThingsRepository(ThingsRepository thingsRepository) {
        this.thingsRepository = thingsRepository;
    }

    private final ObjectMapper mapper = new ObjectMapper();


    @KafkaListener(topics = "thingChange", groupId = "1")
    public void listen(String raw) throws JsonProcessingException {

        ThingsDTO dto = mapper.readValue(raw, ThingsDTO.class);

        if (dto.getOperation().equalsIgnoreCase("UPDATE")) {
            List<Things> thingsList = new ArrayList<>(thingsRepository.findByThingId(dto.getId()));
            if (!thingsList.isEmpty()) {
                thingsList.forEach((Things things) -> {
                    things.setDescription(dto.getDescription());
                    thingsRepository.save(things);
                });
            }
        }
    }

}
