package org.example.repository;

import org.bson.types.ObjectId;
import org.example.entity.Things;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ThingsRepository extends MongoRepository<Things, String> {
    List<Things> findByUser(int user);

    @DeleteQuery
    void deleteById(ObjectId objectId);
}
