package org.deepti.tuts.hystrixTuts.ConsumerService;

import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import org.deepti.tuts.hystrixTuts.ConsumerService.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


@SpringBootApplication
@RestController
@EnableCircuitBreaker
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

    @RequestMapping("/testasync")
    public String testAsync() throws ExecutionException, InterruptedException {
        System.out.println("START TIME: " + System.currentTimeMillis());
        Future<String> r1  = consumerService.asyncMethod();
        Future<String> r2  = consumerService.asyncMethod();
        Future<String> r3  = consumerService.asyncMethod();
        Future<String> r4  = consumerService.asyncMethod();
        Future<String> r5  = consumerService.asyncMethod();
        while(!r1.isDone() && !r2.isDone() && !r3.isDone() && !r4.isDone() && !r5.isDone()) {
        }
        String s1 = r1.get().toString();
        String s2 =  r2.get().toString();
        String s3 = r3.get().toString();
        String s4 =  r4.get().toString();
        String s5 =  r5.get().toString();

        System.out.println("END TIME: " + System.currentTimeMillis());
        return s1 + "  " + s2;
    }
}

