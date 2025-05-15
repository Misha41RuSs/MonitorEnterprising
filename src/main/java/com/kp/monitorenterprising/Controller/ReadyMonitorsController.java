package com.kp.monitorenterprising.Controller;

import com.kp.monitorenterprising.dao.OrderDAO;
import com.kp.monitorenterprising.model.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;

public class ReadyMonitorsController {

    @FXML private TableView<Order> ordersTable;
    @FXML private TableColumn<Order, Integer> orderIdColumn;
    @FXML private TableColumn<Order, String> modelColumn;
    @FXML private TableColumn<Order, Integer> quantityColumn;
    @FXML private TextField remarkField;

    private final OrderDAO orderDAO = new OrderDAO();

    @FXML
    public void initialize() {
        orderIdColumn.setCellValueFactory(
                new javafx.scene.control.cell.PropertyValueFactory<>("orderId"));
        modelColumn.setCellValueFactory(
                new javafx.scene.control.cell.PropertyValueFactory<>("monitorName"));
        quantityColumn.setCellValueFactory(
                new javafx.scene.control.cell.PropertyValueFactory<>("quantity"));

        loadData();
    }

    private void loadData() {
        List<Order> list = orderDAO.getTestingOrders();
        ObservableList<Order> data = FXCollections.observableArrayList(list);
        ordersTable.setItems(data);
    }

    /** Ставим статус обратно в «В процессе работы» */
    @FXML
    void rejectOrder() {
        Order sel = ordersTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            showAlert("Выберите заказ.");
            return;
        }
        boolean ok = orderDAO.updateOrderStatus(sel.getOrderId(), "В процессе работы");
        if (ok) {
            showAlert("Заказ возвращён на этап «В процессе работы».");
            loadData();
        } else {
            showAlert("Не удалось обновить статус.");
        }
    }

    /** Ставим статус «В процессе упаковки» */
    @FXML
    void acceptOrder() {
        Order sel = ordersTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            showAlert("Выберите заказ.");
            return;
        }
        boolean ok = orderDAO.updateOrderStatus(sel.getOrderId(), "В процессе упаковки");
        if (ok) {
            showAlert("Заказ принят и переведён в «В процессе упаковки».");
            loadData();
        } else {
            showAlert("Не удалось обновить статус.");
        }
    }

    @FXML
    void handleClose() {
        ((Stage)ordersTable.getScene().getWindow()).close();
    }

    private void showAlert(String msg) {
        new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK)
                .showAndWait();
    }
}
