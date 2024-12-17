package com.banking.bankingmicroservicetask.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Table("bank_account")
public class BankAccount {
    @Id
    private UUID id;

    @Column("account_number")
    private Integer accountNumber;

    @Column("account_holder")
    private String accountHolder;

    @Column("available_funds")
    private Double availableFunds;

    @Column("account_deleted_time")
    private LocalDateTime bankAccountDeletedTime;

    @Column("currency_shortname_id")
    private UUID currencyShortnameId;

    @Column("limit_goods_id")
    private UUID transactionLimitGoodsId;

    @Column("limit_services_id")
    private UUID transactionLimitServicesId;

    @Column("limit_goods")
    private Double limitGoods;

    @Column("limit_services")
    private Double limitServices;
}
