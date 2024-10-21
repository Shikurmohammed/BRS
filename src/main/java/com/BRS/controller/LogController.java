package com.BRS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.BRS.entity.Log;
import com.BRS.service.LogService;

/*
@RestController: special type of @Controller, 
               : unlike @Controller(which generates html view) it generates json/xml instead of html view   
               : it will make a class RESTFull
 */
//@RestController
/*
 * @RequestMapping: specifies the base url for all endpoints in this class
 */
//@RequestMapping("/api")
/*
 * This class contains methods to create, update, fetch, search & delete Log
 * details
 */
public class LogController {
    /*
     * @Autowired: used to inject a spring boot beans,( classes decorated
     * with @Component, @Service, @Repository,& @Controller) without explicitly
     * instantiating their objects
     * Here , we are
     */
    @Autowired
    private LogService logService;

    @GetMapping("/getLogs")
    public ResponseEntity<List<Log>> getLogList() {
        List<Log> Logs = logService.getAllLog();
        return ResponseEntity.ok(Logs);
    }

    // @PostMapping("/saveLog")
    public ResponseEntity<String> saveLog(@RequestBody Log Log) {

        try {
            Log savedLog = logService.saveLog(Log);
            // System.out.println(Log);
            if (savedLog != null) {
                return new ResponseEntity<>("Log saved Successfully with Log ID:" + Log.getId(),
                        HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Fail:", HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            return new ResponseEntity<>("Error:" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
