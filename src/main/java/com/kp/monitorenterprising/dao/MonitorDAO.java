package com.kp.monitorenterprising.dao;

import com.kp.monitorenterprising.model.Monitor;
import com.kp.monitorenterprising.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MonitorDAO {
    /**
     * Возвращает список всех мониторов из таблицы Monitors.
     */
    public List<Monitor> getAllMonitors() {
        List<Monitor> monitors = new ArrayList<>();
        String query = "SELECT MonitorID, ModelName, ScreenSize, Resolution, RefreshRate, Price FROM Monitors";

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
}