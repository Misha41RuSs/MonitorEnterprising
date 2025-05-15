package com.kp.monitorenterprising.dao;

import com.kp.monitorenterprising.model.Monitor;
import com.kp.monitorenterprising.util.DatabaseConnection;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MonitorDAO {

    // В MonitorDAO.java
    public Monitor getById(int monitorId) {
        String sql = "SELECT MonitorID, ModelName, Description "
                + "FROM Monitors WHERE MonitorID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, monitorId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Monitor m = new Monitor(
                            rs.getInt("MonitorID"),
                            rs.getString("ModelName"),
                            0,         // screenSize (неважно)
                            null,      // resolution
                            0,         // refreshRate
                            BigDecimal.ZERO // price
                    );
                    m.setDescription(rs.getString("Description"));
                    return m;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Возвращает список всех мониторов из таблицы Monitors.
     */
    public List<Monitor> getAllMonitors() {
        List<Monitor> monitors = new ArrayList<>();
        String query = ""
                + "SELECT MonitorID, ModelName, ScreenSize, Resolution, RefreshRate, Price "
                + "FROM Monitors";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Monitor m = new Monitor(
                        rs.getInt("MonitorID"),
                        rs.getString("ModelName"),
                        rs.getDouble("ScreenSize"),
                        rs.getString("Resolution"),
                        rs.getInt("RefreshRate"),
                        rs.getBigDecimal("Price")
                );
                monitors.add(m);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return monitors;
    }

    /**
     * Возвращает список мониторов по указанному статусу,
     * включая поля status и description.
     */
    public List<Monitor> getMonitorsByStatus(String status) {
        List<Monitor> monitors = new ArrayList<>();
        String query = ""
                + "SELECT MonitorID, ModelName, ScreenSize, Resolution, RefreshRate, Price, Status, Description "
                + "FROM Monitors "
                + "WHERE Status = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, status);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Monitor m = new Monitor(
                            rs.getInt("MonitorID"),
                            rs.getString("ModelName"),
                            rs.getDouble("ScreenSize"),
                            rs.getString("Resolution"),
                            rs.getInt("RefreshRate"),
                            rs.getBigDecimal("Price"),
                            rs.getString("Status"),
                            rs.getString("Description")
                    );
                    monitors.add(m);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return monitors;
    }
}
