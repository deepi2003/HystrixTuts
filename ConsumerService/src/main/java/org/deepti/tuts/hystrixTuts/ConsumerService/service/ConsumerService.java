package org.deepti.tuts.hystrixTuts.ConsumerService.service;


import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandMetrics;
import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.HystrixThreadPoolMetrics;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.sql.Time;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Service
public class ConsumerService {

    @Autowired
    private RestTemplate restTemplate;

    //commandProperties = {@HystrixProperty(name = "requestCache.enabled", value = "false")},
    @HystrixCommand(
            fallbackMethod = "fallBackGetHello",
            commandKey = "test",
            threadPoolKey = "test",
            commandProperties = {
                    @HystrixProperty(name = "requestCache.enabled", value = "false")
            },
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "30")
            })
    public String getHello() {
        System.out.println(" COmmand metrics----------------  "+ HystrixCommandMetrics.getInstance(getCommandKey()).getProperties().requestCacheEnabled().get());
        System.out.println(" thread metrics----------------  "+ HystrixThreadPoolMetrics.getInstance(getThreadPoolKey()).getProperties().coreSize().get());
        URI uri = URI.create("http://localhost:5678/");
        return restTemplate.getForObject(uri, String.class);

    }

    private String fallBackGetHello() {
        return "Fallback hello";
    }

    public String getGoogle() {
        String result;
        String httpResult = restTemplate.getForObject("http://google.com",
                String.class);
        result = "Message SUCCESS result: " + httpResult;

        return result;
    }

    private static HystrixCommandKey getCommandKey() {
        return HystrixCommandKey.Factory.asKey("test");
    }

    private static HystrixThreadPoolKey getThreadPoolKey() {
        return HystrixThreadPoolKey.Factory.asKey("test");
    }


    @HystrixCommand(fallbackMethod = "asyncFallback")
    public Future<String> asyncMethod() {
        return new AsyncResult<String>() {
            @Override
            public String invoke() {
                System.out.println(" Start invoke");
                URI uri = URI.create("http://localhost:5678/async");
                String res =  restTemplate.getForObject(uri, String.class);
                System.out.println(" invoke done");
                return res;
            }
        };
    }

    private String asyncFallback() {
        return "Async Fallback";
    }
}
