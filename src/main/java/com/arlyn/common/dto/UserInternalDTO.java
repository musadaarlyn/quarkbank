package com.arlyn.common.dto;

import com.arlyn.common.enums.AccountStatus;
import com.arlyn.common.enums.AccountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UserInternalDTO {

    @NotBlank
    public Long user_id;

    @NotNull
    public AccountType account_type;

    public AccountStatus account_status;
}
