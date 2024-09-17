package com.banking.banking_microservice_test_task.dao;

import com.banking.banking_microservice_test_task.entity.Limit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LimitRepository extends JpaRepository<Limit, UUID> {
}
