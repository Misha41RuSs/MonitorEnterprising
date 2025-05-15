package com.kp.monitorenterprising.Controller;

import com.kp.monitorenterprising.dao.MaterialDAO;
import com.kp.monitorenterprising.dao.MonitorMaterialsDAO;
import com.kp.monitorenterprising.dao.OrderDAO;
import com.kp.monitorenterprising.model.Material;
import com.kp.monitorenterprising.model.MonitorMaterial;
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
import java.util.List;

public class OrdersListController {

    @FXML private TableView<Order> ordersTable;
    @FXML private TableColumn<Order, Integer>    orderIdColumn;
    @FXML private TableColumn<Order, String>     userColumn;
    @FXML private TableColumn<Order, String>     modelColumn;
    @FXML private TableColumn<Order, Integer>    quantityColumn;
    @FXML private TableColumn<Order, java.time.LocalDate> dateColumn;
    @FXML private TableColumn<Order, String>     statusColumn;

    private final OrderDAO orderDAO = new OrderDAO();
    private final MonitorMaterialsDAO mmDAO = new MonitorMaterialsDAO();
    private final MaterialDAO materialDAO = new MaterialDAO();

    @FXML
    public void initialize() {
        // Настраиваем колонки
        orderIdColumn.setCellValueFactory(
                new javafx.scene.control.cell.PropertyValueFactory<>("orderId"));
        userColumn.setCellValueFactory(
                new javafx.scene.control.cell.PropertyValueFactory<>("username"));
        modelColumn.setCellValueFactory(
                new javafx.scene.control.cell.PropertyValueFactory<>("monitorName"));
        quantityColumn.setCellValueFactory(
                new javafx.scene.control.cell.PropertyValueFactory<>("quantity"));
        dateColumn.setCellValueFactory(
                new javafx.scene.control.cell.PropertyValueFactory<>("orderDate"));
        statusColumn.setCellValueFactory(
                new javafx.scene.control.cell.PropertyValueFactory<>("status"));

        // Загружаем только «В обработке»
        List<Order> list = orderDAO.getOrdersByStatus("В обработке");
        ObservableList<Order> data = FXCollections.observableArrayList(list);
        ordersTable.setItems(data);
    }

    @FXML
    void viewMaterials(ActionEvent event) {
        Order sel = ordersTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            showAlert("Ошибка", "Сначала выберите заказ.");
            return;
        }

        // Собираем требования по материалам
        List<MonitorMaterial> reqs = mmDAO.getByMonitorId(sel.getMonitorId());
        StringBuilder sb = new StringBuilder();
        boolean allAvailable = true;
        for (MonitorMaterial req : reqs) {
            Material m = materialDAO.getById(req.getMaterialId());
            int needed = req.getQuantity() * sel.getQuantity();
            int inStock = m.getStock();
            boolean ok = inStock >= needed;
            if (!ok) allAvailable = false;
            sb.append(String.format("%s: требуется %d, в наличии %d → %s%n",
                    m.getName(),
                    needed,
                    inStock,
                    ok ? "OK" : "НЕТ"));
        }

        // Показываем окно со списком
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Материалы для заказа #" + sel.getOrderId());
        alert.setHeaderText(null);
        alert.getDialogPane().setContentText(sb.toString());

        // Если всё есть — предлагаем «Отправить на производство»
        if (allAvailable) {
            ButtonType toProd = new ButtonType("Отправить на производство", ButtonBar.ButtonData.OK_DONE);
            alert.getButtonTypes().setAll(toProd, ButtonType.CANCEL);
            alert.showAndWait().ifPresent(btn -> {
                if (btn == toProd) {
                    orderDAO.updateOrderStatus(sel.getOrderId(), "В процессе работы");
                    initialize(); // перезагрузить таблицу
                }
            });
        } else {
            alert.getButtonTypes().setAll(ButtonType.OK);
            alert.showAndWait();
        }
    }

    @FXML
    void goBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/kp/monitorenterprising/manager.fxml")
        );
        Stage stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        stage.setTitle("Менеджер");
    }

    @FXML
    void handleLogout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/kp/monitorenterprising/mainWindow.fxml")
        );
        Stage stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        stage.setTitle("Главное меню");
    }

    private void showAlert(String title, String text) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(text);
        a.showAndWait();
    }
}
