package com.zp.demo.bean;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author zp
 * @Title: WebClientConf
 * @Description: TODO
 * @date 2019/1/3010:47
 */
@Configuration
public class WebClientConf {

    @Bean
    @LoadBalanced
    public WebClient webClient(){
        return  WebClient.create();
    }

    @Bean
    public WebClient.Builder builder() {
        return WebClient.builder();
    }

    @Bean(name = "restTemplate")
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean(name = "lbRestTemplate")
    @LoadBalanced
    public RestTemplate lbRestTemplate() {
        return new RestTemplate();
    }


}
