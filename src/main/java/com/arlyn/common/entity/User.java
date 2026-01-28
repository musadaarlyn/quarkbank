package com.arlyn.common.entity;

import java.time.LocalDateTime;

public class User {
    public Long user_id;
    public String full_name;
    public String email;
    public String password_hash;
    public LocalDateTime created_at;
}
