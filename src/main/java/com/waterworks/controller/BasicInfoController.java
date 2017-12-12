package com.waterworks.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class BasicInfoController {
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String testHelo(){
        log.info("test Request method Get");
        return "hello spring boot!";
    }
}
