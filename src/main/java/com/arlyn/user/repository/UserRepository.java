package com.arlyn.user.repository;

import com.arlyn.common.entity.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import javax.sql.DataSource;
import java.sql.*;

@ApplicationScoped
public class UserRepository {

    @Inject
    DataSource dataSource;

    // CRUD
    // -----------------------------------

    // CREATE
    // CREATE USER
    public Long create(User user) {

        String sql = """
            INSERT INTO users (full_name, email, password_hash, created_at)
            VALUES (?, ?, ?, ?)
            """;

        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {

            ps.setString(1, user.full_name);
            ps.setString(2, user.email);
            ps.setString(3, user.password_hash);
            ps.setTimestamp(
                    4,
                    user.created_at != null
                            ? Timestamp.valueOf(user.created_at)
                            : Timestamp.valueOf(java.time.LocalDateTime.now())
            );

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    user.user_id = keys.getLong(1);
                    return user.user_id;
                }
            }

        return null;

        } catch (Exception e) {
            throw new RuntimeException("Failed to insert user", e);
        }
    }


    // ASSIGN ROLE TO USER
    public void assignRole(long userId, long roleId) {

        String sql = """
            INSERT INTO user_roles (user_id, role_id)
            VALUES (?, ?)
            """;

        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setLong(1, userId);
            ps.setLong(2, roleId);
            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Failed to assign role", e);
        }
    }


    // HELPERS
    // -----------------------------------

    // VALIDATE EMAIL
    public boolean existsByEmail(String email) {

        String sql = "SELECT 1 FROM users WHERE email = ? LIMIT 1";

        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to check username existence", e);
        }
    }


    // FIND USER ROLE BY ID
    public long findRoleIdByName(String roleName) {

        String sql = "SELECT role_id FROM roles WHERE role_name = ?";

        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setString(1, roleName.toUpperCase());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong("role_id");
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        throw new RuntimeException("Role not found");
    }
}