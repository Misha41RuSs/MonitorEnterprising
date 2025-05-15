// src/main/java/com/kp/monitorenterprising/dao/MonitorMaterialsDAO.java
package com.kp.monitorenterprising.dao;

import com.kp.monitorenterprising.model.MonitorMaterial;
import com.kp.monitorenterprising.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MonitorMaterialsDAO {

    /**
     * Возвращает список записей MonitorMaterials для заданного MonitorID
     */
    public List<MonitorMaterial> getByMonitorId(int monitorId) {
        List<MonitorMaterial> list = new ArrayList<>();
        String sql = "SELECT MonitorID, MaterialID, Quantity " +
                "FROM MonitorMaterials " +
                "WHERE MonitorID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, monitorId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Возвращает все записи MonitorMaterials
     */
    public List<MonitorMaterial> getAll() {
        List<MonitorMaterial> list = new ArrayList<>();
        String sql = "SELECT MonitorID, MaterialID, Quantity FROM MonitorMaterials";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Добавляет новую запись MonitorMaterials
     */
    public boolean create(int monitorId, int materialId, int quantity) {
        String sql = "INSERT INTO MonitorMaterials (MonitorID, MaterialID, Quantity) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, monitorId);
            ps.setInt(2, materialId);
            ps.setInt(3, quantity);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private MonitorMaterial mapRow(ResultSet rs) throws SQLException {
        return new MonitorMaterial(
                rs.getInt("MonitorID"),
                rs.getInt("MaterialID"),
                rs.getInt("Quantity")
        );
    }
}
