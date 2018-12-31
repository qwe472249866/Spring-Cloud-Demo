package com.zp.demo.controller;

import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/info")
public class ServerConController implements ApplicationListener<ServletWebServerInitializedEvent> {

    WebServer server;

    @Override
    public void onApplicationEvent(ServletWebServerInitializedEvent servletWebServerInitializedEvent) {
        server = servletWebServerInitializedEvent.getWebServer();
    }

    @GetMapping("/port")
    public int getPort() {
        int port = server.getPort();
        return port;
    }

}
