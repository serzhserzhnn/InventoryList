package org.example.service;

import org.example.entity.Things;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ThingsService {
    List<Things> findByUser(String user);

    Optional<Things> findByUserAndThingId(String user, String thingId);

    List<Things> findByThingId(String thingId);

    void deleteByIdIn(List<String> strings);

    void deleteAllByUser(String user);

    ResponseEntity<Object> save(Things things);

    void deleteById(String id);

    void delete(Things things);
}
