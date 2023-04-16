package org.example.controller;

import org.example.email.BodyMail;
import org.example.email.SendMail;
import org.example.entity.Things;
import org.example.repository.ThingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value = "/inventory", consumes = MediaType.ALL_VALUE)
public class ThingsController {

    ThingsRepository thingsRepository;

    @Autowired
    private void setThingsRepository(ThingsRepository thingsRepository) {
        this.thingsRepository = thingsRepository;
    }

    SendMail sendMail;

    @Autowired
    private void setSendMail(SendMail sendMail) {
        this.sendMail = sendMail;
    }

    BodyMail bodyMail;

    @Autowired
    private void setBodyMail(BodyMail bodyMail) {
        this.bodyMail = bodyMail;
    }

    @GetMapping("/things_list")
    public ResponseEntity<List<Things>> getAllThings(@RequestParam(required = false) String user) {
        try {
            List<Things> things = new ArrayList<>();

            things.addAll(thingsRepository.findByUser(user));

            if (things.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(things, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add_thing")
    public ResponseEntity<Things> create(@Valid @RequestBody Things things) {
        try {
            Optional<Things> check = thingsRepository.findByUserAndThingId(things.getUser(), things.getThingId());
            if (!check.isPresent()) {
                thingsRepository.save(things);
                return new ResponseEntity<>(HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
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

    @DeleteMapping("/remove_things_list/{user}")
    public ResponseEntity<HttpStatus> deleteAllThings(@PathVariable("user") String user) {
        try {
            thingsRepository.deleteAllByUser(user);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/things_list/sendmail")
    public void sendMail(@RequestParam(required = false) String user) {
        try {
            String body = bodyMail.sendList(user);

            sendMail.Send("Test", body);
        } catch (Exception e) {
            e.getMessage();
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
}
