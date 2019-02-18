package com.zp.demo.route;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Component
//public class RouteCacheManagerStartUp implements ApplicationRunner {
/**
 *  不要等到容器初始化完执行,那样看起来启动成功了?
 */

public class RouteCacheManagerStartUp implements InitializingBean {

    /**
     * using default inMemRouteDefRepository as cache
     */
    @Autowired
    private RouteDefinitionRepository inMemoryRouteDefinitionRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void setInMemoryRouteDefinitionRepository(RouteDefinitionRepository inMemoryRouteDefinitionRepository) {
        this.inMemoryRouteDefinitionRepository = inMemoryRouteDefinitionRepository;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //todo init route from db
        jdbcTemplate.query("select * from route_definition",(resultSet -> {
            String route = resultSet.getString("route");
            RouteDefinition routeDefinition = JSON.parseObject(route,RouteDefinition.class);
            inMemoryRouteDefinitionRepository.save(Mono.just(routeDefinition)).subscribe();
        }));
    }
}
