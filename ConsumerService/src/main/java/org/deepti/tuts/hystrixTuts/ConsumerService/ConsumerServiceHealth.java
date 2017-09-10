package org.deepti.tuts.hystrixTuts.ConsumerService;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;


@Component
public class ConsumerServiceHealth implements HealthIndicator {

    @Override
    public Health health() {
        // perform some specific health check
        return Health.down().build();
    }

}