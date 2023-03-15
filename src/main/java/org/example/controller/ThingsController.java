package org.example.controller;

import org.bson.types.ObjectId;
import org.example.entity.Things;
import org.example.repository.ThingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value = "/inventory", consumes = MediaType.ALL_VALUE)
public class ThingsController {

    @Autowired
    ThingsRepository thingsRepository;

    @GetMapping("/things_list")
    public ResponseEntity<List<Things>> getAllTutorials(@RequestParam(required = false) int user) {
        try {
            List<Things> tutorials = new ArrayList<>();

            thingsRepository.findByUser(user).forEach(tutorials::add);

            if (tutorials.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(tutorials, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/things_list/{id}")
    public ResponseEntity<HttpStatus> deleteThing(@PathVariable("id") String id) {
        try {
            thingsRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    ThingsService thingsService;
//
//    public void setThingsService(ThingsService thingsService) {
//        this.thingsService = thingsService;
//    }
//
//    @GetMapping(value = "/things_list", produces = "application/json;charset=UTF-8")
//    public ResponseEntity<List<Things>> getAll() {
//        try {
//            List<Things> thingsList = new ArrayList<>();
//
//            thingsService.getAll().forEach(thingsList::add);
//            if (thingsList.isEmpty()) {
//                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            }
//            return new ResponseEntity<>(thingsList, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @DeleteMapping("/things_list/{id}")
//    public ResponseEntity<HttpStatus> deleteThing(@PathVariable("id") int id) {
//        try {
//            thingsService.delete(id);
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @DeleteMapping("/things_list")
//    public ResponseEntity<HttpStatus> deleteAllThings() {
//        try {
//            thingsService.deleteAll();
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

}
