package com.arlyn.account.mapper;

import com.arlyn.account.entity.Account;
import com.arlyn.common.dto.UserInternalDTO;
import com.arlyn.common.enums.AccountStatus;
import com.arlyn.common.enums.AccountType;

import java.math.BigDecimal;
import java.util.UUID;

public class AccountMapper {

    // USER INTERNAL DTO -> ACCOUNT ENTITY
    public static Account toEntity(UserInternalDTO user) {

        Account account = new Account();
        account.user_id = user.user_id;
        account.account_type = user.account_type;

        return account;
    }
}
