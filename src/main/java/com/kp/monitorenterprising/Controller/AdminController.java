package com.kp.monitorenterprising.Controller;

import com.kp.monitorenterprising.dao.OrderDAO;
import com.kp.monitorenterprising.model.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class AdminController {

    @FXML
    private TableView<Order> ordersTable;

    @FXML
    private TableColumn<Order, Integer> orderIdColumn;

    @FXML
    private TableColumn<Order, String> userColumn;

    @FXML
    private TableColumn<Order, String> modelColumn;

    @FXML
    private TableColumn<Order, Integer> quantityColumn;

    @FXML
    private TableColumn<Order, LocalDate> dateColumn;

    @FXML
    private TableColumn<Order, Double> totalPriceColumn;

    @FXML
    private TableColumn<Order, String> statusColumn;

    @FXML
    private ComboBox<String> statusComboBox;

    private final OrderDAO orderDAO = new OrderDAO();

    @FXML
    public void initialize() {
        statusComboBox.setItems(FXCollections.observableArrayList(
                "В обработке", "Подтверждён", "Отменён", "Доставлен"
        ));

        orderIdColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("orderId"));
        userColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("username"));
        modelColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("monitorName"));
        quantityColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("quantity"));
        dateColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("orderDate"));
        totalPriceColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("totalPrice"));
        statusColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("status"));

        loadAllOrders();
    }

    private void loadAllOrders() {
        List<Order> orders = orderDAO.getAllOrdersWithUsernames();
        ObservableList<Order> orderList = FXCollections.observableArrayList(orders);
        ordersTable.setItems(orderList);
    }

    @FXML
    public void changeStatus(ActionEvent event) {
        Order selectedOrder = ordersTable.getSelectionModel().getSelectedItem();
        String newStatus = statusComboBox.getValue();

        if (selectedOrder == null || newStatus == null) {
            showAlert("Ошибка", "Выберите заказ и статус для изменения.");
            return;
        }

        boolean success = orderDAO.updateOrderStatus(selectedOrder.getOrderId(), newStatus);
        if (success) {
            showAlert("Успешно", "Статус заказа обновлён.");
            loadAllOrders();
        } else {
            showAlert("Ошибка", "Не удалось обновить статус.");
        }
    }

    @FXML
    public void handleLogout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/kp/monitorenterprising/mainWindow.fxml"));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        stage.setTitle("Главное меню");
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
