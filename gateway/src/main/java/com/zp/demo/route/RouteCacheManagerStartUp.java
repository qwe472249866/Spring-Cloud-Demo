package com.zp.demo.route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class RouteCacheManagerStartUp implements ApplicationRunner {


    @Resource
    private CacheManager cacheManager;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("init route from ds");
    }
}
