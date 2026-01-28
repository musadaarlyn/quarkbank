package com.arlyn.transaction.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
    Long transaction_id;
    Long account_id;
    String transaction_type;
    BigDecimal amount;
    LocalDateTime transaction_time;
    String description;
}
