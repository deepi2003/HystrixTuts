package org.deepti.tuts.hystrixTuts.ConsumerService.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
public class ConsumerService {

    @Autowired
    private RestTemplate restTemplate;

    public String getHello() {
        URI uri = URI.create("http://localhost:5678/");
        return restTemplate.getForObject(uri, String.class);
    }
}
