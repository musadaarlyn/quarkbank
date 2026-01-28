package com.arlyn.user.service;

import com.arlyn.common.entity.User;
import com.arlyn.common.enums.UserType;
import com.arlyn.common.dto.UserInternalDTO;
import com.arlyn.user.repository.UserRepository;
import com.arlyn.user.dto.RegisterRequestDTO;
import com.arlyn.user.mapper.UserMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import io.quarkus.elytron.security.common.BcryptUtil;

import java.time.LocalDateTime;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository userRepository;

    // CRUD
    // -----------------------------------

    // CREATE USER (TYPE "USER")
    public UserInternalDTO create(RegisterRequestDTO dto) {

        // Business validation
        if (userRepository.existsByEmail(dto.email)) {
            throw new BadRequestException("Email already exists");
        }

        // Hash password
        dto.password = BcryptUtil.bcryptHash(dto.password);

        // Build entity
        User user = UserMapper.toEntity(dto);
        user.created_at = LocalDateTime.now();

        // Persist
        user.user_id = userRepository.create(user);

        if(user.user_id == null) {
            throw new BadRequestException("User not created");
        }

        // assign default USER role
        long roleId = userRepository.findRoleIdByName(UserType.USER.name());
        userRepository.assignRole(user.user_id, roleId);

        // Map to response DTO
        return UserMapper.toInternalDTO(dto, user);
    }
}
