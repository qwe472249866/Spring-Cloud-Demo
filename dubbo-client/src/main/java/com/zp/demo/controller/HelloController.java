package com.zp.demo.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zp.demo.service.HelloService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Reference(
            version = "${hello.service.version}",
            application = "${dubbo.application.id}",
            registry = "${dubbo.registry.id}"
    )
    private HelloService helloService;

    @GetMapping("hello/{name}")
    public String sayHello(@PathVariable String name) {
        return helloService.sayHello(name);
    }

}
