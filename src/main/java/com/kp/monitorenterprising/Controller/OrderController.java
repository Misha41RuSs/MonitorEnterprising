package com.kp.monitorenterprising.Controller;

import com.kp.monitorenterprising.model.Monitor;
import com.kp.monitorenterprising.util.DatabaseConnection;
import com.kp.monitorenterprising.util.SessionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;

public class OrderController {

    @FXML
    private TableView<Monitor> monitorTable;
    @FXML
    private TableColumn<Monitor, String> modelColumn;
    @FXML
    private TableColumn<Monitor, Double> sizeColumn;
    @FXML
    private TableColumn<Monitor, String> resolutionColumn;
    @FXML
    private TableColumn<Monitor, Integer> refreshColumn;
    @FXML
    private TableColumn<Monitor, BigDecimal> priceColumn;
    @FXML
    private TextField quantityField;

    private ObservableList<Monitor> monitors = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        modelColumn.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getModelName()));
        sizeColumn.setCellValueFactory(cell -> new javafx.beans.property.SimpleObjectProperty<>(cell.getValue().getScreenSize()));
        resolutionColumn.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getResolution()));
        refreshColumn.setCellValueFactory(cell -> new javafx.beans.property.SimpleIntegerProperty(cell.getValue().getRefreshRate()).asObject());
        priceColumn.setCellValueFactory(cell -> new javafx.beans.property.SimpleObjectProperty<>(cell.getValue().getPrice()));

        loadMonitorsFromDatabase();
    }

    private void loadMonitorsFromDatabase() {
        String query = "SELECT * FROM Monitors";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                monitors.add(new Monitor(
                        rs.getInt("MonitorID"),
                        rs.getString("ModelName"),
                        rs.getDouble("ScreenSize"),
                        rs.getString("Resolution"),
                        rs.getInt("RefreshRate"),
                        rs.getBigDecimal("Price")
                ));
            }

            monitorTable.setItems(monitors);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void submitOrder() {
        Monitor selected = monitorTable.getSelectionModel().getSelectedItem();
        String quantityText = quantityField.getText().trim();

        if (selected == null || quantityText.isEmpty()) {
            showAlert("Ошибка", "Выберите монитор и введите количество.");
            return;
        }

        try {
            int quantity = Integer.parseInt(quantityText);
            if (quantity <= 0) throw new NumberFormatException();

            int userId = SessionManager.getCurrentUser().getId(); // ✅ берём ID юзера

            String insert = "INSERT INTO Orders (UserID, MonitorID, Quantity, OrderDate) VALUES (?, ?, ?, ?)";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(insert)) {

                stmt.setInt(1, userId);
                stmt.setInt(2, selected.getId());
                stmt.setInt(3, quantity);
                stmt.setDate(4, Date.valueOf(LocalDate.now()));

                stmt.executeUpdate();

                BigDecimal total = selected.getPrice().multiply(BigDecimal.valueOf(quantity));
                showAlert("Успешно", "Заказ оформлен. Сумма: " + total + " ₽");

            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Ошибка", "Ошибка при оформлении заказа.");
            }

        } catch (NumberFormatException e) {
            showAlert("Ошибка", "Введите корректное количество.");
        }
    }

    @FXML
    void goBack() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/kp/monitorenterprising/client.fxml"));
        Stage stage = (Stage) quantityField.getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        stage.setTitle("Панель заказчика");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
