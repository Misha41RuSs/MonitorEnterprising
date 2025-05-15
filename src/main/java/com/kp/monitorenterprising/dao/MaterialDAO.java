// src/main/java/com/kp/monitorenterprising/dao/MaterialDAO.java
package com.kp.monitorenterprising.dao;

import com.kp.monitorenterprising.model.Material;
import com.kp.monitorenterprising.util.DatabaseConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MaterialDAO {

    /** Возвращает все материалы из таблицы */
    public List<Material> getAll() {
        List<Material> materials = new ArrayList<>();
        String sql = "SELECT MaterialID, Name, Unit, PricePerUnit, Stock FROM Materials";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                materials.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return materials;
    }

    /** Возвращает материал по его ID или null, если не найден */
    public Material getById(int materialId) {
        String sql = "SELECT MaterialID, Name, Unit, PricePerUnit, Stock FROM Materials WHERE MaterialID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, materialId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Увеличивает на складе количество материала materialId на qty.
     * @return true, если было обновлено хотя бы 1 строка
     */
    public boolean increaseStock(int materialId, int qty) {
        String sql = "UPDATE Materials SET Stock = Stock + ? WHERE MaterialID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, qty);
            ps.setInt(2, materialId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Попытка списать qty единиц материала с наличия.
     * @return true, если списание прошло (Stock >= qty), false иначе
     */
    public boolean decreaseStock(int materialId, int qty) {
        String checkSql = "SELECT Stock FROM Materials WHERE MaterialID = ?";
        String updateSql = "UPDATE Materials SET Stock = Stock - ? WHERE MaterialID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement checkPs = conn.prepareStatement(checkSql)) {

            checkPs.setInt(1, materialId);
            try (ResultSet rs = checkPs.executeQuery()) {
                if (!rs.next() || rs.getInt("Stock") < qty) {
                    return false;
                }
            }
            try (PreparedStatement updPs = conn.prepareStatement(updateSql)) {
                updPs.setInt(1, qty);
                updPs.setInt(2, materialId);
                return updPs.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Маппинг строки ResultSet в объект Material */
    private Material mapRow(ResultSet rs) throws SQLException {
        return new Material(
                rs.getInt("MaterialID"),
                rs.getString("Name"),
                rs.getString("Unit"),
                rs.getBigDecimal("PricePerUnit"),
                rs.getInt("Stock")
        );
    }
}
