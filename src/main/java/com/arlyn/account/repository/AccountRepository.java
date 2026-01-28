package com.arlyn.account.repository;

import com.arlyn.account.entity.Account;
import com.arlyn.common.enums.AccountType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.sql.DataSource;
import java.sql.*;

@ApplicationScoped
public class AccountRepository {

    @Inject
    DataSource dataSource;

    // CREATE ACCOUNT
    public void  createAccount(Account account) {

        String sql = """
            INSERT INTO accounts (user_id, account_number, account_type, balance, status, created_at)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {

            ps.setLong(1, account.user_id);
            ps.setString(2, account.account_number);
            ps.setString(3, account.account_type.toString());
            ps.setBigDecimal(4, account.balance);
            ps.setString(5, account.status.toString());
            ps.setTimestamp(
                    6,
                    account.created_at != null
                            ? Timestamp.valueOf(account.created_at)
                            : Timestamp.valueOf(java.time.LocalDateTime.now())
            );

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    account.account_id = keys.getLong(1);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to insert user", e);
        }
    }

    // HELPERS

    public boolean userHasAccountType(Long userId, AccountType accountType) {
        String sql = "SELECT COUNT(*) FROM accounts WHERE user_id = ? AND account_type = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, userId);
            ps.setString(2, String.valueOf(accountType));

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0; // true if user already has this type
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to check account type for user", e);
        }

        return false;
    }

}
