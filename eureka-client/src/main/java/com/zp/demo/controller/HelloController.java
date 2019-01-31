package com.zp.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public  String hello(){
        return  "hello";
    }

    @GetMapping("/v1/hello")
    public  String helloV1(@RequestParam(value="name",required = false) String name){
        return  "hello,v1";
    }

    @GetMapping("/v2/hello")
    public  String helloV2(@RequestParam(value="name",required = false) String name){
        return  "hello,v2";
    }

}
