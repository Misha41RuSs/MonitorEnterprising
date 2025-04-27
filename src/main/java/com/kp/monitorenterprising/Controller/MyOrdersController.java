package com.kp.monitorenterprising.Controller;

import com.kp.monitorenterprising.dao.OrderDAO;
import com.kp.monitorenterprising.model.Order;
import com.kp.monitorenterprising.util.SessionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class MyOrdersController {

    @FXML
    private TableColumn<Order, String> statusColumn;


    @FXML
    private TableView<Order> ordersTable;

    @FXML
    private TableColumn<Order, Integer> orderIdColumn;

    @FXML
    private TableColumn<Order, String> modelColumn;

    @FXML
    private TableColumn<Order, Integer> quantityColumn;

    @FXML
    private TableColumn<Order, LocalDate> orderDateColumn;

    @FXML
    private TableColumn<Order, Double> totalPriceColumn;

    @FXML
    public void initialize() {
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("monitorName"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        orderDateColumn.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        loadOrders(); // ✅ сразу загружаем после инициализации
    }

    private void loadOrders() {
        var currentUser = SessionManager.getCurrentUser();
        if (currentUser == null) return;

        OrderDAO orderDAO = new OrderDAO();
        List<Order> orders = orderDAO.getOrdersByUserId(currentUser.getId());

        ObservableList<Order> orderList = FXCollections.observableArrayList(orders);
        ordersTable.setItems(orderList);
    }

    @FXML
    void goBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/kp/monitorenterprising/client.fxml"));
        Stage stage = (Stage) ordersTable.getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        stage.setTitle("Меню клиента");
    }
}
