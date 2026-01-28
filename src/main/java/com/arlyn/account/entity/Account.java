package com.arlyn.account.entity;

import com.arlyn.common.enums.AccountStatus;
import com.arlyn.common.enums.AccountType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Account {
    public Long account_id;
    public Long user_id;
    public String account_number;
    public AccountType account_type;
    public BigDecimal balance;
    public AccountStatus status;
    public LocalDateTime created_at;
}
