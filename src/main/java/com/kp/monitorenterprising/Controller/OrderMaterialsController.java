package com.kp.monitorenterprising.Controller;

import com.kp.monitorenterprising.dao.MaterialDAO;
import com.kp.monitorenterprising.model.Material;
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

public class OrderMaterialsController {

    @FXML private TableView<Material> materialsTable;
    @FXML private TableColumn<Material, String> nameColumn;
    @FXML private TableColumn<Material, String> unitColumn;
    @FXML private TableColumn<Material, Integer> stockColumn;
    @FXML private TextField quantityField;

    private final MaterialDAO materialDAO = new MaterialDAO();

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(
                new javafx.scene.control.cell.PropertyValueFactory<>("name"));
        unitColumn.setCellValueFactory(
                new javafx.scene.control.cell.PropertyValueFactory<>("unit"));
        stockColumn.setCellValueFactory(
                new javafx.scene.control.cell.PropertyValueFactory<>("stock"));

        loadMaterials();
    }

    private void loadMaterials() {
        List<Material> list = materialDAO.getAll();  // все материалы из таблицы
        ObservableList<Material> data = FXCollections.observableArrayList(list);
        materialsTable.setItems(data);
    }

    @FXML
    void orderMaterials(ActionEvent event) {
        Material sel = materialsTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            showAlert("Ошибка", "Выберите материал.");
            return;
        }
        String txt = quantityField.getText().trim();
        int qty;
        try {
            qty = Integer.parseInt(txt);
            if (qty <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            showAlert("Ошибка", "Введите корректное количество.");
            return;
        }

        boolean ok = materialDAO.increaseStock(sel.getMaterialId(), qty);
        if (ok) {
            showAlert("Готово", "На склад добавлено " + qty + " шт.");
            loadMaterials();
            quantityField.clear();
        } else {
            showAlert("Ошибка", "Не удалось обновить склад.");
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
