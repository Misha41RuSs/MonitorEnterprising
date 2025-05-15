package com.kp.monitorenterprising.Controller;

import com.kp.monitorenterprising.dao.MonitorDAO;
import com.kp.monitorenterprising.dao.OrderDAO;
import com.kp.monitorenterprising.model.Monitor;
import com.kp.monitorenterprising.model.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;
import java.util.Random;

public class SendToClientController {

    @FXML private TableView<Order>   ordersTable;
    @FXML private TableColumn<Order, Integer> orderIdColumn;
    @FXML private TableColumn<Order, String>  modelColumn;
    @FXML private TableColumn<Order, Integer> quantityColumn;

    private final OrderDAO orderDAO = new OrderDAO();
    private final Random random = new Random();

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
        List<Order> list = orderDAO.getOrdersByStatus("В процессе упаковки");
        ObservableList<Order> data = FXCollections.observableArrayList(list);
        ordersTable.setItems(data);
    }

    /** Генерирует 4-значный трек-номер и обновляет статус заказа */
    @FXML
    void sendToClient() {
        Order sel = ordersTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            alert("Пожалуйста, выберите заказ.");
            return;
        }
        int code = 1000 + random.nextInt(9000);
        String status = "Трек-номер " + code;
        if (orderDAO.updateOrderStatus(sel.getOrderId(), status)) {
            alert("Установлен статус: " + status);
            loadData();
        } else {
            alert("Не удалось обновить статус.");
        }
    }

    /** Показывает сгенерированную документацию для заказа */
    @FXML
    void showDocumentation() {
        Order sel = ordersTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            alert("Пожалуйста, выберите заказ.");
            return;
        }

        // 1) Сначала вытаскиваем монитор по ID из Order
        Monitor monitor = new MonitorDAO().getById(sel.getMonitorId());
        String desc = (monitor != null && monitor.getDescription() != null)
                ? monitor.getDescription()
                : "Описание отсутствует.";

        // 2) Генерим номер гарантийного талона
        int guarantee = 100 + random.nextInt(900);

        // 3) Составляем текст документации
        String doc = String.format(
                "Гарантийный талон №%d%n" +
                        "Руководство пользователя:%n" +
                        "1. Подключите монитор к питанию.%n" +
                        "2. Включите питание.%n" +
                        "3. Настройте яркость и контраст.%n%n" +
                        "%s",
                guarantee,
                desc
        );

        // 4) Показываем одним Alert
        Alert a = new Alert(Alert.AlertType.INFORMATION, doc, ButtonType.OK);
        a.setTitle("Документация заказа #" + sel.getOrderId());
        a.setHeaderText(null);
        a.showAndWait();
    }

    /** Заглушка редактирования */
    @FXML
    void editDocumentation() {
        alert("Редактирование документации пока недоступно.");
    }

    /** Закрывает это окно */
    @FXML
    void handleClose() {
        ((Stage) ordersTable.getScene().getWindow()).close();
    }

    private void alert(String text) {
        new Alert(Alert.AlertType.INFORMATION, text, ButtonType.OK)
                .showAndWait();
    }
}
