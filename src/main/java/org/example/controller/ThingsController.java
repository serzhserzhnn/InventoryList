package org.example.controller;

import org.example.dto.ThingsDTO;
import org.example.email.BodyMail;
import org.example.email.SendMail;
import org.example.entity.Things;
import org.example.service.ThingsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/inventory_list", consumes = MediaType.ALL_VALUE)
public class ThingsController {

    InputStream inputStream;

    ThingsService thingsService;

    @Autowired
    private void setThingsService(ThingsService thingsService) {
        this.thingsService = thingsService;
    }

    ModelMapper modelMapper;

    @Autowired
    private void setMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
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
            things.addAll(thingsService.findByUser(user));

            if (things.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(things, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add_thing")
    public ResponseEntity<Things> create(@Valid @RequestBody ThingsDTO thingsDTO) {
        try {
            Things things = convertToEntity(thingsDTO);
            Optional<Things> check = thingsService.findByUserAndThingId(things.getUser(), things.getThingId());
            if (!check.isPresent()) {
                thingsService.save(things);
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
            thingsService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/remove_things_list/{user}")
    public ResponseEntity<HttpStatus> deleteAllThings(@PathVariable("user") String user) {
        try {
            thingsService.deleteAllByUser(user);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/remove_things_selected")
    public ResponseEntity<HttpStatus> deleteAllSelectedThings(@RequestBody List<String> things) {
        try {
            List<String> thingsSelect = new ArrayList<>(things);
            thingsService.deleteByIdIn(thingsSelect);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/things_list/sendmail/{user}")
    public void sendMail(@PathVariable("user") String user,
                         @RequestParam(required = false) String email) {
        try {
            Properties prop = new Properties();
            String propFileName = "config.properties";
            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
            prop.load(inputStream);

            String fromEmail = prop.getProperty("email");
            String password = prop.getProperty("password");

            String body = bodyMail.sendList(user);
            sendMail.Send(fromEmail, password, email, "Test", body);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    private ThingsDTO convertToDto(Things things) {
        return modelMapper.map(things, ThingsDTO.class);
    }

    private Things convertToEntity(ThingsDTO thingsDTO) {
        return modelMapper.map(thingsDTO, Things.class);
    }
}
