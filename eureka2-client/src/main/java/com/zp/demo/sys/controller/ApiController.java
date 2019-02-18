package com.zp.demo.sys.controller;

import com.google.common.base.Optional;
import com.google.common.collect.Multimap;
import com.zp.demo.models.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.service.*;
import springfox.documentation.spring.web.DocumentationCache;

import java.util.*;

@RestController
@ApiIgnore
@RequestMapping("/apis")
/**
 *  api info fetch service for gateway
 *  using @ApiIgnore ignore swagger scan this service
 */
public class ApiController {

    @Autowired
    private DocumentationCache documentationCache;

    @GetMapping("/list")
    public List<Api> getApis(@PathVariable(value = "group",required = false) String group){
        String groupName = (String) Optional.fromNullable(group).or("default");
        Documentation documentation = this.documentationCache.documentationByGroup(groupName);
        if(documentation ==null){
            //todo null documentation
            //return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        //api list mapping from documentation
        Multimap<String, ApiListing> apiListings = documentation.getApiListings();
        Iterator it1 = apiListings.values().iterator();
        List<Api> apis = new ArrayList<Api>();
        while(it1.hasNext()) {
            ApiListing apiList = (ApiListing)it1.next();
            Iterator it2 = apiList.getApis().iterator();
            while(it2.hasNext()) {
                ApiDescription apiDescription = (ApiDescription)it2.next();
                String path = apiDescription.getPath();
                List<Operation> operations = apiDescription.getOperations();
                Iterator it3 = operations.iterator();
                while(it3.hasNext()){
                    Operation operation = (Operation) it3.next();
                    String methodName =  operation.getMethod().name();
                    Api api = new Api().path(path).method(methodName);
                    apis.add(api);
                }
            }
        }
        return apis;
    }

}
