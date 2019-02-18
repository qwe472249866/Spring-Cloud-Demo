package com.zp.demo.sys.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.InMemoryRouteDefinitionRepository;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.*;

/**
 * @author zp
 * @Title: DynamicRoute
 * @Description: TODO
 * @date 2019/2/139:16
 */
@RestController
@RequestMapping("/route")
public class DynamicRoute{

    @Autowired
    private  InMemoryRouteDefinitionRepository inMemoryRouteDefinitionRepository;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @GetMapping("/add")
    public Mono addRoute() {
        RouteDefinition definition = new RouteDefinition();
        PredicateDefinition predicateDefinition = new PredicateDefinition();
        predicateDefinition.setName("Path");
        Map<String, String> predicateDefinition_params = new HashMap<>(8);
        predicateDefinition_params.put("pattern", "/hello");
        predicateDefinition.setArgs(predicateDefinition_params);
        List<PredicateDefinition> predicateDefinitions = new ArrayList<>();
        predicateDefinitions.add(predicateDefinition);
        definition.setPredicates(predicateDefinitions);
        URI uri = UriComponentsBuilder.fromUriString("lb://EUREKA-CLIENT-1").build().toUri();
        definition.setUri(uri);
        jdbcTemplate.update("insert into route_definition values (null,?)",JSON.toJSONString(definition));
        Mono<RouteDefinition> mono = Mono.just(definition);
        return inMemoryRouteDefinitionRepository.save(mono).just(definition);
    }


    @GetMapping("/list")
    public  Flux<RouteDefinition> getRoutes(){
        Flux<RouteDefinition> routeDefinitions = inMemoryRouteDefinitionRepository.getRouteDefinitions();
        return routeDefinitions;
    }


    @GetMapping("/delete")
    public  Mono deleteRoutes(){
        Mono<String> mono = Mono.just("uuid");
        return  inMemoryRouteDefinitionRepository.delete(mono);
    }


    public void setInMemoryRouteDefinitionRepository(InMemoryRouteDefinitionRepository inMemoryRouteDefinitionRepository) {
        this.inMemoryRouteDefinitionRepository = inMemoryRouteDefinitionRepository;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
