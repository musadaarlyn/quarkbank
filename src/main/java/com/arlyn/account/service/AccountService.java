package com.arlyn.account.service;

import com.arlyn.account.entity.Account;
import com.arlyn.account.mapper.AccountMapper;
import com.arlyn.account.repository.AccountRepository;
import com.arlyn.common.dto.UserInternalDTO;
import com.arlyn.common.enums.AccountStatus;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.math.BigDecimal;
import java.util.UUID;

@ApplicationScoped
public class AccountService {

    @Inject
    AccountRepository accountRepository;

    // CREATE ACCOUNT
    public void createAccount(UserInternalDTO user) {

        // business validation: check if user has existing account
        if(accountRepository.userHasAccountType(user.user_id, user.account_type)) {
            throw new RuntimeException("Account already exists");
        }

        // build entity
        Account account = AccountMapper.toEntity(user);
        // set initial values
        account.account_number = UUID.randomUUID().toString().replace("-", "").substring(0, 12);
        account.balance = BigDecimal.valueOf(0);
        account.status = AccountStatus.ACTIVE;

        // persist
        accountRepository.createAccount(account);

    }
}
