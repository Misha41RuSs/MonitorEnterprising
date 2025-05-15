package com.kp.monitorenterprising.dao;

import com.kp.monitorenterprising.model.Order;
import com.kp.monitorenterprising.util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {



    public List<Order> getOrdersByUserId(int userId) {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT o.OrderID, m.ModelName, o.Quantity, o.OrderDate, " +
                "(o.Quantity * m.Price) AS TotalPrice, o.Status, u.Username " +
                "FROM Orders o " +
                "JOIN Monitors m ON o.MonitorID = m.MonitorID " +
                "JOIN Users u ON o.UserID = u.UserID " +
                "WHERE o.UserID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Order order = new Order(
                        rs.getInt("OrderID"),
                        rs.getString("ModelName"),
                        rs.getInt("Quantity"),
                        rs.getDate("OrderDate").toLocalDate(),
                        rs.getDouble("TotalPrice"),
                        rs.getString("Status"),
                        rs.getString("Username")
                );
                orders.add(order);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    public List<Order> getAllOrdersWithUsernames() {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT o.OrderID, u.Username, m.ModelName, o.Quantity, o.OrderDate, " +
                "(o.Quantity * m.Price) AS TotalPrice, o.Status " +
                "FROM Orders o " +
                "JOIN Users u ON o.UserID = u.UserID " +
                "JOIN Monitors m ON o.MonitorID = m.MonitorID";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Order order = new Order(
                        rs.getInt("OrderID"),
                        rs.getString("ModelName"),
                        rs.getInt("Quantity"),
                        rs.getDate("OrderDate").toLocalDate(),
                        rs.getDouble("TotalPrice"),
                        rs.getString("Status"),
                        rs.getString("Username")
                );
                orders.add(order);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    public boolean updateOrderStatus(int orderId, String newStatus) {
        String query = "UPDATE Orders SET Status = ? WHERE OrderID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, newStatus);
            stmt.setInt(2, orderId);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Order getOrderById(int orderId) {
        String query = "SELECT o.OrderID, m.ModelName, o.Quantity, o.OrderDate, " +
                "(o.Quantity * m.Price) AS TotalPrice, o.Status, u.Username " +
                "FROM Orders o " +
                "JOIN Monitors m ON o.MonitorID = m.MonitorID " +
                "JOIN Users u ON o.UserID = u.UserID " +
                "WHERE o.OrderID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Order(
                        rs.getInt("OrderID"),
                        rs.getString("ModelName"),
                        rs.getInt("Quantity"),
                        rs.getDate("OrderDate").toLocalDate(),
                        rs.getDouble("TotalPrice"),
                        rs.getString("Status"),
                        rs.getString("Username")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // если заказ не найден
    }

    public boolean createOrder(int userId, int monitorId, int qty) {
        String insert = "INSERT INTO Orders (UserID, MonitorID, Quantity, OrderDate, Status) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(insert)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, monitorId);
            stmt.setInt(3, qty);
            stmt.setDate(4, Date.valueOf(LocalDate.now()));
            stmt.setString(5, "В обработке");

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Order> getOrdersByStatus(String status) {
        List<Order> orders = new ArrayList<>();
        String sql = ""
                + "SELECT o.OrderID, o.MonitorID, m.ModelName, o.Quantity, "
                + "       o.OrderDate, (o.Quantity * m.Price) AS TotalPrice, "
                + "       o.Status, u.Username "
                + "FROM Orders o "
                + "  JOIN Monitors m ON o.MonitorID = m.MonitorID "
                + "  JOIN Users u    ON o.UserID    = u.UserID "
                + "WHERE o.Status = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // создаём Order старым конструктором
                    Order order = new Order(
                            rs.getInt("OrderID"),
                            rs.getString("ModelName"),
                            rs.getInt("Quantity"),
                            rs.getDate("OrderDate").toLocalDate(),
                            rs.getDouble("TotalPrice"),
                            rs.getString("Status"),
                            rs.getString("Username")
                    );
                    // и дополняем monitorId
                    order.setMonitorId(rs.getInt("MonitorID"));
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public List<Order> getProcessingOrders() {
        return getOrdersByStatus("В процессе работы");
    }
    public List<Order> getTestingOrders() {
        return getOrdersByStatus("Тестируется");
    }
}
