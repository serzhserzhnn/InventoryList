package org.example.service;

import org.example.entity.Things;
import org.example.repository.ThingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ThingsServiceImp implements ThingsService {

    ThingsRepository thingsRepository;

    @Autowired
    public void setThingsRepository(ThingsRepository thingsRepository) {
        this.thingsRepository = thingsRepository;
    }

    @Override
    public List<Things> findByUser(String user) {
        return thingsRepository.findByUser(user);
    }

    @Override
    public Optional<Things> findByUserAndThingId(String user, String thingId) {
        return thingsRepository.findByUserAndThingId(user, thingId);
    }

    @Override
    public List<Things> findByThingId(String thingId) {
        return thingsRepository.findByThingId(thingId);
    }

    @Override
    public void deleteByIdIn(List<String> ids) {
        thingsRepository.deleteByIdIn(ids);
    }

    @Override
    public void deleteAllByUser(String user) {
        thingsRepository.deleteAllByUser(user);
    }

    @Override
    public void save(Things things) {
        thingsRepository.save(things);
    }

    @Override
    public void update(Things things) {
    }

    @Override
    public void deleteById(String id) {
        thingsRepository.deleteById(id);
    }

    @Override
    public void delete(Things things) {
        thingsRepository.delete(things);
    }
}
