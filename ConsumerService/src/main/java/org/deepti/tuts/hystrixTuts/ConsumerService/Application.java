package org.deepti.tuts.hystrixTuts.ConsumerService;

import org.deepti.tuts.hystrixTuts.ConsumerService.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
@RestController
public class Application {

    @Autowired
    private ConsumerService consumerService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }

    @RequestMapping("/consumer")
    public String getHello() {
        System.out.println(" consumer end point executed");
        return consumerService.getHello();

    }
}

