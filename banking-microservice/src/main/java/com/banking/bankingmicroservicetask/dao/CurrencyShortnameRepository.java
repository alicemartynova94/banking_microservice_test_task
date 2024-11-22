package com.banking.bankingmicroservicetask.dao;

import com.banking.bankingmicroservicetask.entity.CurrencyShortname;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface CurrencyShortnameRepository extends JpaRepository<CurrencyShortname, UUID> {
}
