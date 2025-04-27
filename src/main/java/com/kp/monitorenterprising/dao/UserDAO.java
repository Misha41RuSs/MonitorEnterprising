package com.kp.monitorenterprising.dao;

import com.kp.monitorenterprising.model.User;
import com.kp.monitorenterprising.util.DatabaseConnection;

import java.sql.*;

public class UserDAO {

    public User login(String username, String password) {
        String query = "SELECT u.UserID, u.Username, r.RoleName " +
                "FROM Users u " +
                "JOIN Roles r ON u.RoleID = r.RoleID " +
                "WHERE u.Username = ? AND u.PasswordHash = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("UserID"),
                        rs.getString("Username"),
                        rs.getString("RoleName")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // если пользователь не найден
    }

    public boolean register(String username, String password) {
        String checkQuery = "SELECT UserID FROM Users WHERE Username = ?";
        String insertQuery = "INSERT INTO Users (Username, PasswordHash, RoleID) VALUES (?, ?, 2)"; // 2 — заказчик

        try (Connection conn = DatabaseConnection.getConnection()) {

            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                return false; // уже существует
            }

            PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
            insertStmt.setString(1, username);
            insertStmt.setString(2, password);
            insertStmt.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
