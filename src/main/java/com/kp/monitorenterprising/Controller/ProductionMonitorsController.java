package com.kp.monitorenterprising.Controller;

import com.kp.monitorenterprising.dao.MonitorDAO;
import com.kp.monitorenterprising.dao.OrderDAO;
import com.kp.monitorenterprising.model.Monitor;
import com.kp.monitorenterprising.model.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.util.List;

public class ProductionMonitorsController {

    // теперь таблица заказов, а не мониторов
    @FXML private TableView<Order> monitorTable;
    @FXML private TableColumn<Order, Integer> quantityColumn;
    @FXML private TableColumn<Order, Integer> idColumn;
    @FXML private TableColumn<Order, String> modelColumn;

    private final OrderDAO orderDAO = new OrderDAO();

    private void loadData() {
        List<Order> list = orderDAO.getProcessingOrders();
        ObservableList<Order> data = FXCollections.observableArrayList(list);
        monitorTable.setItems(data);
    }

    @FXML
    public void initialize() {
        // Привязываем колонки к полям Order
        idColumn.setCellValueFactory(
                new javafx.scene.control.cell.PropertyValueFactory<>("orderId"));
        modelColumn.setCellValueFactory(
                new javafx.scene.control.cell.PropertyValueFactory<>("monitorName"));
        quantityColumn.setCellValueFactory(
                new javafx.scene.control.cell.PropertyValueFactory<>("quantity"));

        // Загружаем именно «в процессе работы»
        List<Order> list = orderDAO.getProcessingOrders();
        ObservableList<Order> data = FXCollections.observableArrayList(list);
        monitorTable.setItems(data);
    }

    // В ProductionMonitorsController.java
    @FXML
    void viewDescription() {
        // выбираем объект Order, а не Monitor
        Order sel = monitorTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            new Alert(Alert.AlertType.WARNING, "Пожалуйста, выберите запись.").showAndWait();
            return;
        }

        // вытягиваем из БД описание по monitorId
        Monitor monitor = new MonitorDAO().getById(sel.getMonitorId());
        String desc = (monitor != null && monitor.getDescription() != null)
                ? monitor.getDescription()
                : "Технологическая карта отсутствует.";

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Технологическая карта заказа #" + sel.getOrderId());
        alert.setHeaderText(null);
        alert.setContentText(desc);
        alert.showAndWait();
    }


    @FXML
    void handleClose() {
        ((Stage)monitorTable.getScene().getWindow()).close();
    }
    /** Переводит выбранный заказ в статус «Тестируется» */
    @FXML
    void sendToTesting() {
        Order sel = monitorTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            new Alert(Alert.AlertType.WARNING, "Сначала выберите заказ.").showAndWait();
            return;
        }
        boolean ok = orderDAO.updateOrderStatus(sel.getOrderId(), "Тестируется");
        if (ok) {
            new Alert(Alert.AlertType.INFORMATION,
                    "Заказ переведён в статус «Тестируется».",
                    ButtonType.OK).showAndWait();
            loadData();  // обновляем таблицу
        } else {
            new Alert(Alert.AlertType.ERROR,
                    "Не удалось обновить статус.",
                    ButtonType.OK).showAndWait();
        }
    }
}
