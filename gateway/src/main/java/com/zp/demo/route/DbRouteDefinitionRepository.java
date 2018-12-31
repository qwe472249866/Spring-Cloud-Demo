package com.zp.demo.route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
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
        RouteDefinition routeDefinition = new RouteDefinition();
        PredicateDefinition predicateDefinition = new PredicateDefinition();
        Map<String, String> predicateParams = new HashMap<>(8);
        routeDefinition.setId("welcome");
        URI uri = UriComponentsBuilder.fromUriString("lb://EUREKA-CLIENT-1").build().toUri();

        routeDefinition.setUri(uri);
        predicateParams.put("pattern", "/**");
        predicateDefinition.setName("Path");
        predicateDefinition.setArgs(predicateParams);
        routeDefinition.setPredicates(Arrays.asList(predicateDefinition));
        Flux<RouteDefinition> definitionFlux = Flux.fromIterable(Arrays.asList(routeDefinition));
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
