package com.arlyn.user.mapper;

import com.arlyn.common.entity.User;
import com.arlyn.user.dto.RegisterRequestDTO;
import com.arlyn.common.dto.UserInternalDTO;
import com.arlyn.user.dto.UserResponseDTO;

public class UserMapper {

    // USER ENTITY -> INTERNAL DTO
    public static UserInternalDTO toInternalDTO(RegisterRequestDTO dto, User user) {
        UserInternalDTO internalDTO = new UserInternalDTO();
        internalDTO.user_id = user.user_id;
        internalDTO.account_type = dto.account_type;

        return internalDTO;
    }

    // USER ENTITY -> DTO
    public static UserResponseDTO toDTO(User user) {

        UserResponseDTO dto = new UserResponseDTO();
        dto.email = user.email;
        dto.fullName = user.full_name;
        dto.createdAt = user.created_at;

        return dto;
    }

    // USER DTO -> ENTITY
    public static User toEntity(RegisterRequestDTO dto) {

        User user = new User();
        user.email = dto.email;
        user.full_name = dto.fullName;
        user.password_hash = dto.password;

        return user;
    }

}
