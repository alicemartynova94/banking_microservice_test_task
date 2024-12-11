package com.banking.bankingmicroservicetask;

import io.r2dbc.spi.ConnectionFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
// EnableR2dbcRepositories ??
@EnableR2dbcRepositories
@RequiredArgsConstructor
public class ReactorConfig {

  private final ConnectionFactory connectionFactory;

  @Bean
  public ReactiveTransactionManager transactionManager() {
    return new R2dbcTransactionManager(connectionFactory);
  }

}
