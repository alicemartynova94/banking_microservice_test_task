package com.banking.banking_microservice_test_task.dao;

import com.banking.banking_microservice_test_task.entity.CurrencyShortname;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CurrencyShortnameRepository extends JpaRepository<CurrencyShortname, Short> {
}
