package org.example.repository;

import org.example.entity.Things;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ThingsRepository extends MongoRepository<Things, String> {
    List<Things> findByUser(String user);

    Optional<Things> findByUserAndThingId(String user, String thingId);

    List<Things> findByThingId(String thingId);

    void deleteAllByUser(String user);
}
