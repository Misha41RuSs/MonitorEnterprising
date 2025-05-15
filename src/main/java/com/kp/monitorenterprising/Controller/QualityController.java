package com.kp.monitorenterprising.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class QualityController {

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
    void showReadyMonitors(ActionEvent event) {
        navigateTo("readyMonitors.fxml", "Готовые мониторы", event);
    }

    @FXML
    void viewGost12345678(ActionEvent event) {
        navigateTo("gost12345678.fxml", "ГОСТ 12345678", event);
    }

    @FXML
    void viewGost87654321(ActionEvent event) {
        navigateTo("gost87654321.fxml", "ГОСТ 87654321", event);
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
