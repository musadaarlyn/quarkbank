package com.arlyn.account.orchestrator;

import com.arlyn.account.repository.AccountRepository;
import com.arlyn.account.service.AccountService;
import com.arlyn.common.dto.UserInternalDTO;
import com.arlyn.common.enums.AccountStatus;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class AccountOrchestrator {

    @Inject
    AccountService accountService;

    public void createAccount(UserInternalDTO user) {
        accountService.createAccount(user);
    }
}
