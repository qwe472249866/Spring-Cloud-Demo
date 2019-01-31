package com.zp.demo.route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.config.PropertiesRouteDefinitionLocator;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.CachingRouteLocator;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.cloud.gateway.support.WeightConfig;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class DbRouteDefinitionRepository implements RouteDefinitionRepository, ApplicationEventPublisherAware {

    @Autowired
    private ApplicationEventPublisher publisher;


    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {

        RouteDefinition routeDefinition_v1 = new RouteDefinition();
        routeDefinition_v1.setId("welcome_v1");
        URI uri_v1 = UriComponentsBuilder.fromUriString("lb://EUREKA-CLIENT-1").build().toUri();
        routeDefinition_v1.setUri(uri_v1);
        //路径参数
        PredicateDefinition pathPredicateDefinition_v1 = new PredicateDefinition();
        Map<String, String> pathPredicateParams_v1 = new HashMap<>(8);
        pathPredicateDefinition_v1.setName("Path");
        pathPredicateParams_v1.put("pattern", "/v1/**");
        pathPredicateDefinition_v1.setArgs(pathPredicateParams_v1);
        //权重参数
        PredicateDefinition weightPredicateDefinition_v1 = new PredicateDefinition();
        Map<String, String> weightPredicateParams_v1 = new HashMap<>(8);
        weightPredicateDefinition_v1.setName("Weight");
        weightPredicateParams_v1.put("group", "eureka_service");
        weightPredicateParams_v1.put("weight", "90");
        weightPredicateDefinition_v1.setArgs(weightPredicateParams_v1);

        RouteDefinition routeDefinition_v2 = new RouteDefinition();
        routeDefinition_v2.setId("welcome_v2");
        URI uri_v2 = UriComponentsBuilder.fromUriString("lb://EUREKA-CLIENT-1").build().toUri();
        routeDefinition_v2.setUri(uri_v2);
        //路径参数
        PredicateDefinition pathPredicateDefinition_v2 = new PredicateDefinition();
        Map<String, String> pathPredicateParams_v2 = new HashMap<>(8);
        pathPredicateDefinition_v2.setName("Path");
        pathPredicateParams_v2.put("pattern", "/v2/**");
        pathPredicateDefinition_v2.setArgs(pathPredicateParams_v2);
        //权重参数
        PredicateDefinition weightPredicateDefinition_v2 = new PredicateDefinition();
        Map<String, String> weightPredicateParams_v2 = new HashMap<>(8);
        weightPredicateDefinition_v2.setName("Weight");
        weightPredicateParams_v2.put("group", "eureka_service");
        weightPredicateParams_v2.put("weight", "10");
        weightPredicateDefinition_v2.setArgs(weightPredicateParams_v2);

        routeDefinition_v1.setPredicates(Arrays.asList(pathPredicateDefinition_v1));
        routeDefinition_v2.setPredicates(Arrays.asList(pathPredicateDefinition_v2));
        RouteDefinition[] definitions = new RouteDefinition[]{routeDefinition_v1, routeDefinition_v2};

        Flux<RouteDefinition> definitionFlux = Flux.fromIterable(Arrays.asList(definitions));
        return definitionFlux;
    }

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return null;
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return null;
    }
}
