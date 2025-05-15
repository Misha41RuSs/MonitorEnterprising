package com.kp.monitorenterprising.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class DocumentationController {

    @FXML
    void handleLogout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/kp/monitorenterprising/mainWindow.fxml")
        );
        Stage stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        stage.setTitle("Главное меню");
    }


    @FXML
    void writeDocumentation(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Генерация документации");
        alert.setHeaderText(null);
        alert.setContentText("Шаблонная документация сгенерирована для всех заказов, поступивших в наш отдел");
        alert.showAndWait();
    }

    @FXML
    void sendToClient(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/kp/monitorenterprising/sendToClient.fxml")
            );
            Stage stage = new Stage();
            stage.setTitle("Отправка документации клиенту");
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void navigateTo(String fxml, String title, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/kp/monitorenterprising/" + fxml)
            );
            Stage stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle(title);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
