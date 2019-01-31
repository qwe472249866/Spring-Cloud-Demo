package com.zp.demo.controller;

import com.zp.demo.models.Api;
import com.zp.demo.util.ApiUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zp
 * @Title: ApiInfo
 * @Description: fetch apis from services
 * @date 2019/1/259:58
 */
@RestController
@RequestMapping("/info/discover")
public class ApiInfo {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private WebClient webClient;

    /**
     * using restTemplate with out lb
     */
    @Autowired()
    @Qualifier("restTemplate")
    private RestTemplate restTemplate;

    @GetMapping("/services")
    public Map<String, List<ServiceInstance>> getServices() {
        Map<String, List<ServiceInstance>> infos = new LinkedHashMap<>();
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            List<ServiceInstance> instances = discoveryClient.getInstances(service);
            infos.put(service, instances);
        }
        return infos;
    }

    @GetMapping("/services/apis")
    public Map<String, Api> getInfos() {
        Map<String, Api> apiMap = new LinkedHashMap<>();
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            //todo ignore gateway ?
            if ("gateway".equals(service)) {
                continue;
            }
            List<ServiceInstance> instances = discoveryClient.getInstances(service);
            for (ServiceInstance instance : instances) {
                URI uri = instance.getUri();
                String fetchUrl = uri + "/apis/list";
                Flux<Api> apis = webClient.get().uri(fetchUrl).retrieve().bodyToFlux(Api.class);
                Iterable it = apis.toIterable();
               // it.forEach();
                System.out.println(apis);
//                Mono<List<Api>> apisEntity = webClient.get().uri(fetchUrl).retrieve().
//                        bodyToMono(new ParameterizedTypeReference<List<Api>>() {});
//                System.out.println(apisEntity);
//                List<Api> apis = apisEntity.
//                //reduce by api path_method hash
//                for (Api api : apis) {
//                    String apiHash = null;
//                    try {
//                        apiHash = ApiUtil.genHash(api);
//                    } catch (UnsupportedEncodingException e) {
//                        e.printStackTrace();
//                    }
//                    Api o = apiMap.get(apiHash);
//                    if (o != null) {
//                        o.getUris().add(uri);
//                    } else {
//                        api.getUris().add(uri);
//                        apiMap.put(apiHash, api);
//                    }
//                }

            }
        }
        return apiMap;

    }


    @GetMapping("/services/apiss")
    public Map<String, Api> getInfoss() {
        Map<String, Api> apiMap = new LinkedHashMap<>();
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            //todo ignore gateway ?
            if ("gateway".equals(service)) {
                continue;
            }
            List<ServiceInstance> instances = discoveryClient.getInstances(service);
            for (ServiceInstance instance : instances) {
                URI uri = instance.getUri();
                String fetchUrl = uri + "/apis/list";
                ResponseEntity<List<Api>> apisEntity = restTemplate.exchange(fetchUrl, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<Api>>() {
                        });
                List<Api> apis = apisEntity.getBody();
                //reduce by api path_method hash
                for (Api api : apis) {
                    String apiHash = null;
                    try {
                        apiHash = ApiUtil.genHash(api);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    Api o = apiMap.get(apiHash);
                    if (o != null) {
                        o.getUris().add(uri);
                    } else {
                        api.getUris().add(uri);
                        apiMap.put(apiHash, api);
                    }
                }

            }
        }
        return apiMap;
    }

}
