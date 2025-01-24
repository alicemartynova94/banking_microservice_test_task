package org.example;

import com.banking.api.BankAccountOpenFeignClient;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactivefeign.webclient.WebReactiveFeign;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ClientConfig {

  @Autowired
  private DiscoveryClient discoveryClient;

  @Value("${service.name}")
  private String bankingServiceName;

  public String serviceUrl() {
    int retries = 10;
    int delay = 2000;

    for (int i = 0; i < retries; i++) {
      logRegisteredServices();
      List<ServiceInstance> list = discoveryClient.getInstances(bankingServiceName);
      if (list != null && !list.isEmpty()) {
        return list.get(0).getUri().toString();
      }

      try {
        Thread.sleep(delay);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }

    throw new IllegalStateException("Could not find the service: " + bankingServiceName);
  }


  public void logRegisteredServices() {
    List<String> services = discoveryClient.getServices();
    log.debug("Registered services in Eureka: {}", services);
  }

  @Bean
  public BankAccountOpenFeignClient bankAccountOpenFeignClient() {
    return WebReactiveFeign.<BankAccountOpenFeignClient>builder()
        .target(BankAccountOpenFeignClient.class, serviceUrl());
  }
}