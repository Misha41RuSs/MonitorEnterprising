package com.kp.monitorenterprising.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class EngineerController {

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
    void viewGost61747(ActionEvent event) {
        navigateTo("gost61747.fxml", "ГОСТ 61747", event);
    }

    @FXML
    void viewGost81234(ActionEvent event) {
        navigateTo("gost81234.fxml", "ГОСТ 81234", event);
    }

    @FXML
    void viewAwaitProduction(ActionEvent event) {
        navigateTo("awaitProduction.fxml", "Ожидают производства", event);
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
