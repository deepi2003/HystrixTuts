package org.deepti.tuts.hystrixTuts.ConsumerService.service;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
public class ConsumerService {

    @Autowired
    private RestTemplate restTemplate;

    //commandProperties = {@HystrixProperty(name = "requestCache.enabled", value = "false")},
    @HystrixCommand(fallbackMethod = "fallBackGetHello",
            commandProperties = {@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "1")})
    public String getHello() {
        URI uri = URI.create("http://localhost:5678/");
        return restTemplate.getForObject(uri, String.class);

    }

    private String fallBackGetHello() {
        return "Fallback hello";
    }
}
