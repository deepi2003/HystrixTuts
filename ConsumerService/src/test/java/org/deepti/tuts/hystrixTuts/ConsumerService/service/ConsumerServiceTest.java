package org.deepti.tuts.hystrixTuts.ConsumerService.service;

import org.deepti.tuts.hystrixTuts.ConsumerService.AppConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;


//@RunWith(SpringJUnit4ClassRunner.class)
@RunWith(MockitoJUnitRunner.class)
@EnableCircuitBreaker
@EnableAspectJAutoProxy
//@TestPropertySource("classpath:test.properties")
@ContextConfiguration(classes = {AppConfiguration.class})
public class ConsumerServiceTest {

    private MockRestServiceServer mockServer;

    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    ConsumerService consumerService = new ConsumerService();

    @Before
    public void setUp() {
        this.mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void testGetHelloFallBack() throws Exception {
        mockServer.expect(requestTo("http://localhost:5678/"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withServerError());

         String actual = consumerService.getHello();
         assertEquals("Fallback hello", actual);


    }

}