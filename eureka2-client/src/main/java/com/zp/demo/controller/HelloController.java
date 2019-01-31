package com.zp.demo.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api("HelloController")
@RestController
public class HelloController {

    @GetMapping("/hello")
    public  String hello(@RequestParam(value="name",required = false) String name){
        return  "hello,"+name+"!";
    }


    @GetMapping("/v1/hello")
    public  String helloV1(@RequestParam(value="name",required = false) String name){
        return  "hello,v1";
    }

    @GetMapping("/v2/hello")
    public  String helloV2(@RequestParam(value="name",required = false) String name){
        return  "hello,v2";
    }


    @PostMapping("/hello")
    public  String saveHello(@RequestParam(value="name",required = false) String name){
        return  "hello,"+name+"!";
    }

}
